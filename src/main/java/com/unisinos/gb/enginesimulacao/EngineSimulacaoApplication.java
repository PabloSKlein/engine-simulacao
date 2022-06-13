package com.unisinos.gb.enginesimulacao;

import com.unisinos.gb.enginesimulacao.engine.Scheduler;
import com.unisinos.gb.enginesimulacao.enumeration.DistributionEnum;
import com.unisinos.gb.enginesimulacao.enumeration.QueueModeEnum;
import com.unisinos.gb.enginesimulacao.model.entity.EntitySet;
import com.unisinos.gb.enginesimulacao.model.event.Chegada;
import com.unisinos.gb.enginesimulacao.model.process.AtendimentoCaixa;
import com.unisinos.gb.enginesimulacao.model.resources.Balcao;
import com.unisinos.gb.enginesimulacao.model.resources.Caixa;
import com.unisinos.gb.enginesimulacao.model.resources.Mesa;
import com.unisinos.gb.enginesimulacao.model.process.BalcaoOuMesa;

import java.util.Arrays;

public class EngineSimulacaoApplication {
    static final Scheduler de = new Scheduler();
    public static final double TEMPO_INICIAL_SISTEMA = 0.0;

    public static void main(String[] args) {

        // Inicializa as filas
        var filaCaixa1 = new EntitySet(de.generateId(), "FILA CAIXA1", QueueModeEnum.FIFO, 100);
        var filaCaixa2 = new EntitySet(de.generateId(), "FILA CAIXA2", QueueModeEnum.FIFO, 100);
        var filaPedido = new EntitySet(de.generateId(), "FILA PEDIDO", QueueModeEnum.FIFO, 100);
        var filaBalcao = new EntitySet(de.generateId(), "FILA BALCAO", QueueModeEnum.FIFO, 100);
        var filaMesas = new EntitySet(de.generateId(), "FILA MESAS", QueueModeEnum.FIFO, 100);
        var filaSaida = new EntitySet(de.generateId(), "FILA MESAS", QueueModeEnum.FIFO, 100);
        adicionaFilasNoSchedulerParaLog(filaCaixa1, filaCaixa2, filaPedido, filaBalcao, filaMesas);

        // Recursos
        var recursoCaixa1 = new Caixa(de.generateId(), 1);
        var recursoCaixa2 = new Caixa(de.generateId(), 1);
        var recursoBalcao = new Balcao(de.generateId(), 6);
        Mesa[] recursoMesas = criaMesas();

        // Processos
        var atendimentoCaixa1 = new AtendimentoCaixa(de.generateId(), de, filaCaixa1, filaPedido, filaBalcao, filaMesas,
                recursoCaixa1);
        var atendimentoCaixa2 = new AtendimentoCaixa(de.generateId(), de, filaCaixa1, filaPedido, filaBalcao, filaMesas,
                recursoCaixa2);

        var balcaoOuMesa = new BalcaoOuMesa(de.generateId(), "Balcao Ou Mesa", de, DistributionEnum.EXPONENTIAL,
                filaMesas, filaBalcao, recursoMesas, recursoBalcao, null, filaSaida);

        // Cria os eventos de entrada
        criaChegadaPeloTempo(60, filaCaixa1, filaCaixa2);
        criaProcessosNoTempoZero(atendimentoCaixa1, atendimentoCaixa2 /* , balcaoOuMesa */);
        de.simulate();
    }

    private static void adicionaFilasNoSchedulerParaLog(EntitySet... filas) {
        Arrays.stream(filas).forEach(de::addEntitySet);
    }

    private static void criaProcessosNoTempoZero(AtendimentoCaixa... processos) {
        Arrays.stream(processos).forEach(processo -> de.scheduleAt(processo, TEMPO_INICIAL_SISTEMA));
        ;
    }

    public static void criaChegadaPeloTempo(double time, EntitySet filaCaixa1, EntitySet filaCaixa2) {
        for (int i = 3; i <= time; i = i + 3) {
            double timeArrival = DistributionEnum.EXPONENTIAL.getDistribution(0.0, 3.0);
            criaChegada(timeArrival, filaCaixa1, filaCaixa2);
        }
    }

    public static void criaChegada(double time, EntitySet filaCaixa1, EntitySet filaCaixa2) {
        de.scheduleAt(new Chegada(de.generateId(), filaCaixa1, filaCaixa2, de, 1.0), time);
    }

    public static Mesa[] criaMesas() {
        Mesa[] mesas = new Mesa[8];
        for (int i = 0; i < mesas.length; i++) {
            if (i < 4)
                mesas[i] = new Mesa(de.generateId(), "MESA " + i, 2);
            else
                mesas[i] = new Mesa(de.generateId(), "MESA " + i, 4);
        }
        return mesas;
    }
}
