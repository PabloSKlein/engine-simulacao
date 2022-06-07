package com.unisinos.gb.enginesimulacao.model.resources;

public class Resource {
    private final String name;
    private final int id;
    private final int quantity;

    public Resource(int id, String name, int quantity) {
        this.id = id;
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

    public Integer getId() {
        return this.id;
    }
}
