package com.unisinos.gb.enginesimulacao.model.event;

import com.unisinos.gb.enginesimulacao.model.EntitySet;
import com.unisinos.gb.enginesimulacao.model.Executable;

public abstract class Event extends Executable {
    private final Integer eventId;
    private final String name;
    private final EntitySet fila;

    protected Event(int eventId, String name, EntitySet fila) {
        this.eventId = eventId;
        this.name = name;
        this.fila = fila;
    }

    public String getName() {
        return name;
    }

    public Integer getEventId() {
        return eventId;
    }

    public abstract void  execute();

    public EntitySet getFila() {
        return fila;
    }

}
