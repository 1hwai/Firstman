package com.hwai.firstman.init;

import com.hwai.firstman.Firstman;
import com.hwai.firstman.block.BasicGeneratorBlock;
import com.hwai.firstman.block.BoilerBlock;
import com.hwai.firstman.block.CableBlock;
import com.hwai.firstman.block.SolarPanelSlopeBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Firstman.MODID);

    public static final RegistryObject<Block> BASIC_GENERATOR_BLOCK = register("basic_generator_block", () -> new BasicGeneratorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
    public static final RegistryObject<Block> BOILER_BLOCK = register("boiler_block", BoilerBlock::new);
    public static final RegistryObject<Block> SOLAR_PANEL_SLOPE_BLOCK = register("solar_panel_slope_block", SolarPanelSlopeBlock::new);
    public static final RegistryObject<CableBlock> CABLE_BLOCK = register("cable_block", CableBlock::new);

    public static <T extends Block> RegistryObject<T> register(String id, Supplier<T> sup) {
        final RegistryObject<T> block = BLOCKS.register(id, sup);
        CreativeTabInit.addToTab(ItemInit.ITEMS.register(id, () -> new BlockItem(block.get(), new Item.Properties())));
        return block;
    }
}
