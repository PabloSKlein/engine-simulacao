package com.unisinos.gb.enginesimulacao.model;

public class Event {
    private final String name;
    private final Integer eventId;

    public Event(String name) {
        this.name = name;
        this.eventId = 1;
    }

    public String getName() {
        return name;
    }

    public Integer getEventId() {
        return eventId;
    }
}
