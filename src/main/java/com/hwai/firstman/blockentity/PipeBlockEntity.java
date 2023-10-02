package com.hwai.firstman.blockentity;

import com.hwai.firstman.models.network.NetworkManager;
import com.hwai.firstman.models.network.pipe.Pipe;
import com.hwai.firstman.models.network.pipe.attachment.AttachmentManager;
import com.hwai.firstman.models.network.pipe.attachment.ClientAttachmentManager;
import com.hwai.firstman.models.network.pipe.attachment.DummyAttachmentManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class PipeBlockEntity extends BlockEntity {

    private final AttachmentManager clientAttachmentManager = new ClientAttachmentManager();

    protected PipeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public AttachmentManager getAttachmentManager() {
        if (level.isClientSide()) {
            return clientAttachmentManager;
        }

        Pipe pipe = NetworkManager.get(level).getPipe(worldPosition);

        if (pipe != null) {
            return pipe.getAttachmentManager();
        }

        return DummyAttachmentManager.INSTANCE;
    }

    @Override
    public void clearRemoved() {
        super.clearRemoved();

        if (!level.isClientSide()) {
            NetworkManager mgr = NetworkManager.get(level);

            if (mgr.getPipe(worldPosition) == null) {
                mgr.addPipe(createPipe(level, worldPosition));
            }
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();

        if (!level.isClientSide()) {
            NetworkManager mgr = NetworkManager.get(level);

//            Pipe pipe = mgr.getPipe(worldPosition);
//            if (pipe != null) {
//                for (Attachment attachment : pipe.getAttachmentManager().getAttachments()) {
//                    Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), attachment.getDrop());
//                }
//            }

            mgr.removePipe(worldPosition);
        }
    }

    protected abstract Pipe createPipe(Level level, BlockPos pos);
}
