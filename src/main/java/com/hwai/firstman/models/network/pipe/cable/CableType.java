package com.hwai.firstman.models.network.pipe.cable;

public enum CableType {
    THIN("thin"),
    NORMAL("normal"),
    THICK("thick");

    private final String id;

    CableType(String id) {
        this.id = id;
    }

    public String toString() {
        return id;
    }

    public int getMaxTransfer() {
        return 100;
    }

    public int getLoss() {
        return 2;
    }
}
