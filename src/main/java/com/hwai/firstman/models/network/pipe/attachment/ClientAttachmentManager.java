package com.hwai.firstman.models.network.pipe.attachment;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ClientAttachmentManager implements AttachmentManager {
    @Override
    public ResourceLocation[] getState() {
        return new ResourceLocation[0];
    }

    @Override
    public boolean hasAttachment(Direction dir) {
        return false;
    }

    @Override
    public void openAttachmentContainer(Direction dir, ServerPlayer player) {

    }

    @NotNull
    @Override
    public ItemStack getPickBlock(Direction dir) {
        return null;
    }

    @Nullable
    @Override
    public Attachment getAttachment(Direction dir) {
        return null;
    }

    @Override
    public void writeUpdate(CompoundTag tag) {
        throw new RuntimeException("Server-side only");
    }

    @Override
    public void readUpdate(@Nullable CompoundTag tag) {

    }
}
