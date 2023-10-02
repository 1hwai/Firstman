package com.hwai.firstman.models.network.pipe;

import com.hwai.firstman.models.network.Network;
import com.hwai.firstman.models.network.pipe.attachment.Attachment;
import com.hwai.firstman.models.network.pipe.attachment.ServerAttachmentManager;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public abstract class Pipe {
    private BlockPos pos;
    private Level level;
    protected final ServerAttachmentManager attachmentManager = new ServerAttachmentManager(this);
    protected Network network;

    public Pipe(Level level, BlockPos pos) {
        this.level = level;
        this.pos = pos;
    }

    public void update() {
        for (Attachment attachment : attachmentManager.getAttachments()) {
            attachment.update();
        }
    }

    public ServerAttachmentManager getAttachmentManager() {
        return attachmentManager;
    }

    public BlockPos getPos() {
        return pos;
    }

    public Level getLevel() {
        return level;
    }

    public Network getNetwork() {
        return network;
    }

    public void joinNetwork(Network network) {
        this.network = network;

        sendBlockUpdate();
    }

    public void leaveNetwork() {
        this.network = null;

        sendBlockUpdate();
    }

    public void sendBlockUpdate() {
        BlockState state = level.getBlockState(pos);
        level.sendBlockUpdated(pos, state, state, 1 | 2);
    }

    public CompoundTag writeToNbt(CompoundTag tag) {
        tag.putLong("pos", pos.asLong());

        attachmentManager.writeToNbt(tag);

        return tag;
    }

    public abstract ResourceLocation getNetworkType();

    public abstract ResourceLocation getId();

}
