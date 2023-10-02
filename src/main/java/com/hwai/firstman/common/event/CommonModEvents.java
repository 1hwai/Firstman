package com.hwai.firstman.common.event;

import com.hwai.firstman.Firstman;
import com.hwai.firstman.models.network.NetworkFactoryManager;
import com.hwai.firstman.models.network.NetworkManager;
import com.hwai.firstman.models.network.NetworkType;
import com.hwai.firstman.models.network.energy.EnergyNetworkFactory;
import com.hwai.firstman.models.network.pipe.PipeFactoryManager;
import com.hwai.firstman.models.network.pipe.cable.Cable;
import com.hwai.firstman.models.network.pipe.cable.CableFactory;
import com.hwai.firstman.models.network.pipe.cable.CableType;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = Firstman.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {
    @SubscribeEvent
    public static void commonSetup(@NotNull FMLCommonSetupEvent event) {
        NetworkFactoryManager.INSTANCE.addFactory(NetworkType.ENERGY.getNetworkType(), new EnergyNetworkFactory(CableType.NORMAL));
        PipeFactoryManager.INSTANCE.addFactory(Cable.ID, new CableFactory());
    }

//    @SubscribeEvent
//    public static void onLevelTick(TickEvent.LevelTickEvent e) {
//        if (!e.level.isClientSide() && e.phase == TickEvent.Phase.END) {
//            NetworkManager.get(e.level).getNetworks().forEach(n -> n.update(e.level));
//        }
//    }

}
