package com.unisinos.gb.enginesimulacao.model.event;

import com.unisinos.gb.enginesimulacao.engine.Scheduler;

public class Saida extends Event {
    public Saida(int eventId, Scheduler scheduler) {
        super(eventId, "SAÍDA" + eventId, scheduler, 0.0);
    }

    @Override
    public void execute() {
        getScheduler().incrementSaida();
    }
    
}
