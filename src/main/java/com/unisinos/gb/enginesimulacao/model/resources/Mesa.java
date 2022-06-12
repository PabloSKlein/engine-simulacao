package com.unisinos.gb.enginesimulacao.model.resources;

public class Mesa extends Resource {
    public Mesa(int id, String name, int quantidade) {
        super(id, name, quantidade);
    }

    public boolean isOccupied(){
        return this.podeAlocarRecurso();
    }

    public void ocupaMesa(){
        this.allocate();
    }

    public void desocupaMesa(){
        this.release();
    }
}
