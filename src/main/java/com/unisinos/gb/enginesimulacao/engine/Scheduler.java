package com.unisinos.gb.enginesimulacao.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.unisinos.gb.enginesimulacao.enumeration.DistributionEnum;
import com.unisinos.gb.enginesimulacao.model.entity.EntitySet;
import com.unisinos.gb.enginesimulacao.model.event.Event;
import com.unisinos.gb.enginesimulacao.model.process.Process;
import com.unisinos.gb.enginesimulacao.model.resources.Balcao;
import com.unisinos.gb.enginesimulacao.model.resources.Mesa;
import com.unisinos.gb.enginesimulacao.model.resources.Resource;

public class Scheduler {

	public static double meanValue = 2;
	public static double stdDeviationValue = 5;
	public static double minValue = 1;
	public static double maxValue = 6;
	private boolean processosLigados = true;

	private Double tempoAnterior = 0.0;
	private Double tempo = 0.0;
	private int id = 0;

	private int contCiclos = 0;
	private int contChegada = 0;
	private int contSaida = 0;

	// Adicionado listas
	private final List<Event> eventosAgendados = new ArrayList<>();
	private final List<EntitySet> entitySetList = new ArrayList<>();
	private final List<Resource> mesasDisponiveis = new ArrayList<>();
	private final List<Resource> balcaoDisponivel = new ArrayList<>();

	public List<Resource> getMesasDisponiveis() {
		return mesasDisponiveis;
	}

	public List<Resource> getBalcaoDisponivel() {
		return balcaoDisponivel;
	}

	public void addEntitySet(EntitySet entitySet) {
		this.entitySetList.add(entitySet);
	}

	public List<EntitySet> entitySetList() {
		return this.entitySetList;
	}

	public Double getTempo() {
		return tempo;
	}

	public void incrementChegada() {
		this.contChegada++;
	}

	public int getContChegada() {
		return this.contChegada;
	}

	public void incrementSaida() {
		this.contSaida++;
	}

	public int getContSaida() {
		return this.contSaida;
	}

	private List<Process> retornarSomenteProcessos() {
		return this.eventosAgendados.stream().filter(eventos -> eventos instanceof Process).map(e -> (Process) e).collect(Collectors.toList());
	}

	private List<Event> retornarSomenteEventosEventos() {
		return this.eventosAgendados.stream().filter(eventos -> (eventos instanceof Process) == false).collect(Collectors.toList());
	}

	// disparo de eventos e processos =============================================

	public void scheduleNow(Event event) {
		event.setTime(this.tempo);
		eventosAgendados.add(event);
	}

	public void scheduleIn(Event event, Double timeToEvent) {
		event.setTime(tempo + timeToEvent);
		eventosAgendados.add(event);
	}

	public boolean isProcessosLigados() {
		return processosLigados;
	}

	public void scheduleAt(Event event, Double absoluteTime) {
		event.setTime(absoluteTime);
		eventosAgendados.add(event);
	}

	public void startProcessNow(Process process) {
		process.setTime(tempo);
	}

	public void startProcessIn(Process process, Double timeToStart) {
		process.setTime(tempo + timeToStart);
	}

	public void startProcessAt(Process process, Double absoluteTime) {
		process.setTime(absoluteTime);
	}

	public String getMinimunSize(String value, Integer minimum) {
		value = (value == null ? "" : value);
		return (value.length() > minimum ? value : (value + (" ".repeat(minimum - value.length()))));
	}

	public void printLog() {
		System.out.println("=".repeat(100));
		System.out.println("     ========= CICLO " + this.contCiclos + " ======= TEMPO " + this.tempo + " ===== ENTRADAS " + this.contChegada + " ======= SAIDAS " + this.contSaida
				+ " =============");
		System.out.println("=".repeat(100));
		StringBuilder stb = new StringBuilder();
		// int totalAgendado = this.eventosAgendados.size();
		int totalEventos = this.retornarSomenteEventosEventos().size();
		int totalProcessos = this.retornarSomenteProcessos().size();
		// Eventos
		// stb.append("EVENTOS TOTAIS -> " + "|".repeat(totalAgendado) + " (" +
		// totalAgendado + ")\n");
		stb.append("EVENTOS    -> " + "|".repeat(totalEventos) + " (" + totalEventos + ")\n");
		stb.append("PROCESSOS  -> " + "|".repeat(totalProcessos) + " (" + totalProcessos + ")\n\n");
		// Filas
		this.entitySetList.forEach(fila -> {
			stb.append(fila);
			stb.append("\n");
		});
		stb.append("\n");
		// Processos
		this.retornarSomenteProcessos().forEach(processos -> {
			stb.append(processos);
			stb.append("\n");
		});
		stb.append("\n");
		System.out.println(stb);
	}

