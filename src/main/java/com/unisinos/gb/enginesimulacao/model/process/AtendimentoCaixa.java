package com.unisinos.gb.enginesimulacao.model.process;

import com.unisinos.gb.enginesimulacao.model.EntitySet;
import com.unisinos.gb.enginesimulacao.model.entity.Entity;
import com.unisinos.gb.enginesimulacao.model.resources.Resource;

public class AtendimentoCaixa extends Process {

    private final EntitySet entitySet;
    private Entity entity;
    private final Resource resource;

    public AtendimentoCaixa(int id, String name, Double duration, EntitySet entitySet, Resource resource) {
        super(id, name, duration);
        this.entitySet = entitySet;
        this.resource = resource;
    }

    @Override
    public void executeOnStart() {
        this.entity = entitySet.remove();
    }

    @Override
    public void executeOnEnd() {

    }
}
