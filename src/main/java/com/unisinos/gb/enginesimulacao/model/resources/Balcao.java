package com.unisinos.gb.enginesimulacao.model.resources;

public class Balcao extends Resource {

    public Balcao(int id, String name, int quantidade) {
        super(id, name, quantidade);
    }

    public boolean isOccupied(){
        return this.podeAlocarRecurso();
    }

    public void ocupaBanco(){
        this.allocate();
    }

    public void desocupaBanco(){
        this.release();
    }
}
