package com.hwai.firstman.models.network.energy;

import com.hwai.firstman.models.network.Network;
import com.hwai.firstman.models.network.NetworkFactory;
import com.hwai.firstman.models.network.pipe.cable.CableType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class EnergyNetworkFactory implements NetworkFactory {

    private static final Logger LOGGER = LogManager.getLogger(EnergyNetworkFactory.class);

    private final CableType cableType;

    public EnergyNetworkFactory(CableType cableType) {
        this.cableType = cableType;
    }

    @Override
    public Network create(BlockPos pos) {
        return new EnergyNetwork(pos, UUID.randomUUID().toString());
    }

    @Override
    public Network create(CompoundTag tag) {
        EnergyNetwork network = new EnergyNetwork(BlockPos.of(tag.getLong("origin")), tag.getString("id"));

        LOGGER.debug("Deserialized energy network {} of type {}", network.getId(), network.getType().toString());

        return network;
    }
}
