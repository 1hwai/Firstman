package com.hwai.firstman.models.multiblock;

import com.hwai.firstman.Firstman;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class BoilerManager extends SavedData {

    private static final String ID = Firstman.MODID + ".boilers";
    private final Level level;
    private final HashMap<BlockPos, Boiler> boilers = new HashMap<>();

    private BoilerManager(Level level) {
        this.level = level;
    }

    public static BoilerManager get(Level level) {
        return get((ServerLevel) level);
    }

    private static BoilerManager get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(tag -> {
            BoilerManager boilerManager = new BoilerManager(level);
            boilerManager.load(tag);
            return boilerManager;
        }, () -> new BoilerManager(level), ID);
    }

    public Boiler getBoiler(BlockPos pos) {
        return boilers.get(pos);
    }

    public void addBoiler(Boiler boiler) {
        if (boilers.containsKey(boiler.getPos()))
            throw new RuntimeException("Duplicated boiler: " + boiler.getPos());

        boilers.put(boiler.getPos(), boiler);
        System.out.println("Boilers: " + boilers.values().size());
        setDirty();
    }

    public void removeBoiler(BlockPos pos) {
        if (!boilers.containsKey(pos))
            throw new RuntimeException("boiler not found: " + pos);

        boilers.remove(pos);
        setDirty();
    }

    public void load(CompoundTag tag) {
        ListTag boilerTags = tag.getList("boilers", Tag.TAG_COMPOUND);
        for (Tag boilerTag : boilerTags) {
            CompoundTag boilerTagCompound = (CompoundTag) boilerTag;
            BlockPos pos = BlockPos.of(boilerTagCompound.getLong("pos"));
            addBoiler(new Boiler(level, pos));
        }
        System.out.println("Boilers Loaded: " + boilers.values().size());
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag tag) {
        ListTag boilerTags = new ListTag();
        for (Boiler b : boilers.values()) {
            CompoundTag boilerTag = new CompoundTag();
            boilerTag.putString("id", b.getId().toString());
            boilerTag.putLong("pos", b.getPos().asLong());
            boilerTags.add(boilerTag);
        }
        tag.put("boilers", boilerTags);

        return tag;
    }

}
