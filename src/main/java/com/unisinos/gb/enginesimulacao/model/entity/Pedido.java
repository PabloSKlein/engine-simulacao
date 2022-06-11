package com.unisinos.gb.enginesimulacao.model.entity;

public class Pedido extends Entity {

    private final GrupoCliente grupoCliente;

    public Pedido(Integer id, String name, GrupoCliente grupoCliente) {
        super(id, name);
        this.grupoCliente = grupoCliente;
    }

    public Pedido(GrupoCliente grupoCliente) {
        super(1, "PEDIDO");
        this.grupoCliente = grupoCliente;
    }
}
