package com.hwai.firstman.models.network.pipe.cable;

import com.hwai.firstman.models.network.pipe.PipeFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

public class CableFactory implements PipeFactory {

    @Override
    public Cable createFromNbt(Level level, CompoundTag tag) {
        BlockPos pos = BlockPos.of(tag.getLong("pos"));

        Cable cable = new Cable(level, pos, CableType.values()[tag.getInt("type")]);
        //Attachment manager should be called
        //cable.getAttachmentManager(). ...

        return cable;
    }
}
