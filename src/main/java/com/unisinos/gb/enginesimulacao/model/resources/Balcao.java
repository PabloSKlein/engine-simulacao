package com.unisinos.gb.enginesimulacao.model.resources;

public class Balcao extends Resource {
    public Balcao(int id, String name, int quantidade) {
        super(id, name, quantidade);
    }

    public boolean isOccupied(){
        if(this.quantidade>=1)
            return false;
        else
            return true;
    }

    public void ocupaBanco(){
        --this.quantidade;
    }
}
