package com.hwai.firstman.models;

import com.hwai.firstman.Firstman;
import com.hwai.firstman.energy.Energy;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;


public class SolarPanel implements EnergyGenerators {
    private final Level level;
    private final BlockPos pos;

    private static final ResourceLocation ID = new ResourceLocation(Firstman.MODID, "solar_panel");

    private boolean ableToGenerate = false;

    private final Energy output = Energy.init();

    private SolarPanel(Level level, BlockPos pos) {
        this.level = level;
        this.pos = pos;
    }

    public static SolarPanel create(Level level, BlockPos pos) {
        return new SolarPanel(level, pos);
    }

    public static ResourceLocation getID() {
        return ID;
    }

    @Override
    public void generate() {
        if (ableToGenerate) output.set(getBrightness() / 100);
        else output.set(0);
    }

    public Energy getOutput() {
        return output;
    }

    @Override
    public void update() {
        ableToGenerate = isAir(pos.north()) && isAir(pos.west()) && isAir(pos.east())
                && isAir(pos.west().north()) && isAir(pos.east().north());
    }

    private boolean isAir(BlockPos pos) {
        return level.getBlockState(pos).isAir();
    }

    public int getBrightness() {
        int time = (int) level.getDayTime() / 1000;
        if (time >= 0 && time <= 12) {
            return (time*time) * ((time - 12)*(time - 12));
        } else return 0;
    }

}
