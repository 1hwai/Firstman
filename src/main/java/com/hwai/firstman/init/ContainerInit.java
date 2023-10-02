package com.hwai.firstman.init;

import com.hwai.firstman.Firstman;
import com.hwai.firstman.container.SolarPanelContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ContainerInit {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Firstman.MODID);

    public static final RegistryObject<MenuType<SolarPanelContainer>> SOLAR_PANEL_CONTAINER = register("solar_panel_container", SolarPanelContainer::new);

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String id, IContainerFactory<T> factory) {
        return CONTAINERS.register(id, () ->
                IForgeMenuType.create(factory)
        );
    }

}
