package com.unisinos.gb.enginesimulacao.model.event;

import com.unisinos.gb.enginesimulacao.model.EntitySet;
import com.unisinos.gb.enginesimulacao.model.entity.GrupoCliente;
import com.unisinos.gb.enginesimulacao.model.entity.Pedido;

public class FazerPedido extends Event {

    private final GrupoCliente grupoCliente;

    public FazerPedido(int eventId, String name, EntitySet fila1, GrupoCliente grupoCliente) {
        super(eventId, name, fila1);
        this.grupoCliente = grupoCliente;
    }

    @Override
    public void execute() {
        getFila().insert(new Pedido(1, "Grupo Cliente", grupoCliente));
    }
}
