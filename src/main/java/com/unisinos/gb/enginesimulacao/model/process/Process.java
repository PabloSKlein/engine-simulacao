package com.unisinos.gb.enginesimulacao.model.process;

import com.unisinos.gb.enginesimulacao.model.Executable;

public abstract class Process extends Executable {
    private final String name;
    private final Integer processId;
    private final Double duration;
    private boolean active;

    public Process(int id, String name, Double duration) {
        this.processId = id;
        this.name = name;
        this.duration = duration;
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

    public abstract void executeOnStart();

    public abstract void executeOnEnd();

    public void excute() {
        this.executeOnStart();

        try {
            Thread.sleep(duration.longValue());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.executeOnEnd();
    }
}
