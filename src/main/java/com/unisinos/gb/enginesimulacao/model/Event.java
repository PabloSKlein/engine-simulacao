package com.unisinos.gb.enginesimulacao.model;

import com.unisinos.gb.enginesimulacao.enumeration.EventEnum;

public class Event {
    private final Integer eventId;
    private final String name; //INSERT FILA1
    private final EventEnum eventType;

    public Event(int eventId, String name, EventEnum eventType) {
        this.eventId = eventId;
        this.name = name;
        this.eventType = eventType;
    }

    public String getName() {
        return name;
    }

    public Integer getEventId() {
        return eventId;
    }
}
