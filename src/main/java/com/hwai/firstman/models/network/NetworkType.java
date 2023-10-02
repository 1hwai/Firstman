package com.hwai.firstman.models.network;

import com.hwai.firstman.Firstman;
import net.minecraft.resources.ResourceLocation;

public enum NetworkType {
    ENERGY,
    FLUID;

    public ResourceLocation getNetworkType() {
        return switch (this) {
            case FLUID -> new ResourceLocation(Firstman.MODID, "fluid_network");
            case ENERGY -> new ResourceLocation(Firstman.MODID, "energy_network");
        };
    }
}
