package com.unisinos.gb.enginesimulacao.model.event;

public class Event {
    private final Integer eventId;
    private final String name;

    public Event(int eventId, String name) {
        this.eventId = eventId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getEventId() {
        return eventId;
    }
}
