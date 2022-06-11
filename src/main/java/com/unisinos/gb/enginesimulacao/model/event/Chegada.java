package com.unisinos.gb.enginesimulacao.model.event;

import com.unisinos.gb.enginesimulacao.model.EntitySet;
import com.unisinos.gb.enginesimulacao.model.entity.GrupoCliente;

public class Chegada extends Event {
    private final EntitySet fila2;

    public Chegada(int eventId, String name, EntitySet fila1, EntitySet fila2) {
        super(eventId, name, fila1);
        this.fila2 = fila2;
    }

    @Override
    public void execute() {
        getMenorFila().insert(new GrupoCliente(1, "Grupo Cliente"));
    }

    private EntitySet getMenorFila() {
        return getFila().getSize() <= fila2.getSize() ? getFila() : fila2;
    }
}
