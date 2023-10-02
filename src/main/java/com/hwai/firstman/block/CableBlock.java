package com.hwai.firstman.block;

import com.hwai.firstman.blockentity.CableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.*;

import javax.annotation.Nullable;

public class CableBlock extends Block implements EntityBlock {

    private static final VoxelShape C = Shapes.box(0.375, 0.375, 0.375, 0.625, 0.625, 0.625);
    private static final VoxelShape U = Shapes.box(0.375, 0.625, 0.375, 0.625, 1, 0.625);
    private static final VoxelShape D = Shapes.box(0.375, 0, 0.375, 0.625, 0.375, 0.625);
    private static final VoxelShape N = Shapes.box(0.375, 0.375, 0, 0.625, 0.625, 0.375);
    private static final VoxelShape S = Shapes.box(0.375, 0.375, 0.625, 0.625, 0.625, 1);
    private static final VoxelShape E = Shapes.box(0.625, 0.375, 0.375, 1, 0.625, 0.625);
    private static final VoxelShape W = Shapes.box(0, 0.375, 0.375, 0.375, 0.625, 0.625);

    public CableBlock() {
        super(Properties.of().destroyTime(1.0f));
        registerDefaultState(defaultBlockState()
                .setValue(UP, false).setValue(DOWN, false)
                .setValue(NORTH, false).setValue(SOUTH, false)
                .setValue(EAST, false).setValue(WEST, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(UP, DOWN, NORTH, SOUTH, EAST, WEST);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CableBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return makeShape(state);
    }

    public static VoxelShape makeShape(BlockState state) {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, C, BooleanOp.OR);
        if (state.getValue(UP)) Shapes.join(shape, U, BooleanOp.OR);
        if (state.getValue(DOWN)) Shapes.join(shape, D, BooleanOp.OR);
        if (state.getValue(NORTH)) Shapes.join(shape, N, BooleanOp.OR);
        if (state.getValue(SOUTH)) Shapes.join(shape, S, BooleanOp.OR);
        if (state.getValue(EAST)) Shapes.join(shape, E, BooleanOp.OR);
        if (state.getValue(WEST)) Shapes.join(shape, W, BooleanOp.OR);

        return shape;
    }
}
