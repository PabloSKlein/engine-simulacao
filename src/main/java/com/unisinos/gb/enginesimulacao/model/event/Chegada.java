package com.unisinos.gb.enginesimulacao.model.event;

import com.unisinos.gb.enginesimulacao.model.EntitySet;
import com.unisinos.gb.enginesimulacao.model.entity.GrupoCliente;

public class Chegada extends Event {
    public Chegada(int eventId, String name, EntitySet fila1, EntitySet fila2) {
        super(eventId, name, fila1, fila2);
    }

    @Override
    public void execute() {
        getMenorFila().insert(new GrupoCliente(1, "Grupo Cliente"));
    }

    private EntitySet getMenorFila() {
        return getFila1().getSize() <= getFila2().getSize() ? getFila1() : getFila2();
    }
}
