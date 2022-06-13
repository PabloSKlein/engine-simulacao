package com.unisinos.gb.enginesimulacao.model.process;

import com.unisinos.gb.enginesimulacao.engine.Scheduler;
import com.unisinos.gb.enginesimulacao.enumeration.DistributionEnum;
import com.unisinos.gb.enginesimulacao.model.entity.EntitySet;
import com.unisinos.gb.enginesimulacao.model.resources.Caixa;

public class UsarBanheiro extends Process {

    private final EntitySet filaBanheiro;
    private final Caixa caixa;

    public UsarBanheiro(Integer id, Scheduler scheduler, EntitySet filaBanheiro, Caixa caixa) {
        super(id, "USARBANHEIRO " + id, scheduler, DistributionEnum.UNIFORM);
        this.filaBanheiro = filaBanheiro;
        this.caixa = caixa;
    }

    @Override
    public Double getMin() {
        return 1.0;
    }

    @Override
    public Double getMax() {
        return 5.0;
    }

    @Override
    public Double getMean() {
        return 0.0;
    }

    @Override
    public Double getStdDeviation() {
        return 0.0;
    }

    @Override
    protected void executeOnStart() {
        filaBanheiro.remove();
        this.caixa.allocate();
    }

    @Override
    protected void executeOnEnd() {
        this.caixa.release();
    }

    @Override
    public boolean deveProcessar() {
        return caixa.podeAlocarRecurso() && !filaBanheiro.isEmpty();
    }
}
