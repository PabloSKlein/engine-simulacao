package com.unisinos.gb.enginesimulacao;

import com.unisinos.gb.enginesimulacao.enumeration.QueueModeEnum;
import com.unisinos.gb.enginesimulacao.model.EntitySet;
import com.unisinos.gb.enginesimulacao.model.Scheduler;
import com.unisinos.gb.enginesimulacao.model.event.Chegada;

import java.util.ArrayList;
import java.util.List;

public class EngineSimulacaoApplication {

    public static void main(String[] args) {

        var de = new Scheduler();

        //var recursoCaixa1 = new Caixa(de.getId(), "CAIXA1", 1);
        //var processoAtendimentocaixa = new AtendimentoCaixa(de.getId(), "ATENDIMENTOCAIXA", de.normal(8.0, 2.0), filaCaixa1, recursoCaixa1);
        //de.startProcessAt(processoAtendimentocaixa, tempos.get(0));//Tempo que demora para executar

        var filaCaixa1 = new EntitySet(de.getId(), "FILACAIXA1", QueueModeEnum.FIFO, 10);
        var filaCaixa2 = new EntitySet(de.getId(), "FILACAIXA2", QueueModeEnum.FIFO, 10);

        //para fins de log
        de.addEntitySet(filaCaixa1);
        de.addEntitySet(filaCaixa2);
        new ArrayList<>(List.of(0.000997185, 0.009546665, 0.010303237))
                .forEach(it -> criaChegadaFila(de, filaCaixa1, filaCaixa2, it));

        de.simulate();
    }

    private static void criaChegadaFila(Scheduler de, EntitySet filaCaixa1, EntitySet filaCaixa2, double time) {
        de.scheduleAt(new Chegada(de.getId(), "CHEGADA", filaCaixa1, filaCaixa2), time);
    }
}
