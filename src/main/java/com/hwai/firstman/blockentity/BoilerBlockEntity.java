package com.hwai.firstman.blockentity;

import com.hwai.firstman.init.BlockEntityInit;
import com.hwai.firstman.models.multiblock.Boiler;
import com.hwai.firstman.models.multiblock.BoilerManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BoilerBlockEntity extends BlockEntity implements TickingBlockEntity {

    private int timer = 0;
    public BoilerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.BOILER_BLOCK_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        if (level.isClientSide()) return;

        if (timer++ % 30 == 0)
            System.out.println("boil boil...");

    }

    @Override
    public void clearRemoved() {
        super.clearRemoved();

        if (!level.isClientSide()) {
            BoilerManager mgr = BoilerManager.get(level);

            if (mgr.getBoiler(worldPosition) == null) {
                mgr.addBoiler(createBoiler(level, worldPosition));
                System.out.println("eungAe");
            }
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();

        if (!level.isClientSide() && level.isLoaded(worldPosition)) {
            BoilerManager mgr = BoilerManager.get(level);
            mgr.removeBoiler(worldPosition);
            System.out.println("euAk");
        }
    }

    private Boiler createBoiler(Level level, BlockPos pos) {

        return new Boiler(level, pos);
    }

}
