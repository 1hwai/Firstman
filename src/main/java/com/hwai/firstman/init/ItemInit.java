package com.hwai.firstman.init;

import com.hwai.firstman.Firstman;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Firstman.MODID);

    public static final RegistryObject<Item> WRENCH = register("wrench", () -> new Item(new Item.Properties().stacksTo(1).durability(256)));
    public static final RegistryObject<Item> SOLAR_PANEL = register("solar_panel", () -> new Item(new Item.Properties()));

    private static RegistryObject<Item> register(String id, Supplier<Item> sup) {
        return CreativeTabInit.addToTab(ITEMS.register(id, sup));
    }

}
