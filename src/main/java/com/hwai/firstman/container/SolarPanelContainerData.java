package com.hwai.firstman.container;

import com.hwai.firstman.models.SolarPanel;
import net.minecraft.world.inventory.SimpleContainerData;

public class SolarPanelContainerData extends SimpleContainerData {
    private final SolarPanel solarPanel;

    public SolarPanelContainerData(SolarPanel solarPanel, int amount) {
        super(amount);
        this.solarPanel = solarPanel;
    }

    @Override
    public int get(int key) {
        return switch (key) {
            case 1 -> solarPanel.getOutput().getAmount();
            default -> solarPanel.getBrightness();
        };
    }

}
