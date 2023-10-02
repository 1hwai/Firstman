package com.hwai.firstman.container;

import com.hwai.firstman.init.BlockInit;
import com.hwai.firstman.init.ContainerInit;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;

public class SolarPanelContainer extends AbstractContainerMenu {

    private final BlockPos pos;
    private final ContainerData data;

    public SolarPanelContainer(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, extraData.readBlockPos(), new SimpleContainerData(2));
    }
    public SolarPanelContainer(int id, BlockPos pos, ContainerData data) {
        super(ContainerInit.SOLAR_PANEL_CONTAINER.get(), id);
        this.pos = pos;
        this.data = data;

        addDataSlots(data);
    }

    public ContainerData getData() {
        return data;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(player.level(), pos), player, BlockInit.SOLAR_PANEL_SLOPE_BLOCK.get());
    }

}
