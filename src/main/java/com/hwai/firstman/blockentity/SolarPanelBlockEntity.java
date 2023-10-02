package com.hwai.firstman.blockentity;

import com.hwai.firstman.block.SolarPanelSlopeBlock;
import com.hwai.firstman.container.SolarPanelContainer;
import com.hwai.firstman.init.BlockEntityInit;
import com.hwai.firstman.models.SolarPanel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SolarPanelBlockEntity extends BlockEntity implements ModelBlockEntity<SolarPanel>, TickingBlockEntity, MenuProvider {

    private int timer = 0;

    private SolarPanel solarPanel;

    private final ContainerData data = new SimpleContainerData(2) {
        @Override
        public int get(int key) {
            return switch (key) {
                case 0 -> getModel().getOutput().getAmount();
                default -> getModel().getBrightness();
            };
        }
    };

    public SolarPanelBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.SOLAR_PANEL_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        if (level.isClientSide()) return;

        if (!isIdle()) {
            solarPanel.generate();
        }
    }

    @Override
    public void clearRemoved() {
        super.clearRemoved();

        if (!level.isClientSide()) {
            solarPanel = SolarPanel.create(level, worldPosition);
        }
    }

    private boolean isIdle() {
        return getBlockState().getValue(SolarPanelSlopeBlock.IDLE);
    }

    @Override
    public SolarPanel getModel() {
        return solarPanel;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new SolarPanelContainer(id, worldPosition, data);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("Solar Panel");
    }
}
