package com.hwai.firstman.datagen;

import com.hwai.firstman.Firstman;
import com.hwai.firstman.block.CableBlock;
import com.hwai.firstman.init.BlockInit;
import com.hwai.firstman.models.network.pipe.cable.CableType;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.*;

public class FirstManBlockStates extends BlockStateProvider {

    public FirstManBlockStates(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Firstman.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerCables();
    }

    protected void registerCables() {
        CableBlock[] cables = {BlockInit.CABLE_BLOCK.get()};
        for (CableBlock cableBlock : cables) {
            VariantBlockStateBuilder bld = getVariantBuilder(cableBlock);

            Queue<ArrayList<Boolean>> queue = new LinkedList<>();
            ArrayList<Boolean> start = new ArrayList<>();
            queue.add(start);

            while (!queue.isEmpty()) {
                ArrayList<Boolean> element = queue.poll();
                if (element.size() < 6) {
                    ArrayList<Boolean> temp1 = new ArrayList<>(element);
                    ArrayList<Boolean> temp2 = new ArrayList<>(element);
                    temp1.add(true);
                    temp2.add(false);
                    queue.add(temp1);
                    queue.add(temp2);
                } else if (element.size() == 6) {
                    String status = (element.get(0) ? "u" : "") + (element.get(1) ? "d" : "")
                            + (element.get(2) ? "n" : "") + (element.get(3) ? "s" : "") + (element.get(4) ? "e" : "") + (element.get(5) ? "w" : "");
                    if (status.equals("")) status = "none";

                    String path = "block/cable/" + status;
                    BlockModelBuilder cable = models().getBuilder(path);
                    bld.partialState().with(UP, element.get(0)).with(DOWN, element.get(1)).with(NORTH, element.get(2)).with(SOUTH, element.get(3)).with(EAST, element.get(4)).with(WEST, element.get(5))
                            .modelForState().modelFile(cable).addModel();
                }
            }
        }
    }
}