	/**
	 *  se a abordagem para especificação da passagem de tempo nos processos for
	 * explícita
	 */
	public void waitFor(long time) {

	}

	// controlando tempo de execução ===============================================

	/**
	 * executa até esgotar o modelo, isto é, até a engine não ter mais nada para
	 * processar (FEL vazia, i.e., lista de eventos futuros vazia)
	 */
	public void simulate() {
		while (!eventosAgendados.isEmpty()) {
			simulateOneStep();
		}
	}

	/*
	 * executa somente uma primitiva da API e interrompe execução; por ex.: dispara
	 * um evento e para; insere numa fila e para, etc.
	 */
	public void simulateOneStep() {
		if (this.contCiclos == 0) {
			printLog();
		}
		this.contCiclos++;
		if (!eventosAgendados.isEmpty()) {
			// Ajustado, nunca pode pegar coisas no passado.
			this.tempoAnterior = this.tempo;
			this.tempo = getProximoCiclo();
			// Adicionano ordenação por prioridade
			var eventosDoCiclo = eventosAgendados.stream().filter(it -> it.getTempo() <= this.tempo).sorted(Comparator.comparingInt(Event::getPriority).reversed())
					.collect(Collectors.toList());
			eventosDoCiclo.forEach(menorEvento -> {
				menorEvento.execute();
				// Verifca se este evento esta condicionado a remoção.
				if (menorEvento.isRemoveEvent()) {
					eventosAgendados.remove(menorEvento);
				}
			});
			printLog();
			processosLigados = this.manterProcessos();
		}
	}

	private boolean manterProcessos() {
		// Verifica se tem eventos não processos.
		boolean temEventosEventos = !retornarSomenteEventosEventos().isEmpty();
		if (temEventosEventos) {
			// Se tem eventos os processos seguem funcionando.
			return true;
		}
		// Se não tem eventos, tem que verificar se algum esta ativo (processando)
		if (retornarSomenteProcessos().stream().anyMatch(pro -> pro.isActive())) {
			return true;
		}
		return (this.contChegada > this.contSaida);
	}

	public void simulateBy(Double duration) {
		while (this.getTempo() <= duration) {
			simulateOneStep();
		}
	}

	public void simulateUntil(Double absoluteTime) {
		while (this.getTempo() < absoluteTime) {
			simulateOneStep();
		}
	}

	// criação, destruição e acesso para componentes
	// ===============================================

	public Integer generateId() {
		return ++id;
	}

	/*
	 * retorna referência para instancia de Event
	 */
	public Event getEvent(Integer eventId) throws Exception {
		return this.eventosAgendados.stream().filter(e -> e.getId().intValue() == eventId.intValue()).findAny().orElse(null);
	}

	/*
	 * retorna referência para instancia de EntitySet
	 */
	public EntitySet getEntitySet(Integer id) throws Exception {
		return this.entitySetList.stream().filter(esl -> esl.getId() == id.intValue()).findAny().orElse(null);
	}

	public Double getProximoCiclo() {
		Event even = eventosAgendados.stream().filter(event -> event.getTempo() > this.tempo).min(Comparator.comparing(Event::getTempo)).orElse(null);
		return even == null ? this.tempo : even.getTempo();
	}

	public void reAgendarProcesso(Integer processID, Double time) {
		Event eventProcess = eventosAgendados.stream().filter(proc -> proc.getId() == processID.intValue()).findAny().orElseThrow();
		if (!(eventProcess instanceof Process)) {
			throw new RuntimeException("Event " + eventProcess.getId() + " Nao e processo!!");
		}
		eventProcess.setTime(DistributionEnum.round(this.tempo + time, DistributionEnum.NUMBER_DECIMALS));
	}

	public void addMesas(Mesa... mesas) {
		this.mesasDisponiveis.addAll(Arrays.asList(mesas));
	}

	public void addBalcao(Balcao... balcaos) {
		this.balcaoDisponivel.addAll(Arrays.asList(balcaos));
	}
}