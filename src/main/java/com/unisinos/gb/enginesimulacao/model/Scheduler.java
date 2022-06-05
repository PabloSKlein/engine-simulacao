package com.unisinos.gb.enginesimulacao.model;

import java.util.List;

public class Scheduler {

	private Double time;

	// Adicionado listas
	private List<Event> events;
	private List<Entity> entitys;
	private List<Resource> resources;

	public Double getTime() {
		return time;
	}

	public List<Event> getEvents() {
		return events;
	}

	public List<Entity> getEntitys() {
		return entitys;
	}

	public void setEntitys(List<Entity> entitys) {
		this.entitys = entitys;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
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
	public void createEntity(Entity entity) {

	}

	/*
	 * retorna referência para instância de Entity
	 */
	public Entity getEntity(Integer id) throws Exception {
		throw new Exception("IMPLEMENTAR");
	}

	public Integer createResource(String name, Double quantity) throws Exception {
		throw new Exception("IMPLEMENTAR");
	}

	/*
	 * retorna referência para instância de Resource
	 */
	public Resource getResource(Integer id) throws Exception {
		throw new Exception("IMPLEMENTAR");
	}

	public Integer createProcess(String name, long duration) throws Exception {
		throw new Exception("IMPLEMENTAR");
	}

	/*
	 * retorna referência para instancia de Process
	 */
	public Process getProcess(Integer processId) throws Exception {
		throw new Exception("IMPLEMENTAR");
	}

	public Integer createEvent(String name) throws Exception {
		throw new Exception("IMPLEMENTAR");
	}

	/*
	 * retorna referência para instancia de Event
	 */
	public Event getEvent(Integer eventId) throws Exception {
		throw new Exception("IMPLEMENTAR");
	}

	public Integer createEntitySet(String name, Integer mode, Integer maxPossibleSize) throws Exception {
		throw new Exception("IMPLEMENTAR");
	}

	/*
	 * retorna referência para instancia de EntitySet
	 */
	public EntitySet getEntitySet(Integer id) throws Exception {
		throw new Exception("IMPLEMENTAR");
	}

	// random variates

	public double uniform(Integer minValue, Integer maxValue) throws Exception {
		throw new Exception("IMPLEMENTAR");
	}

	/*
	 * 
	 */
	public double exponential(Integer meanValue) throws Exception {
		throw new Exception("IMPLEMENTAR");
	}

	public double normal(Integer meanValue, Integer stdDeviationValue) throws Exception {
		throw new Exception("IMPLEMENTAR");
	}
}
