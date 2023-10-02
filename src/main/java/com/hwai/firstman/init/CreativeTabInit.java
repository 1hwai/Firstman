package com.hwai.firstman.init;

import com.hwai.firstman.Firstman;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Firstman.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CreativeTabInit {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Firstman.MODID);

    public static final List<Supplier<? extends ItemLike>> FIRST_MAN_TAB_ITEMS = new ArrayList<>();
    public static final RegistryObject<CreativeModeTab> FIRST_MAN_TAB = CREATIVE_MODE_TABS.register(Firstman.MODID,
            () -> CreativeModeTab.builder()
                    .title(Component.translatable(Firstman.MODID))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(Items.REDSTONE_BLOCK::getDefaultInstance)
                    .displayItems((parameters, output) -> FIRST_MAN_TAB_ITEMS.forEach(itemLike -> output.accept(itemLike.get()))).build()
    );

    public static <T extends Item> RegistryObject<T> addToTab(RegistryObject<T> itemLike) {
        FIRST_MAN_TAB_ITEMS.add(itemLike);
        return itemLike;
    }

    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.getEntries().putAfter(ItemInit.WRENCH.get().getDefaultInstance(), ItemInit.WRENCH.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }

        if(event.getTab() == FIRST_MAN_TAB.get()) {
        }
    }
}
