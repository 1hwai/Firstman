package com.hwai.firstman.models.network.pipe;

import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class PipeFactoryManager {
    public static final PipeFactoryManager INSTANCE = new PipeFactoryManager();

    private final Map<ResourceLocation, PipeFactory> factories = new HashMap<>();

    private PipeFactoryManager() {
    }

    public void addFactory(ResourceLocation id, PipeFactory factory) {
        if (factories.containsKey(id)) {
            throw new RuntimeException("Cannot register duplicate pipe factory " + id.toString());
        }

        factories.put(id, factory);
    }

    @Nullable
    public PipeFactory getFactory(ResourceLocation id) {
        return factories.get(id);
    }
}
