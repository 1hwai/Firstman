package com.hwai.firstman.datagen;

import com.hwai.firstman.Firstman;
import com.hwai.firstman.init.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class FirstManItemModels extends ItemModelProvider {

    public FirstManItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator.getPackOutput(), Firstman.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        System.out.println("fsdajfklajkldwfjdklasfjdksfjdksfjdsklfjdasklfjadsklfjdkaslfjdkslfjadsfjadkslfjakslfjadklsj");
        registerItemModels(ItemInit.SOLAR_PANEL, "solar_panel");
    }

    private void registerItemModels(RegistryObject<Item> item, String id) {
        System.out.println("sdfafkasjdkakasfjakjsfkafjdj");
        System.out.println("RLoc" + item.getId());
        singleTexture(item.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("items/" + id));
    }

    private void registerBlockModels(RegistryObject<Block> block, String id) {
        withExistingParent(block.getId().getPath(), modLoc("block/" + id));
    }

    @Override
    public String getName() {
        return "SpaceSim Tags";
    }
}