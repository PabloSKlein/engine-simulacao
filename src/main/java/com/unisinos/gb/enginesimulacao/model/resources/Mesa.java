package com.unisinos.gb.enginesimulacao.model.resources;

public class Mesa extends Resource {
    private int isOccupied = 0;

    public Mesa(int id, String name, int quantidade) {
        super(id, name, quantidade);
    }

    public int getIsOccupied() {
        return isOccupied;
    }

    public void setIsOccupied(int isOccupied) {
        this.isOccupied = isOccupied;
    }
}
