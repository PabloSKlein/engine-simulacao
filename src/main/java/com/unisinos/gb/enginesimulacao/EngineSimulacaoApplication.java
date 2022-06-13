package com.unisinos.gb.enginesimulacao;

import java.util.Arrays;

import com.unisinos.gb.enginesimulacao.engine.Scheduler;
import com.unisinos.gb.enginesimulacao.enumeration.DistributionEnum;
import com.unisinos.gb.enginesimulacao.enumeration.QueueModeEnum;
import com.unisinos.gb.enginesimulacao.model.entity.EntitySet;
import com.unisinos.gb.enginesimulacao.model.event.Chegada;
import com.unisinos.gb.enginesimulacao.model.event.Saida;
import com.unisinos.gb.enginesimulacao.model.process.AtendimentoCaixa;
import com.unisinos.gb.enginesimulacao.model.process.PreparaRefeicao;
import com.unisinos.gb.enginesimulacao.model.process.Process;
import com.unisinos.gb.enginesimulacao.model.process.Refeicao;
import com.unisinos.gb.enginesimulacao.model.process.SentarMesaBalcao;
import com.unisinos.gb.enginesimulacao.model.resources.Balcao;
import com.unisinos.gb.enginesimulacao.model.resources.Caixa;
import com.unisinos.gb.enginesimulacao.model.resources.Cozinheiro;
import com.unisinos.gb.enginesimulacao.model.resources.Mesa;

public class EngineSimulacaoApplication {
	static final Scheduler de = new Scheduler();
	public static final double TEMPO_INICIAL_SISTEMA = 0.0;

	private static void adicionaFilasNoSchedulerParaLog(EntitySet... filas) {
		Arrays.stream(filas).forEach(de::addEntitySet);
	}

	private static void criaProcessosNoTempoZero(Process... processos) {
		Arrays.stream(processos).forEach(processo -> de.scheduleAt(processo, TEMPO_INICIAL_SISTEMA));
	}

	public static void criaChegadaPeloTempo(double time, EntitySet filaCaixa1, EntitySet filaCaixa2) {
		for (int i = 3; i <= time; i = i + 3) {
			double timeArrival = DistributionEnum.EXPONENTIAL.getDistribution(null, null, 3.0, null);
			criaChegada(timeArrival, filaCaixa1, filaCaixa2);
		}
	}

	public static void criaChegada(double time, EntitySet filaCaixa1, EntitySet filaCaixa2) {
		de.scheduleAt(new Chegada(de.generateId(), filaCaixa1, filaCaixa2, de), time);
	}

	public static void criaSaida(double time) {
		de.scheduleAt(new Saida(de.generateId(), de), time);
	}

	public static void main(String[] args) {

		var mesa1 = new Mesa(1, "Teste Mesa 1", 4);
		var mesa2 = new Mesa(2, "Teste Mesa 2", 2);
		var mesa3 = new Mesa(3, "Teste Mesa 3", 2);

		var balcao1 = new Balcao(1, 1);
		var balcao2 = new Balcao(2, 2);
		var balcao3 = new Balcao(3, 3);
		var balcao4 = new Balcao(4, 4);
		var balcao5 = new Balcao(5, 5);
		var balcao6 = new Balcao(6, 6);

		de.addMesas(mesa1, mesa2, mesa3);
		de.addBalcao(balcao1, balcao2, balcao3, balcao4, balcao5, balcao6);

		// Inicializa as filas
		var filaCaixa1 = new EntitySet(de.generateId(), "CAIXA 1", QueueModeEnum.FIFO, 100);
		var filaCaixa2 = new EntitySet(de.generateId(), "CAIXA 2", QueueModeEnum.FIFO, 100);
		var filaPedido = new EntitySet(de.generateId(), "PEDIDOS", QueueModeEnum.FIFO, 100);
		var filaPedidosPreparados = new EntitySet(de.generateId(), "PRONTOS", QueueModeEnum.FIFO, 100);
		var filaBalcao = new EntitySet(de.generateId(), "BALCÃO ", QueueModeEnum.FIFO, 100);
		var filaMesas = new EntitySet(de.generateId(), "MESAS  ", QueueModeEnum.FIFO, 100);
		adicionaFilasNoSchedulerParaLog(filaCaixa1, filaCaixa2, filaPedido, filaPedidosPreparados, filaBalcao, filaMesas);

		// Recursos
		var recursoCaixa1 = new Caixa(de.generateId(), 1);
		var recursoCaixa2 = new Caixa(de.generateId(), 1);
		var cozinheiros = new Cozinheiro(de.generateId(), 3);

		// Processos
		var atendimentoCaixa1 = new AtendimentoCaixa(de.generateId(), de, filaCaixa1, filaPedido, filaBalcao, filaMesas, recursoCaixa1);
		var atendimentoCaixa2 = new AtendimentoCaixa(de.generateId(), de, filaCaixa2, filaPedido, filaBalcao, filaMesas, recursoCaixa2);
		var prepararComida1 = new PreparaRefeicao(de.generateId(), de, filaPedido, filaPedidosPreparados, cozinheiros);
		var prepararComida2 = new PreparaRefeicao(de.generateId(), de, filaPedido, filaPedidosPreparados, cozinheiros);
		var prepararComida3 = new PreparaRefeicao(de.generateId(), de, filaPedido, filaPedidosPreparados, cozinheiros);
		var sentarMesa = new SentarMesaBalcao(de.generateId(), de, filaMesas, de.getMesasDisponiveis());
		var sentarBalcao = new SentarMesaBalcao(de.generateId(), de, filaBalcao, de.getBalcaoDisponivel());

		// Cria os eventos de entrada
		criaChegadaPeloTempo(180, filaCaixa1, filaCaixa2);
		criaProcessosNoTempoZero(atendimentoCaixa1, atendimentoCaixa2, sentarMesa, sentarBalcao, prepararComida1, prepararComida2, prepararComida3);

		// Criar processos de mesas
		de.getMesasDisponiveis().forEach(md -> {
			var refeicaoMesa = new Refeicao(de.generateId(), de, "   MESA ", filaPedidosPreparados, md);
			criaProcessosNoTempoZero(refeicaoMesa);
		});

		de.getBalcaoDisponivel().forEach(md -> {
			var refeicaoBalcao = new Refeicao(de.generateId(), de, " BALCAO ", filaPedidosPreparados, md);
			criaProcessosNoTempoZero(refeicaoBalcao);
		});

		de.simulate();
		// de.simulateBy(100.0);
		System.out.println("=".repeat(100));
		System.out.println("=".repeat(100));
		System.out.println("=".repeat(100));
		System.out.println("\nSIMULAÇÃO FINALIZADA.");
	}

}
