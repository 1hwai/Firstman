package com.hwai.firstman.datagen;

import com.hwai.firstman.Firstman;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Firstman.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        generator.addProvider(event.includeClient(), new FirstManItemModels(generator, event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new FirstManBlockModels(packOutput, event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new FirstManBlockStates(packOutput, event.getExistingFileHelper()));
    }
}
