package com.hwai.firstman.init;

import com.hwai.firstman.Firstman;
import com.hwai.firstman.blockentity.BasicGeneratorBlockEntity;
import com.hwai.firstman.blockentity.BoilerBlockEntity;
import com.hwai.firstman.blockentity.CableBlockEntity;
import com.hwai.firstman.blockentity.SolarPanelBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Firstman.MODID);

    public static final RegistryObject<BlockEntityType<BasicGeneratorBlockEntity>> BASIC_GENERATOR_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("basic_generator_block_entity",
                    () -> BlockEntityType.Builder.of(BasicGeneratorBlockEntity::new, BlockInit.BASIC_GENERATOR_BLOCK.get()).build(null)
            );

    public static final RegistryObject<BlockEntityType<BoilerBlockEntity>> BOILER_BLOCK_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("boiler_block_entity",
                    () -> BlockEntityType.Builder.of(BoilerBlockEntity::new, BlockInit.BOILER_BLOCK.get()).build(null)
            );

    public static final RegistryObject<BlockEntityType<SolarPanelBlockEntity>> SOLAR_PANEL_BLOCK_ENTITY =
            register("solar_panel_block_entity", SolarPanelBlockEntity::new, BlockInit.SOLAR_PANEL_SLOPE_BLOCK);

    public static final RegistryObject<BlockEntityType<CableBlockEntity>> CABLE_BLOCK_ENTITY =
            register("cable_block_entity", CableBlockEntity::new, BlockInit.CABLE_BLOCK);

    public static <T extends BlockEntity, V extends Block> RegistryObject<BlockEntityType<T>> register(String id, BlockEntityType.BlockEntitySupplier<T> sup, RegistryObject<V> block) {
        return BLOCK_ENTITIES.register(id,
                () -> BlockEntityType.Builder.of(sup, block.get()).build(null)
        );
    }
}
