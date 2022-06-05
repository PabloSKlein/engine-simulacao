package com.unisinos.gb.enginesimulacao.model;

public class Scheduler {

	private Double time;

	public Double getTime() {
		return time;
	}

	// disparo de eventos e processos =============================================

	public void scheduleNow(Event event) {

	}

	public void scheduleIn(Event event, long timeToEvent) {

	}

	public void scheduleAt(Event event, long absoluteTime) {

	}

	public void startProcessNow(Integer processId) {

	}

	public void startProcessIn(Integer processId, long timeToStart) {

	}

	public void startProcessAt(Integer processId, long absoluteTime) {

	}

	/**
	 *  se a abordagem para especificação da passagem de tempo nos processos for
	 * explícita
	 * 
	 */
	public void waitFor(long time) {

	}

	// controlando tempo de execução ===============================================

	/**
	 * executa até esgotar o modelo, isto é, até a engine não ter mais nada para
	 * processar (FEL vazia, i.e., lista de eventos futuros vazia)
	 */
	public void simulate() {

	}

	/*
	 * executa somente uma primitiva da API e interrompe execução; por ex.: dispara
	 * um evento e para; insere numa fila e para, etc.
	 */
	public void simulateOneStep() {

	}

	public void simulateBy(long duration) {

	}

	public void simulateUntil(long absoluteTime) {

	}

	// criação, destruição e acesso para componentes
	// ===============================================

	/*
	 * instancia nova Entity e destroyEntity(id)
	 */
	public void  createEntity(Entity) {
		
	}

	/*
	 * retorna referência para instância de Entity
	 */
	public Entity getEntity(Integer id) {

	}

	public Integer createResource(String name, Double quantity) {

	}

	/*
	 * retorna referência para instância de Resource
	 */
	public Resource getResource(Integer id) {

	}

	public Integer createProcess(String name, long duration) {

	}

	/*
	 * retorna referência para instancia de Process
	 */
	public Process getProcess(Integer processId) {

	}

	public Integer createEvent(String name) {

	}

	/*
	 * retorna referência para instancia de Event
	 */
	public Event getEvent(Integer eventId) {

	}

	public Integer createEntitySet(String name, Integer mode, Integer maxPossibleSize) {

	}

	/*
	 * retorna referência para instancia de EntitySet
	 */
	public EntitySet getEntitySet(Integer id) {

	}

	// random variates

	public double uniform(Integer minValue, Integer maxValue) {

	}

	/*
	 * 
	 */
	public double exponential(Integer meanValue) {

	}

	public double normal(Integer meanValue, Integer stdDeviationValue) {

	}
}
