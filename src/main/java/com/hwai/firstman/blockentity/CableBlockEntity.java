package com.hwai.firstman.blockentity;

import com.hwai.firstman.init.BlockEntityInit;
import com.hwai.firstman.models.network.pipe.Pipe;
import com.hwai.firstman.models.network.pipe.cable.Cable;
import com.hwai.firstman.models.network.pipe.cable.CableType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CableBlockEntity extends PipeBlockEntity implements ModelBlockEntity<Cable> {

    private Cable cable;

    public CableBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.CABLE_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    protected Pipe createPipe(Level level, BlockPos pos) {
        return new Cable(level, pos, CableType.NORMAL);
    }

    @Override
    public Cable getModel() {
        return cable;
    }
}
