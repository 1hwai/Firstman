package com.hwai.firstman.blockentity;

import com.hwai.firstman.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BasicGeneratorBlockEntity extends BlockEntity implements TickingBlockEntity {

    private int timer;

    public BasicGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.BASIC_GENERATOR_BLOCK_ENTITY.get(), pos, state);
    }

    public void tick() {
        if (level.isClientSide()) return;

        if (timer % 20 == 0 && level.getBlockState(worldPosition.below()).getBlock() == Blocks.GRASS_BLOCK) {
            System.out.println("Generator does work");
        }
    }

}
