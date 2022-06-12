package com.unisinos.gb.enginesimulacao;

import com.unisinos.gb.enginesimulacao.enumeration.DistributionEnum;
import com.unisinos.gb.enginesimulacao.enumeration.QueueModeEnum;
import com.unisinos.gb.enginesimulacao.model.EntitySet;
import com.unisinos.gb.enginesimulacao.engine.Scheduler;
import com.unisinos.gb.enginesimulacao.model.event.Chegada;
import com.unisinos.gb.enginesimulacao.model.process.AtendimentoCaixa;
import com.unisinos.gb.enginesimulacao.model.resources.Caixa;
import com.unisinos.gb.enginesimulacao.model.resources.Mesa;

import java.util.ArrayList;
import java.util.List;

public class EngineSimulacaoApplication {
    static final Scheduler de = new Scheduler();
    public static final double TEMPO_INICIAL_SISTEMA = 0.0;

    public static void main(String[] args) {

        // Inicializa as filas
        var filaBalcao = new EntitySet(de.generateId(), "FILA BALCAO", QueueModeEnum.FIFO, 100);
        var filaMesas = new EntitySet(de.generateId(), "FILA MESAS", QueueModeEnum.FIFO, 100);
        var filaCaixa1 = new EntitySet(de.generateId(), "FILA CAIXA 1", QueueModeEnum.FIFO, 100);
        var filaCaixa2 = new EntitySet(de.generateId(), "FILA CAIXA 2", QueueModeEnum.FIFO, 100);
        var filaPedido = new EntitySet(de.generateId(), "FILA PEDIDO", QueueModeEnum.FIFO, 100);

        // Recursos
        var recursoCaixa1 = new Caixa(de.generateId(), 1);
        var recursoCaixa2 = new Caixa(de.generateId(), 1);
        //var balcao = new Balcao(de.generateId(), 6);
        //var mesas = criaMesas();

        // Processos
        var atendimentoCaixa1 = new AtendimentoCaixa(de.generateId(), de, filaCaixa1, filaPedido, filaBalcao, filaMesas, recursoCaixa1);
        var atendimentoCaixa2 = new AtendimentoCaixa(de.generateId(), de, filaCaixa1, filaPedido, filaBalcao, filaMesas, recursoCaixa2);

        // Cria os eventos de entrada
        criaChegadaPeloTempo(60, filaCaixa1, filaCaixa2);
        criaProcessoNoTempoZero(atendimentoCaixa1);
        criaProcessoNoTempoZero(atendimentoCaixa2);
        de.simulate();
    }

    private static void criaProcessoNoTempoZero(AtendimentoCaixa atendimentoCaixa1) {
        de.scheduleAt(atendimentoCaixa1, TEMPO_INICIAL_SISTEMA);
    }

    public static void criaChegadaPeloTempo(double time, EntitySet filaCaixa1, EntitySet filaCaixa2) {
        for (int i = 3; i <= time; i = i + 3) {
            double timeArrival = DistributionEnum.EXPONENTIAL.getDistribution(3.0, 0.0);
            criaChegada(timeArrival, filaCaixa1, filaCaixa2);
        }
    }

    public static void criaChegada(double time, EntitySet filaCaixa1, EntitySet filaCaixa2) {
        de.scheduleAt(new Chegada(de.generateId(), filaCaixa1, filaCaixa2, de, 1.0), time);
    }

    private static List<Mesa> criaMesas() {
        return new ArrayList<Mesa>();
    }
}

