package com.unisinos.gb.enginesimulacao.model;

public class Resource {
    private final String name;
    private final int id;
    private final int quantity;

    Resource(String name, int quantity) {
        this.id = 0;
        this.name = name;
        this.quantity = quantity;
    }

    public boolean allocate() {
        return true;
    }

    public boolean release() {
        return false;
    }

    public double allocationRate() {
        return 0;
    }

    public double allocationAverage() {
        return 0;
    }
}
