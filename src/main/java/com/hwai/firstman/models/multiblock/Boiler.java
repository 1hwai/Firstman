package com.hwai.firstman.models.multiblock;

import com.hwai.firstman.Firstman;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class Boiler {
    private final Level level;
    private final BlockPos pos;
    private static final ResourceLocation ID = new ResourceLocation(Firstman.MODID, "boiler");

    public Boiler(Level level, BlockPos pos) {
        this.level = level;
        this.pos = pos;
    }

    public BlockPos getPos() {
        return pos;
    }

    public ResourceLocation getId() {
        return ID;
    }

}
