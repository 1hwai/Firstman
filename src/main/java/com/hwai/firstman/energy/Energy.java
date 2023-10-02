package com.hwai.firstman.energy;


public class Energy {
    private int amount;

    private Energy(int amount) {
        this.amount = amount;
    }

    public static Energy create(int amount) {
        return new Energy(amount);
    }

    public static Energy init() {
        return new Energy(0);
    }

    public void set(int amount) {
        this.amount = amount;
    }

    public void set(Energy energy) {
        set(energy.getAmount());
    }

    public int getAmount() {
        return amount;
    }

    public int getDelta(Energy e0, Energy e1) {
        return e0.getAmount() - e1.getAmount();
    }
}
