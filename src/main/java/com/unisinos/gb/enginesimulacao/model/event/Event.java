package com.unisinos.gb.enginesimulacao.model.event;

import com.unisinos.gb.enginesimulacao.model.EntitySet;
import com.unisinos.gb.enginesimulacao.model.Executable;

public abstract class Event extends Executable {
    private final Integer eventId;
    private final String name;
    private final EntitySet fila1;
    private final EntitySet fila2;

    public Event(int eventId, String name, EntitySet fila1, EntitySet fila2) {
        this.eventId = eventId;
        this.name = name;
        this.fila1 = fila1;
        this.fila2 = fila2;
    }

    public String getName() {
        return name;
    }

    public Integer getEventId() {
        return eventId;
    }

    public abstract void  execute();

    public EntitySet getFila1() {
        return fila1;
    }

    public EntitySet getFila2() {
        return fila2;
    }
}
