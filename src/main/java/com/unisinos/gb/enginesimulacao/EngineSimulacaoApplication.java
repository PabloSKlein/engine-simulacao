package com.unisinos.gb.enginesimulacao;

import com.unisinos.gb.enginesimulacao.enumeration.QueueModeEnum;
import com.unisinos.gb.enginesimulacao.model.EntitySet;
import com.unisinos.gb.enginesimulacao.model.Scheduler;
import com.unisinos.gb.enginesimulacao.model.event.Chegada;
import com.unisinos.gb.enginesimulacao.model.process.AtendimentoCaixa;
import com.unisinos.gb.enginesimulacao.model.resources.Caixa;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class EngineSimulacaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EngineSimulacaoApplication.class, args);

        Scheduler de = new Scheduler();

        var recursoCaixa1 = new Caixa(de.getId(), "CAIXA1", 1);
        var filaCaixa1 = new EntitySet(de.getId(), "FILACAIXA1", QueueModeEnum.FIFO, 10);

        var tempos = new ArrayList<>(List.of(0.000997185, 0.009546665, 0.010303237));

        tempos.forEach(it -> criaChegadaFila(de, recursoCaixa1, filaCaixa1, it));

        de.simulate();
    }

    private static void criaChegadaFila(Scheduler de, Caixa recursoCaixa1, EntitySet filaCaixa1, double time) {
        var eventoChegada = new Chegada(de.getId(), "CHEGADA", filaCaixa1);
        var processoAtendimentocaixa = new AtendimentoCaixa(de.getId(), "ATENDIMENTOCAIXA", de.normal(8.0, 2.0), filaCaixa1, recursoCaixa1);

        de.scheduleAt(eventoChegada, time);
        de.startProcessAt(processoAtendimentocaixa, time);
    }

}
