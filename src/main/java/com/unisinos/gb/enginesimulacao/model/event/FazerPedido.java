package com.unisinos.gb.enginesimulacao.model.event;

import com.unisinos.gb.enginesimulacao.model.EntitySet;
import com.unisinos.gb.enginesimulacao.engine.Scheduler;
import com.unisinos.gb.enginesimulacao.model.entity.GrupoCliente;
import com.unisinos.gb.enginesimulacao.model.entity.Pedido;

public class FazerPedido extends Event {

    private final EntitySet fila;
    private final GrupoCliente grupoCliente;

    public FazerPedido(int eventId, String name, EntitySet fila, GrupoCliente grupoCliente, Scheduler scheduler, double time) {
        super(eventId, name, scheduler, time);
        this.fila = fila;
        this.grupoCliente = grupoCliente;
    }

    @Override
    public void execute() {
        fila.insert(new Pedido(getScheduler().generateId(), grupoCliente));
    }
}
