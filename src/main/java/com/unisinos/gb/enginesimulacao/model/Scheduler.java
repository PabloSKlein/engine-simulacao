package com.unisinos.gb.enginesimulacao.model;

import com.unisinos.gb.enginesimulacao.model.entity.Entity;
import com.unisinos.gb.enginesimulacao.model.event.Event;
import com.unisinos.gb.enginesimulacao.model.process.Process;
import com.unisinos.gb.enginesimulacao.model.resources.Resource;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Scheduler {

    private Double time;

    // Adicionado listas
    private List<Event> events;
    private List<Entity> entitys;
    private List<EntitySet> entitySets;
    private List<Resource> resources;
    private List<Process> processes;

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
        events.add(event);
    }

    public void scheduleIn(Event event, long timeToEvent) {

    }

    public void scheduleAt(Event event, long absoluteTime) {

    }

    public void startProcessNow(Integer processId) {
        var process = processes.stream().filter(it -> it.getProcessId().equals(processId)).findAny().orElseThrow();
        process.excute();
    }

    public void startProcessIn(Integer processId, long timeToStart) {

    }

    public void startProcessAt(Integer processId, long absoluteTime) {

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
        //Fila1 0:30
        EntitySet fila1 = entitySets.stream().filter(it -> it.getName().equals("Fila1")).findAny().orElseThrow();
        fila1.insert(new Costumer("Cliente", 1));

        fila1.remove();
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
        entitys.add(new Waiter("Garçom", 1));
    }

    /*
     * retorna referência para instância de Entity
     */
    public Entity getEntity(Integer id) throws Exception {
        throw new Exception("IMPLEMENTAR");
    }

    public Integer createResource(Resource resource) {
        resources.add(resource);
        return resource.getId();
    }

    /*
     * retorna referência para instância de Resource
     */
    public Resource getResource(Integer id) throws Exception {
        throw new Exception("IMPLEMENTAR");
    }

    public Integer createProcess(Process process) {
        processes.add(process);
        return process.getProcessId();
    }

    /*
     * retorna referência para instancia de Process
     */
    public Process getProcess(Integer processId) throws Exception {
        throw new Exception("IMPLEMENTAR");
    }

    public Integer createEvent(Event event) {
        events.add(event);
        return event.getEventId();
    }

    /*
     * retorna referência para instancia de Event
     */
    public Event getEvent(Integer eventId) throws Exception {
        throw new Exception("IMPLEMENTAR");
    }

    public Integer createEntitySet(EntitySet teste) {
        entitySets.add(teste);
        return teste.getId();
    }

    /*
     * retorna referência para instancia de EntitySet
     */
    public EntitySet getEntitySet(Integer id) throws Exception {
        throw new Exception("IMPLEMENTAR");
    }

    // random variates
    
    /**
     * Retorna uma distribuição uniforme entre o valor minimo e o valor máximo
     */
    public double uniform(Integer minValue, Integer maxValue) throws Exception {
    	return ThreadLocalRandom.current().nextDouble(minValue, maxValue);
    }

    /*
     * Retorna uma distribuição exponencial de acordo com a média
     */
    public double exponential(Integer meanValue) throws Exception {
    	double m = 1 / meanValue;
		return m*(Math.exp((-m*this.getTime())));
    }
    
    /**
     * Retorna uma distribuição normal utilizando a média e o valor de desvio padrão
     */
    public double normal(Double meanValue, Double stdDeviationValue) {
    	return (this.getTime() - meanValue)/stdDeviationValue;
    }
}
