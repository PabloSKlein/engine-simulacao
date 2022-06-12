package com.unisinos.gb.enginesimulacao.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.unisinos.gb.enginesimulacao.enumeration.QueueModeEnum;
import com.unisinos.gb.enginesimulacao.model.entity.Entity;
import com.unisinos.gb.enginesimulacao.model.event.Chegada;
import com.unisinos.gb.enginesimulacao.model.event.Event;
import com.unisinos.gb.enginesimulacao.model.process.AtendimentoCaixa;
import com.unisinos.gb.enginesimulacao.model.process.Process;
import com.unisinos.gb.enginesimulacao.model.resources.Balcao;
import com.unisinos.gb.enginesimulacao.model.resources.Caixa;
import com.unisinos.gb.enginesimulacao.model.resources.Resource;
import com.unisinos.gb.enginesimulacao.model.resources.Mesa;

public class Scheduler {

	private Double time;
	private static int id = 0;

	private int contChegada = 0;
	private int contSaida = 0;

	// Adicionado listas
	private final List<Event> eventosAgendados = new ArrayList<>();
	// private final List<Process> processosAgendados = new ArrayList<>();
	private final List<EntitySet> entitySetList = new ArrayList<>();
//	private final List<s> processes = new ArrayList<>();

	// Inicializa as filas
	private EntitySet filaBalcao = new EntitySet(generateId(), "FILA BALCAO", QueueModeEnum.FIFO, 100);
	private EntitySet filaMesas = new EntitySet(generateId(), "FILA MESAS", QueueModeEnum.FIFO, 100);
	private EntitySet filaCaixa1 = new EntitySet(generateId(), "FILA CAIXA 1", QueueModeEnum.FIFO, 100);
	private EntitySet filaCaixa2 = new EntitySet(generateId(), "FILA CAIXA 2", QueueModeEnum.FIFO, 100);
	private EntitySet filaPedido = new EntitySet(generateId(), "FILA PEDIDO", QueueModeEnum.FIFO, 100);

	private Balcao = new Balcao(this.id(),"BALCAO", 6);
	private Mesa[] mesas = new ArrayList<Mesa>();

	public void startMesas(){
		for (int i = 0; i < this.mesas.length; i++) {
			if (i < 4)
				this.mesas[i] = new Mesa(this.getI(), "MESA " + i, 2);
			else
				this.mesas[i] = new Mesa(this.getId(), "MESA " + i, 4);
		}
	}
	public void criaChegadaFila(double time) {
		int id = this.generateId();
		this.scheduleAt(new Chegada(id, "CHEGADA " + id, filaCaixa1, filaCaixa2), time);
	}

	public void addEntitySet(EntitySet entitySet) {
		this.entitySetList.add(entitySet);
	}

	public List<EntitySet> entitySetList() {
		return this.entitySetList;
	}

	public Double getTime() {
		return time;
	}

	public List<Event> getEventosAgendados() {
		return eventosAgendados;
	}

	// disparo de eventos e processos =============================================

	public void scheduleNow(Event event) {
		event.setTime(this.time);
		eventosAgendados.add(event);
	}

	public void scheduleIn(Event event, Double timeToEvent) {
		event.setTime(time + timeToEvent);
		eventosAgendados.add(event);
	}

	public void scheduleAt(Event event, Double absoluteTime) {
		event.setTime(absoluteTime);
		eventosAgendados.add(event);
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
		/*
		 * >>>>>>> 6672fb65fb785cf40fc7b378073dba025c2c2249 while
		 * (!eventosAgendados.isEmpty()) {
		 * eventosAgendados.stream().min(Comparator.comparing(Event::getTime)).ifPresent
		 * (menorEvento -> { this.time = menorEvento.getTime(); menorEvento.execute();
		 * eventosAgendados.remove(menorEvento); });
		 * 
		 * processes.stream().filter(Process::deveProcessar).forEach(Process::excute); }
		 * System.out.println("Todos eventos processados.");
		 */
	}

	/*
	 * executa somente uma primitiva da API e interrompe execução; por ex.: dispara
	 * um evento e para; insere numa fila e para, etc.
	 */
	public void simulateOneStep() {
//    	this.eventosAgendados.get(0).execute();
	}

