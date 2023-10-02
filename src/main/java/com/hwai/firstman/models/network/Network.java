package com.hwai.firstman.models.network;

import com.hwai.firstman.models.network.graph.NetworkGraph;
import com.hwai.firstman.models.network.graph.NetworkGraphScannerResult;
import com.hwai.firstman.models.network.pipe.Destination;
import com.hwai.firstman.models.network.pipe.DestinationType;
import com.hwai.firstman.models.network.pipe.Pipe;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.List;

public abstract class Network {

    protected final NetworkGraph graph = new NetworkGraph(this);
    private final String id;
    private BlockPos origin;
    private boolean didDoInitialScan;

    public Network(BlockPos origin, String id) {
        this.id = id;
        this.origin = origin;
    }

    public void setOrigin(BlockPos origin) {
        this.origin = origin;
    }

    public String getId() {
        return id;
    }

    public NetworkGraphScannerResult scanGraph(Level level, BlockPos pos) {
        return graph.scan(level, pos);
    }

    public List<Destination> getDestinations(DestinationType type) {
        return graph.getDestinations(type);
    }

    public CompoundTag writeToNbt(CompoundTag tag) {
        tag.putString("id", id);
        tag.putLong("origin", origin.asLong());

        return tag;
    }

    public void update(Level level) {
        if (!didDoInitialScan) {
            didDoInitialScan = true;

            scanGraph(level, origin);
        }

        graph.getPipes().forEach(Pipe::update);
    }

    public abstract void onMergedWith(Network mainNetwork);

    public abstract ResourceLocation getType();
}
