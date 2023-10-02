package com.hwai.firstman.models.network.pipe.cable;

import com.hwai.firstman.Firstman;
import com.hwai.firstman.models.network.NetworkType;
import com.hwai.firstman.models.network.pipe.Pipe;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class Cable extends Pipe {

    public static final ResourceLocation ID = new ResourceLocation(Firstman.MODID, "cable");

    private final CableType type;

    public Cable(Level level, BlockPos pos, CableType type) {
        super(level, pos);
        this.type = type;
    }

    @Override
    public CompoundTag writeToNbt(CompoundTag tag) {
        tag = super.writeToNbt(tag);

        tag.putInt("type", type.ordinal());

        return tag;
    }

    @Override
    public ResourceLocation getNetworkType() {
        return NetworkType.ENERGY.getNetworkType();
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}
