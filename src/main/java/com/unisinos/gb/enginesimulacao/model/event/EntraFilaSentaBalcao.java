package com.unisinos.gb.enginesimulacao.model.event;

import com.unisinos.gb.enginesimulacao.engine.Scheduler;
import com.unisinos.gb.enginesimulacao.model.entity.EntitySet;
import com.unisinos.gb.enginesimulacao.model.entity.GrupoCliente;
import com.unisinos.gb.enginesimulacao.model.resources.Balcao;

public class EntraFilaSentaBalcao extends Event {

    private final Balcao balcao;
    private final EntitySet filaBalcao;
    private final EntitySet filaSaida;
    private final GrupoCliente grupo;

    protected EntraFilaSentaBalcao(Integer id, String name, Scheduler scheduler, double time, Balcao balcao,
            EntitySet filaBalcao, GrupoCliente grupo, EntitySet filaSaida) {
        super(id, name, scheduler, time);
        this.balcao = balcao;
        this.filaBalcao = filaBalcao;
        this.grupo = grupo;
        this.filaSaida = filaSaida;
    }

    @Override
    public void execute() {
        temBalcao();

    }

    private void temBalcao() {
        if (balcao.isOccupied() == false) {
            balcao.ocupaBanco();
            this.filaSaida.insert(this.grupo);
            // Time de saida
        } else {
            filaBalcao.insert(this.grupo);
        }
    }

}
