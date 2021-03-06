package com.unisinos.gb.enginesimulacao.engine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.unisinos.gb.enginesimulacao.model.entity.EntitySet;
import com.unisinos.gb.enginesimulacao.model.event.Event;
import com.unisinos.gb.enginesimulacao.model.process.Process;

public class Scheduler {

	public static double meanValue = 2;
	public static double stdDeviationValue = 5;
	public static double minValue = 1;
	public static double maxValue = 6;

	private Double tempo = 0.0;
	private int id = 0;

	private int contCiclos = 0;
	private int contChegada = 0;
	private int contSaida = 0;

	// Adicionado listas
	private final List<Event> eventosAgendados = new ArrayList<>();
	private final List<EntitySet> entitySetList = new ArrayList<>();

	public void addEntitySet(EntitySet entitySet) {
		this.entitySetList.add(entitySet);
	}

	public List<EntitySet> entitySetList() {
		return this.entitySetList;
	}

	public Double getTempo() {
		return tempo;
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

	public void scheduleAt(Event event, Double absoluteTime) {
		event.setTime(absoluteTime);
		eventosAgendados.add(event);
	}

	public void startProcessNow(Process process) {
		process.setTime(tempo);
		// processosAgendados.add(process);
	}

	public void startProcessIn(Process process, Double timeToStart) {
		process.setTime(tempo + timeToStart);
		// processosAgendados.add(process);
	}

	public void startProcessAt(Process process, Double absoluteTime) {
		process.setTime(absoluteTime);
		// processosAgendados.add(process);
	}

	public void printLog() {
		System.out.println("=============== CICLO " + this.contCiclos + " ======== TEMPO " + this.tempo + "=========================");
		// Filas
		StringBuilder stb = new StringBuilder();
		this.entitySetList.forEach(lista -> {
			stb.append(lista.getName() + " -> \t");
			lista.getEntityList().forEach(es -> {
				stb.append("|");
			});
			stb.append(" (" + lista.getEntityList().size() + ") ");
			stb.append("\n");
		});
		stb.append("\n");
		System.out.println(stb);
	}

	/**
	 * ??? se a abordagem para especifica????o da passagem de tempo nos processos for
	 * expl??cita
	 */
	public void waitFor(long time) {

	}

	// controlando tempo de execu????o ===============================================

	/**
	 * executa at?? esgotar o modelo, isto ??, at?? a engine n??o ter mais nada para
	 * processar (FEL vazia, i.e., lista de eventos futuros vazia)
	 */
	public void simulate() {
		printLog();
		this.contCiclos++;
		while (!eventosAgendados.isEmpty()) {
			System.out.println(this.tempo);
			var proximoCiclo = getProximoCiclo();
			var eventosDoCiclo = eventosAgendados.stream().filter(it -> it.getTempo() <= proximoCiclo).collect(Collectors.toList());
			eventosDoCiclo.forEach(menorEvento -> {
				menorEvento.execute();
				// Adicionado pois eventos que forem do tipo Processo n??o podem ser removidos
				if (!(menorEvento instanceof Process)) {
					eventosAgendados.remove(menorEvento);
				}
				printLog();
			});
			this.tempo = proximoCiclo;
		}
		System.out.println("Todos eventos processados.");
	}

	/*
	 * executa somente uma primitiva da API e interrompe execu????o; por ex.: dispara
	 * um evento e para; insere numa fila e para, etc.
	 */
	public void simulateOneStep() {
		// this.eventosAgendados.get(0).execute();
	}

	public void simulateBy(long duration) {
		while (this.getTempo() <= duration) {
			// simula
		}
	}

	public void simulateUntil(long absoluteTime) {
		while (this.getTempo() < absoluteTime) {
			// simula
		}
	}

	// cria????o, destrui????o e acesso para componentes
	// ===============================================

	public Integer generateId() {
		return ++id;
	}

	/*
	 * retorna refer??ncia para instancia de Event
	 */
	public Event getEvent(Integer eventId) throws Exception {
		return this.eventosAgendados.stream().filter(e -> e.getId().intValue() == eventId.intValue()).findAny().orElse(null);
	}

	/*
	 * retorna refer??ncia para instancia de EntitySet
	 */
	public EntitySet getEntitySet(Integer id) throws Exception {
		return this.entitySetList.stream().filter(esl -> esl.getId() == id.intValue()).findAny().orElse(null);
	}

	// random variates

	/**
	 * Cria chegada na fila pelo tempo passado em minutos
	 */

	public Double getProximoCiclo() {
		return eventosAgendados.stream().min(Comparator.comparing(Event::getTempo)).orElseThrow().getTempo();
	}

	// Alerta De Gambiarra!
	public Optional<Double> getProximoProximoCiclo() {
		return eventosAgendados.stream().filter(event -> event.getTempo() != this.tempo).min(Comparator.comparing(Event::getTempo)).map(Event::getTempo);
	}

	public void reAgendarProcessoProximoCiclo(Integer processID) {
		getProximoProximoCiclo().ifPresent(it -> reAgendarProcesso(processID, it));

	}

	public void reAgendarProcesso(Integer processID, Double time) {
		Event eventProcess = eventosAgendados.stream().filter(proc -> proc.getId() == processID.intValue()).findAny().orElseThrow();
		if (!(eventProcess instanceof Process)) {
			throw new RuntimeException("Event " + eventProcess.getId() + " Nao e processo!!");
		}
		eventProcess.setTime(this.tempo + time);
		// Comentado metodo add pois pois colocado bloqueio de remo????o no simulate()
		// eventosAgendados.add(eventProcess);
	}
}