	public void simulateBy(long duration) {
		while (this.getTime() <= duration) {
			// simula
		}
	}

	public void simulateUntil(long absoluteTime) {
		while (this.getTime() < absoluteTime) {
			// simula
		}
	}

	// criação, destruição e acesso para componentes
	// ===============================================

	/*
	 * retorna referência para instância de Entity
	 */
	public Entity getEntity(Integer id) throws Exception {
		Entity searchedEntity = null;
		for (EntitySet entitySet : this.entitySetList) {
			searchedEntity = entitySet.getEntityList().stream().filter(e -> e.getId() == id.intValue()).findAny().orElse(null);
			if (searchedEntity != null)
				return searchedEntity;
		}
		return null;
	}

	/*
	 * retorna referência para instância de Resource
	 */
	public Resource getResource(Integer id) throws Exception {
		throw new Exception("IMPLEMENTAR");
	}

	public Integer createEvent(Event event) {
		return 1;
	}

	public static Integer generateId() {
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

	// random variates

	/**
	 * Retorna uma distribuição uniforme entre o valor minimo e o valor máximo
	 */
	public double uniform(Integer minValue, Integer maxValue) throws Exception {
		return ThreadLocalRandom.current().nextDouble(minValue, maxValue);
	}

	public double exponential(double meanValue) {
		double lambda = (double) 1 / meanValue;
		return Math.log((1 - new Random().nextDouble())) / (-lambda);
	}

	/**
	 * Cria chegada na fila pelo tempo passado em minutos
	 */
	public void createArrivalByTime(double time) {
		for (int i = 3; i <= time; i = i + 3) {
			double timeArrival = this.exponential(3);
			this.criaChegadaFila(timeArrival);
		}
	}

	/**
	 * Cria chegada na fila pelo número de grupo de clientes
	 */
	public void createArrivalByAmountOfClients(double time) {
		this.createArrivalByTime(time * 3);
	}

	/**
	 * Retorna uma distribuição normal utilizando a média e o valor de desvio padrão
	 */
	public double normal(Double meanValue, Double stdDeviationValue, double time) {
		return (time - meanValue) / stdDeviationValue;
	}

	/**
	 * Retorna uma distribuição normal utilizando a média e o valor de desvio padrão
	 * usando o time do schedule.
	 */
	public double normalScheduler(Double meanValue, Double stdDeviationValue) {
		return this.normal(meanValue, stdDeviationValue, this.getTime());
	}

	public Double getProximoCiclo() {
		return eventosAgendados.stream().min(Comparator.comparing(Event::getTime)).get().getTime();
	}

	public void reAgendarProcessoProximoCiclo(Integer processID) {
		reAgendarProcesso(processID, getProximoCiclo());
	}

	public void reAgendarProcesso(Integer processID, Double time) {
		Event eventProcess = eventosAgendados.stream().filter(proc -> proc.getId() == processID.intValue()).findAny().orElseThrow();
		if ((eventProcess instanceof Process) == false) {
			throw new RuntimeException("Event " + eventProcess.getId() + " Nao e processo!!");
		}
		eventProcess.setTime(this.time + time);
	}

	public Balcao criaBalcao(int i, String nome) {
		return new Balcao(generateId(), nome, i);
	}

	public Caixa criaCaixa(String nome, int i) {
		return new Caixa(generateId(), nome, i);
	}

	public AtendimentoCaixa criaAtendimento(double time, EntitySet filaCaixa1, EntitySet filaPedido, EntitySet filaBalcao, EntitySet filaMesa, Caixa recursoCaixa1) {
		var id = generateId();
		return new AtendimentoCaixa(id, "ATENDIMENTOCAIXA" + id, this, time, 1.0, filaCaixa1, filaPedido, filaBalcao, filaMesa, recursoCaixa1);
	}

	public static void main(String[] args) {

		Scheduler de = new Scheduler();

		// Cria os eventos de entrada
		de.createArrivalByTime(60);
		System.out.println(de.getEventosAgendados().toString());

	}

}
