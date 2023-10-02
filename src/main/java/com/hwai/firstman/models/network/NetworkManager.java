package com.hwai.firstman.models.network;

import com.hwai.firstman.Firstman;
import com.hwai.firstman.models.network.graph.NetworkGraphScannerResult;
import com.hwai.firstman.models.network.pipe.Pipe;
import com.hwai.firstman.models.network.pipe.PipeFactory;
import com.hwai.firstman.models.network.pipe.PipeFactoryManager;
import com.hwai.firstman.models.network.pipe.cable.Cable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

public class NetworkManager extends SavedData {
    private static final String ID = Firstman.MODID + ".networks";
    private static final Logger LOGGER = LogManager.getLogger(NetworkManager.class);
    private final Level level;
    private final Map<String, Network> networks = new HashMap<>();
    private final Map<BlockPos, Pipe> pipes = new HashMap<>();

    private NetworkManager(Level level) {
        this.level = level;
    }

    public static NetworkManager get(Level level) {
        return get((ServerLevel) level);
    }

    public static NetworkManager get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent((tag) -> {
            NetworkManager networkManager = new NetworkManager(level);
            networkManager.load(tag);
            return networkManager;
        }, () -> new NetworkManager(level), ID);
    }

    //Network

    public void addNetwork(Network network) {
        if (networks.containsKey(network.getId())) {
            throw new RuntimeException("Duplicate network " + network.getId());
        }

        networks.put(network.getId(), network);

        LOGGER.debug("Network {} created", network.getId());

        setDirty();
    }

    public void removeNetwork(String id) {
        if (!networks.containsKey(id)) {
            throw new RuntimeException("Network " + id + " not found");
        }

        networks.remove(id);

        LOGGER.debug("Network {} removed", id);

        setDirty();
    }

    private void formNetworkAt(Level level, BlockPos pos, ResourceLocation type) {
        Network network = NetworkFactoryManager.INSTANCE.getFactory(type).create(pos);

        addNetwork(network);

        network.scanGraph(level, pos);
    }

    private void mergeNetworks(Set<Pipe> candidates, Level level, BlockPos pos) {
        if (candidates.isEmpty()) throw new RuntimeException("Failed to merge Networks: no candidates");

        Set<Network> networkCandidates = new HashSet<>();

        for (Pipe candidate : candidates) {
            if (candidate.getNetwork() == null) {
                throw new RuntimeException("Pipe network is null!");
            }

            networkCandidates.add(candidate.getNetwork());
        }

        Iterator<Network> networks = networkCandidates.iterator();

        Network mainNetwork = networks.next();

        Set<Network> mergedNetworks = new HashSet<>();

        while (networks.hasNext()) {
            // Remove all the other networks.
            Network otherNetwork = networks.next();

            boolean canMerge = mainNetwork.getType().equals(otherNetwork.getType());

            if (canMerge) {
                mergedNetworks.add(otherNetwork);

                removeNetwork(otherNetwork.getId());
            }
        }

        mainNetwork.scanGraph(level, pos);

        mergedNetworks.forEach(n -> n.onMergedWith(mainNetwork));

    }

    private void splitNetwork(Pipe originPipe) {
        for (Pipe adjacent : findAdjacentPipes(originPipe.getPos(), originPipe.getNetworkType())) {
            if (adjacent.getNetwork() == null) {
                throw new RuntimeException("Adjacent pipe has no network");
            }

            if (adjacent.getNetwork() != originPipe.getNetwork()) {
                throw new RuntimeException("The origin pipe network is different than the adjacent pipe network");
            }
        }

        Pipe otherPipeInNetwork = findFirstAdjacentPipe(originPipe.getPos(), originPipe.getNetworkType());

        if (otherPipeInNetwork != null) {
            otherPipeInNetwork.getNetwork().setOrigin(otherPipeInNetwork.getPos());
            setDirty();

            NetworkGraphScannerResult result = otherPipeInNetwork.getNetwork().scanGraph(
                    otherPipeInNetwork.getLevel(),
                    otherPipeInNetwork.getPos()
            );

            // For sanity checking
            boolean foundRemovedPipe = false;

            for (Pipe removed : result.getRemovedPipes()) {
                // It's obvious that our removed pipe is removed.
                // We don't want to create a new splitted network for this one.
                if (removed.getPos().equals(originPipe.getPos())) {
                    foundRemovedPipe = true;
                    continue;
                }

                // The formNetworkAt call below can let these removed pipes join a network again.
                // We only have to form a new network when necessary, hence the null check.
                if (removed.getNetwork() == null) {
                    formNetworkAt(removed.getLevel(), removed.getPos(), removed.getNetworkType());
                }
            }

            if (!foundRemovedPipe) {
                throw new RuntimeException("Didn't find removed pipe when splitting network");
            }
        } else {
            LOGGER.debug("Removing empty network {}", originPipe.getNetwork().getId());

            removeNetwork(originPipe.getNetwork().getId());
        }
    }

    //Pipe

    public void addPipe(Pipe pipe) {
        if (pipes.containsKey(pipe.getPos())) {
            throw new RuntimeException("Pipe at " + pipe.getPos() + " already exists");
        }

        pipes.put(pipe.getPos(), pipe);
        LOGGER.info("pipes {");
        pipes.values().forEach(p -> LOGGER.info("P : "  + p.getPos()));
        LOGGER.info("}");

        LOGGER.debug("Pipe added at {}", pipe.getPos());

        setDirty();

        Set<Pipe> adjacentPipes = findAdjacentPipes(pipe.getPos(), pipe.getNetworkType());
        if (adjacentPipes.isEmpty()) {
            formNetworkAt(level, pipe.getPos(), pipe.getNetworkType());
        } else {
            mergeNetworks(adjacentPipes, pipe.getLevel(), pipe.getPos());
        }
    }

    public void removePipe(BlockPos pos) {
        Pipe pipe = getPipe(pos);
        if (pipe == null) {
            throw new RuntimeException("Pipe at " + pos + " was not found");
        }

        if (pipe.getNetwork() == null) {
            LOGGER.warn("Removed pipe at {} has no associated network", pipe.getPos());
        }

        pipes.remove(pipe.getPos());

        LOGGER.debug("Pipe removed at {}", pipe.getPos());

        setDirty();

        if (pipe.getNetwork() != null) {
            splitNetwork(pipe);
        }
    }

    private Set<Pipe> findAdjacentPipes(BlockPos pos, ResourceLocation networkType) {
        Set<Pipe> pipes = new HashSet<>();

        for (Direction dir : Direction.values()) {
            Pipe pipe = getPipe(pos.relative(dir));

            if (pipe != null && pipe.getNetworkType().equals(networkType)) {
                pipes.add(pipe);
            }
        }

        return pipes;
    }

    @Nullable
    private Pipe findFirstAdjacentPipe(BlockPos pos, ResourceLocation networkType) {
        for (Direction dir : Direction.values()) {
            Pipe pipe = getPipe(pos.relative(dir));

            if (pipe != null && pipe.getNetworkType().equals(networkType)) {
                return pipe;
            }
        }

        return null;
    }

    @Nullable
    public Pipe getPipe(BlockPos pos) {
        return pipes.get(pos);
    }

    public Collection<Network> getNetworks() {
        return networks.values();
    }

    //Data save & load

    public void load(CompoundTag tag) {
        ListTag pipeTags = tag.getList("pipes", Tag.TAG_COMPOUND);
        for (Tag pipeTag : pipeTags) {
            CompoundTag pipeCompoundTag = (CompoundTag) pipeTag;

            ResourceLocation id = pipeCompoundTag.contains("id") ? new ResourceLocation(pipeCompoundTag.getString("id")) : Cable.ID;
            PipeFactory factory = PipeFactoryManager.INSTANCE.getFactory(id);
            if (factory == null) {
                LOGGER.warn("Failed to load Pipe : " + id);
                continue;
            }

            Pipe pipe = factory.createFromNbt(level, pipeCompoundTag);

            pipes.put(pipe.getPos(), pipe);
        }

        ListTag networkTags = tag.getList("networks", Tag.TAG_COMPOUND);
        for (Tag networkTag : networkTags) {
            CompoundTag networkCompoundTag = (CompoundTag) networkTag;

            if (!networkCompoundTag.contains("type")) {
                LOGGER.warn("Skipping network without type");
                continue;
            }

            ResourceLocation type = new ResourceLocation(networkCompoundTag.getString("type"));

            NetworkFactory factory = NetworkFactoryManager.INSTANCE.getFactory(type);
            if (factory == null) {
                LOGGER.warn("Failed to load Network" + type);
                continue;
            }

            Network network = factory.create(networkCompoundTag);

            networks.put(network.getId(), network);
        }

        LOGGER.debug("Read {} pipes", pipes.size());
        LOGGER.debug("Read {} networks", networks.size());
    }

    @Override
    public @NotNull CompoundTag save(CompoundTag tag) {
        ListTag pipeTags = new ListTag();
        for (Pipe pipe : pipes.values()) {
            CompoundTag pipeCompoundTag = new CompoundTag();
            pipeCompoundTag.putString("id", pipe.getId().toString());
            pipeTags.add(pipe.writeToNbt(pipeCompoundTag));
        }
        tag.put("pipes", pipeTags);

        ListTag networkTags = new ListTag();
        for (Network network : networks.values()) {
            CompoundTag networkCompoundTag = new CompoundTag();
            networkCompoundTag.putString("type", network.getType().toString());
            networkTags.add(network.writeToNbt(networkCompoundTag));
        }
        tag.put("networks", networkTags);

        return tag;
    }

}
