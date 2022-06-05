package com.unisinos.gb.enginesimulacao.model;

public class Process {
    private final String name;
    private final Integer processId;
    private final Double duration;
    private boolean active;

    Process(String name, Double duration) {
        this.name = name;
        this.duration = duration;
        this.processId = 1;
    }

    public String getName() {
        return name;
    }

    public Integer getProcessId() {
        return processId;
    }

    public Double getDuration() {
        return duration;
    }

    public boolean isActive() {
        return active;
    }

    public void activate(boolean active) {
        this.active = active;
    }
}
