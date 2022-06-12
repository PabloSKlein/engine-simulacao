package com.unisinos.gb.enginesimulacao.model.entity;

public class Pedido extends Entity {

    private final GrupoCliente grupoCliente;

    public Pedido(int id, GrupoCliente grupoCliente) {
        super(id, "PEDIDO" + id);
        this.grupoCliente = grupoCliente;
    }
}
