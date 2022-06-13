package com.unisinos.gb.enginesimulacao.model.event;

import com.unisinos.gb.enginesimulacao.engine.Scheduler;

public class Saida extends Event {
    public Saida(int eventId, Scheduler scheduler, double time) {
        super(eventId, "SAÍDA" + eventId, scheduler, time);
    }

    @Override
    public void execute() {
        getScheduler().incrementSaida();
    }
    
}
