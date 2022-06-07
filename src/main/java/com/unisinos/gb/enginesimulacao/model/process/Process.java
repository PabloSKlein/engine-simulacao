package com.unisinos.gb.enginesimulacao.model.process;

public abstract class Process {
    private final String name;
    private final Integer processId;
    private final Long duration;
    private boolean active;

    public Process(int id, String name, Long duration) {
        this.name = name;
        this.duration = duration;
        this.processId = id;
    }

    public String getName() {
        return name;
    }

    public Integer getProcessId() {
        return processId;
    }

    public Long getDuration() {
        return duration;
    }

    public boolean isActive() {
        return active;
    }

    public void activate(boolean active) {
        this.active = active;
    }

    public abstract void executeOnStart();

    public abstract void executeOnEnd();

    public void excute() {
        this.executeOnStart();

        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.executeOnEnd();
    }
}
