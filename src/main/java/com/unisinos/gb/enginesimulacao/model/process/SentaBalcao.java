package com.unisinos.gb.enginesimulacao.model.process;

import com.unisinos.gb.enginesimulacao.enumeration.DistributionEnum;
import com.unisinos.gb.enginesimulacao.model.EntitySet;
import com.unisinos.gb.enginesimulacao.model.Scheduler;
import com.unisinos.gb.enginesimulacao.model.entity.GrupoCliente;
import com.unisinos.gb.enginesimulacao.model.resources.Balcao;

public class SentaBalcao extends Process {

    Balcao balcao;
    EntitySet filaBalcao;
    EntitySet filaSaida;
    GrupoCliente grupo;

    public SentaBalcao(Integer id, String name, Scheduler scheduler, double time, boolean active,
            DistributionEnum enumDuration, Balcao balcao, EntitySet filaBalcao, EntitySet filaSaida) {
        super(id, name, scheduler, time, active, enumDuration);
        this.balcao = balcao;
        this.filaBalcao = filaBalcao;
        this.filaSaida = filaSaida;
    }

    @Override
    public void executeOnStart() {
        temBalcao();

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

    private void temBalcao() {
        if (balcao.isOccupied() == false) {
            balcao.ocupaBanco();
            this.filaSaida.insert(this.grupo);
            // Time de saida
            // Faço o que com o grupo de pessoas?
        } else {
            filaBalcao.insert(this.grupo);
        }
    }

}
