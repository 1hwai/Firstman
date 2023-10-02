package com.hwai.firstman.block;

import com.hwai.firstman.blockentity.SolarPanelBlockEntity;
import com.hwai.firstman.blockentity.TickingBlockEntity;
import com.hwai.firstman.init.BlockEntityInit;
import com.hwai.firstman.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SolarPanelSlopeBlock extends HorizontalDirectionalBlock implements EntityBlock {

    public static final VoxelShape VOXEL_SHAPE = makeShape();
    public static final BooleanProperty IDLE = BooleanProperty.create("idle");

    public SolarPanelSlopeBlock() {
        super(Properties.copy(Blocks.ANVIL).dynamicShape());
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(IDLE, Boolean.TRUE));
    }

    @Override
    protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING).add(IDLE);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return VOXEL_SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(BlockState state, @NotNull Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (!level.isClientSide()) {
            ItemStack itemStack = player.getMainHandItem();
            Item item = itemStack.getItem();
            if (item == ItemInit.SOLAR_PANEL.get()) {
                if (state.getValue(IDLE)) {
                    itemStack.setCount(itemStack.getCount() - 1);
                    state = state.setValue(IDLE, false);
                    level.setBlock(pos, state, Block.UPDATE_ALL);
                    if (level.getBlockEntity(pos) instanceof SolarPanelBlockEntity be) {
                        updateModel(be);
                    }
                    return InteractionResult.FAIL;
                }
            } else if (item == ItemInit.WRENCH.get()) {
                if (!state.getValue(IDLE)) {
                    state = state.setValue(IDLE, true);
                    level.setBlock(pos, state, Block.UPDATE_ALL);
                    return InteractionResult.FAIL;
                }
            }

            if (level.getBlockEntity(pos) instanceof final SolarPanelBlockEntity be) {
                updateModel(be);

                NetworkHooks.openScreen((ServerPlayer) player, be, pos);
            }

        }

        return InteractionResult.SUCCESS;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide()) {
            if (level.getBlockEntity(pos) instanceof SolarPanelBlockEntity be) {
                updateModel(be);
            }
        }
    }

    private void updateModel(SolarPanelBlockEntity be) {
        be.getModel().update();
    }

    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(-1, 0.0625, -0.6875, -0.9375, 0.875, -0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(1.9375, 0.0625, -0.6875, 2, 0.875, -0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-1, 0, 1.25, 2, 0.0625, 1.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-1, 0, -0.6875, 2, 0.75, -0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-1, 0.75, -0.6875, 2, 0.8125, -0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-1, 0, -0.6875, -0.9375, 0.0625, 1.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(1.9375, 0, -0.6875, 2, 0.0625, 1.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-1, 0.6875, -0.625, 2, 0.75, -0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-1, 0.625, -0.375, 2, 0.6875, -0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-1, 0.5625, -0.1875, 2, 0.625, -0.0625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-1, 0.5, -0.0625, 2, 0.5625, 0.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-1, 0.4375, 0.125, 2, 0.5, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-1, 0.375, 0.25, 2, 0.4375, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-1, 0.3125, 0.375, 2, 0.375, 0.5), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-1, 0.25, 0.5, 2, 0.3125, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-1, 0.1875, 0.6875, 2, 0.25, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-1, 0.125, 0.8125, 2, 0.1875, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-1, 0.0625, 1, 2, 0.125, 1.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-1, 0, 1.125, 2, 0.0625, 1.25), BooleanOp.OR);
        return shape;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityInit.SOLAR_PANEL_BLOCK_ENTITY.get().create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return TickingBlockEntity.getTickerHelper(level);
    }
}
