package com.unisinos.gb.enginesimulacao;

import java.util.Arrays;

import com.unisinos.gb.enginesimulacao.engine.Scheduler;
import com.unisinos.gb.enginesimulacao.enumeration.DistributionEnum;
import com.unisinos.gb.enginesimulacao.enumeration.QueueModeEnum;
import com.unisinos.gb.enginesimulacao.model.entity.EntitySet;
import com.unisinos.gb.enginesimulacao.model.event.Chegada;
import com.unisinos.gb.enginesimulacao.model.event.IrAoBanheiro;
import com.unisinos.gb.enginesimulacao.model.event.Saida;
import com.unisinos.gb.enginesimulacao.model.process.AtendimentoCaixa;
import com.unisinos.gb.enginesimulacao.model.process.PreparaRefeicao;
import com.unisinos.gb.enginesimulacao.model.process.Process;
import com.unisinos.gb.enginesimulacao.model.process.Refeicao;
import com.unisinos.gb.enginesimulacao.model.process.SentarMesaBalcao;
import com.unisinos.gb.enginesimulacao.model.process.UsarBanheiro;
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

	private static void criaIdaAoBanheiroPeloTempo(double time, EntitySet filaBanheiro) {
		double teste = 0.0;
		for (int i = (int) time / 4; i <= time; i += (int) time / 4) {
			double timeArrival = DistributionEnum.UNIFORM.getDistribution(teste, ((double) i), null, null);
			criaIdaAoBanheiro(timeArrival, filaBanheiro);
			teste = i;
		}
	}

	public static void criaChegada(double time, EntitySet filaCaixa1, EntitySet filaCaixa2) {
		de.scheduleAt(new Chegada(de.generateId(), filaCaixa1, filaCaixa2, de), time);
	}

	public static void criaIdaAoBanheiro(double time, EntitySet filaBanheiro) {
		de.scheduleAt(new IrAoBanheiro(de.generateId(), "IDABANHEIRO", de, filaBanheiro), time);
	}

	public static void criaSaida(double time) {
		de.scheduleAt(new Saida(de.generateId(), de), time);
	}

	public static void main(String[] args) {

		var mesa1 = new Mesa(de.generateId(), "Teste Mesa 1", 4);
		var mesa2 = new Mesa(de.generateId(), "Teste Mesa 2", 2);
		var mesa3 = new Mesa(de.generateId(), "Teste Mesa 3", 2);
		var mesa4 = new Mesa(de.generateId(), "Teste Mesa 4", 4);
		var mesa5 = new Mesa(de.generateId(), "Teste Mesa 5", 4);
		var mesa6 = new Mesa(de.generateId(), "Teste Mesa 6", 4);
		var mesa7 = new Mesa(de.generateId(), "Teste Mesa 7", 4);
		var mesa8 = new Mesa(de.generateId(), "Teste Mesa 8", 2);

		var balcao1 = new Balcao(de.generateId(), 1);
		var balcao2 = new Balcao(de.generateId(), 1);
		var balcao3 = new Balcao(de.generateId(), 1);
		var balcao4 = new Balcao(de.generateId(), 1);
		var balcao5 = new Balcao(de.generateId(), 1);
		var balcao6 = new Balcao(de.generateId(), 1);

		de.addMesas(mesa1, mesa2, mesa3, mesa4, mesa5, mesa6, mesa7, mesa8);
		de.addBalcao(balcao1, balcao2, balcao3, balcao4, balcao5, balcao6);

		// Inicializa as filas
		var filaCaixa1 = new EntitySet(de.generateId(), "CAIXA 1 ", QueueModeEnum.FIFO, 200, de);
		var filaCaixa2 = new EntitySet(de.generateId(), "CAIXA 2 ", QueueModeEnum.FIFO, 200, de);
		var filaPedido = new EntitySet(de.generateId(), "PEDIDOS ", QueueModeEnum.FIFO, 100, de);
		var filaPedidosPreparados = new EntitySet(de.generateId(), "PRONTOS ", QueueModeEnum.FIFO, 100, de);
		var filaBalcao = new EntitySet(de.generateId(), "BALCÃO  ", QueueModeEnum.FIFO, 100, de);
		var filaMesas = new EntitySet(de.generateId(), "MESAS   ", QueueModeEnum.FIFO, 100, de);
		var filaBanheiro = new EntitySet(de.generateId(), "BANHEIRO", QueueModeEnum.FIFO, 100, de);
		adicionaFilasNoSchedulerParaLog(filaCaixa1, filaCaixa2, filaPedido, filaPedidosPreparados, filaBalcao, filaMesas, filaBanheiro);

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
		var usarBanheiroCaixa1 = new UsarBanheiro(de.generateId(), de, filaBanheiro, recursoCaixa1);
		var usarBanheiroCaixa2 = new UsarBanheiro(de.generateId(), de, filaBanheiro, recursoCaixa2);

		// Cria os eventos de entrada
		criaChegadaPeloTempo(750, filaCaixa1, filaCaixa2);
		// Cria os eventos de ir ao banheiro
		criaIdaAoBanheiroPeloTempo(250, filaBanheiro);

		criaProcessosNoTempoZero(atendimentoCaixa1, atendimentoCaixa2, sentarMesa, sentarBalcao, prepararComida1, prepararComida2, prepararComida3, usarBanheiroCaixa1,
				usarBanheiroCaixa2);

		// Criar processos de mesas
		de.getMesasDisponiveis().forEach(md -> {
			var refeicaoMesa = new Refeicao(de.generateId(), de, "   MESA ", filaPedidosPreparados, md);
			criaProcessosNoTempoZero(refeicaoMesa);
		});

		de.getBalcaoDisponivel().forEach(md -> {
			var refeicaoBalcao = new Refeicao(de.generateId(), de, " BALCAO ", filaPedidosPreparados, md);
			criaProcessosNoTempoZero(refeicaoBalcao);
		});

		// de.simulate();
		de.simulateBy(180.0);
		// de.simulateUntil(100.0);
		System.out.println("=".repeat(100));
		System.out.println("=".repeat(100));
		System.out.println("=".repeat(100));
		System.out.println("\nSIMULAÇÃO FINALIZADA.");

		System.out.println("TEMPO MÉDIO DAS FILAS: ");
		System.out.println("FILA CAIXA 1 -> " + DistributionEnum.round(filaCaixa1.mediaEsperaFila(), DistributionEnum.NUMBER_DECIMALS));
		System.out.println("FILA CAIXA 2 -> " + DistributionEnum.round(filaCaixa2.mediaEsperaFila(), DistributionEnum.NUMBER_DECIMALS));
		System.out.println("FILA DE PEDIDOS -> " + DistributionEnum.round(filaPedido.mediaEsperaFila(), DistributionEnum.NUMBER_DECIMALS));
		System.out.println("FILA DE PEDIDOS PRONTOS -> " + DistributionEnum.round(filaPedidosPreparados.mediaEsperaFila(), DistributionEnum.NUMBER_DECIMALS));
		System.out.println("FILA BALCOES -> " + DistributionEnum.round(filaBalcao.mediaEsperaFila(), DistributionEnum.NUMBER_DECIMALS));
		System.out.println("FILA MESAS -> " + DistributionEnum.round(filaMesas.mediaEsperaFila(), DistributionEnum.NUMBER_DECIMALS));
		// System.out.println("FILA BANHEIRO -> " +
		// DistributionEnum.round(filaBanheiro.mediaEsperaFila(),
		// DistributionEnum.NUMBER_DECIMALS));
	}

}
