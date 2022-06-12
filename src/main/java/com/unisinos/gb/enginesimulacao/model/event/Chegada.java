package com.unisinos.gb.enginesimulacao.model.event;

import com.unisinos.gb.enginesimulacao.model.EntitySet;
import com.unisinos.gb.enginesimulacao.engine.Scheduler;
import com.unisinos.gb.enginesimulacao.model.entity.GrupoCliente;

public class Chegada extends Event {
    private final EntitySet fila1;
    private final EntitySet fila2;

    public Chegada(int eventId, EntitySet fila1, EntitySet fila2, Scheduler scheduler, double time) {
        super(eventId, "CHEGADA" + eventId, scheduler, time);
        this.fila1 = fila1;
        this.fila2 = fila2;
    }

    @Override
    public void execute() {
        getMenorFila().insert(new GrupoCliente(getScheduler().generateId()));
    }

    private EntitySet getMenorFila() {
        return fila1.getSize() <= fila2.getSize() ? fila1 : fila2;
    }
}
