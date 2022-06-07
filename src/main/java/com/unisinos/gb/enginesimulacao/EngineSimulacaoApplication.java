package com.unisinos.gb.enginesimulacao;

import com.unisinos.gb.enginesimulacao.enumeration.QueueModeEnum;
import com.unisinos.gb.enginesimulacao.model.EntitySet;
import com.unisinos.gb.enginesimulacao.model.Scheduler;
import com.unisinos.gb.enginesimulacao.model.event.Chegada;
import com.unisinos.gb.enginesimulacao.model.process.AtendimentoCaixa;
import com.unisinos.gb.enginesimulacao.model.resources.Caixa;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EngineSimulacaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EngineSimulacaoApplication.class, args);


        Scheduler de = new Scheduler();

        var caixa1 = new Caixa(1, "CAIXA1", 1);
        de.createResource(caixa1);

        var filaCaixa1 = new EntitySet(1, "FILACAIXA1", QueueModeEnum.FIFO, 10);
        de.createEntitySet(filaCaixa1);

        var eventoChegada = new Chegada(1, "CHEGADA", filaCaixa1);
        de.createEvent(eventoChegada);

        var atendimentocaixa = new AtendimentoCaixa(1, "ATENDIMENTOCAIXA", de.normal(8.0, 2.0), filaCaixa1, caixa1);
        de.createProcess(atendimentocaixa);

        de.scheduleNow(eventoChegada);
        de.startProcessNow(atendimentocaixa.getProcessId());

        de.simulate();
    }

}
