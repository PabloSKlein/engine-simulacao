package com.unisinos.gb.enginesimulacao.model.event;

import com.unisinos.gb.enginesimulacao.engine.Scheduled;
import com.unisinos.gb.enginesimulacao.engine.Scheduler;

public abstract class Event extends Scheduled {
    private double time;

    protected Event(Integer id, String name, Scheduler scheduler, double time) {
        super(id, name, scheduler);
        this.time = time;
    }

    public double getTempo() {
        return time;
    }

    public abstract void execute();

    public void setTime(double time) {
        this.time = time;
    }

}
