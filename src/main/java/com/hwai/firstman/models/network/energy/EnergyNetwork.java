package com.hwai.firstman.models.network.energy;

import com.hwai.firstman.models.network.Network;
import com.hwai.firstman.models.network.NetworkType;
import com.hwai.firstman.models.network.graph.NetworkGraphScannerResult;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class EnergyNetwork extends Network {

    public EnergyNetwork(BlockPos origin, String id) {
        super(origin, id);
    }

    @Override
    public NetworkGraphScannerResult scanGraph(Level level, BlockPos pos) {
        return super.scanGraph(level, pos);
    }

    @Override
    public void onMergedWith(Network mainNetwork) {
    }

    @Override
    public ResourceLocation getType() {
        return NetworkType.ENERGY.getNetworkType();
    }
}
