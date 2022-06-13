package com.unisinos.gb.enginesimulacao.model.process;

import com.unisinos.gb.enginesimulacao.engine.Scheduler;
import com.unisinos.gb.enginesimulacao.enumeration.DistributionEnum;
import com.unisinos.gb.enginesimulacao.model.entity.EntitySet;
import com.unisinos.gb.enginesimulacao.model.entity.GrupoCliente;
import com.unisinos.gb.enginesimulacao.model.resources.Balcao;
import com.unisinos.gb.enginesimulacao.model.resources.Mesa;

public class BalcaoOuMesa extends Process {

    private final Mesa[] mesas;
    private final Balcao balcao;
    private final EntitySet filaMesa;
    private final EntitySet filaSaida;
    private final EntitySet filaBalcao;
    private GrupoCliente grupo;

    public BalcaoOuMesa(Integer id, String name, Scheduler scheduler, DistributionEnum distributionEnum,
            EntitySet filaMesa,
            EntitySet filaBalcao, Mesa[] mesas, Balcao balcao, GrupoCliente grupo, EntitySet filaSaida) {
        super(id, name, scheduler, distributionEnum);
        this.mesas = mesas;
        this.balcao = balcao;
        this.filaMesa = filaMesa;
        this.filaSaida = filaSaida;
        this.filaBalcao = filaBalcao;
        this.grupo = grupo;
    }

    @Override
    public double getMin() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getMax() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void executeOnStart() {
        // TODO Auto-generated method stub

    }

    @Override
    public void executeOnEnd() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean deveProcessar() {
        // TODO Auto-generated method stub
        return false;
    }

}
