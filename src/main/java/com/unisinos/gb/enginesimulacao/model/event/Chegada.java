package com.unisinos.gb.enginesimulacao.model.event;

import com.unisinos.gb.enginesimulacao.model.EntitySet;

public class Chegada extends Event {
    private final EntitySet entitySet;

    public Chegada(int eventId, String name, EntitySet entitySet) {
        super(eventId, name);
        this.entitySet = entitySet;
    }
}
