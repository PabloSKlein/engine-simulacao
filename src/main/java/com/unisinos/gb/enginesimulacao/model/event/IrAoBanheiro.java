package com.unisinos.gb.enginesimulacao.model.event;

import com.unisinos.gb.enginesimulacao.engine.Scheduler;
import com.unisinos.gb.enginesimulacao.model.entity.AtendenteCaixa;
import com.unisinos.gb.enginesimulacao.model.entity.EntitySet;

public class IrAoBanheiro extends Event {

    private final EntitySet filaBanheiro;

    public IrAoBanheiro(Integer id, String name, Scheduler scheduler, EntitySet filaBanheiro) {
        super(id, name, scheduler, 0);
        this.filaBanheiro = filaBanheiro;
    }

    @Override
    public void execute() {
        filaBanheiro.insert(new AtendenteCaixa(getScheduler().generateId()));
    }
}
