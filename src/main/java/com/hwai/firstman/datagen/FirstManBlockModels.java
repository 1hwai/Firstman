package com.hwai.firstman.datagen;

import com.hwai.firstman.Firstman;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FirstManBlockModels extends BlockModelProvider {

    public FirstManBlockModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Firstman.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }

}
