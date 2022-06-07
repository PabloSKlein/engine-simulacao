package com.unisinos.gb.enginesimulacao.model;

import com.unisinos.gb.enginesimulacao.model.entity.Entity;
import com.unisinos.gb.enginesimulacao.model.event.Event;
import com.unisinos.gb.enginesimulacao.model.process.Process;
import com.unisinos.gb.enginesimulacao.model.resources.Resource;

import java.util.List;

public class Scheduler {

    private Double time;
    private int id = 0;

    // Adicionado listas
    private List<Event> events;
    private List<Process> processes;

    public Double getTime() {
        return time;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    // disparo de eventos e processos =============================================

    public void scheduleNow(Event event) {
        event.setTime(this.time);
        events.add(event);
    }

    public void scheduleIn(Event event, Double timeToEvent) {
        event.setTime(time + timeToEvent);
        events.add(event);
    }

    public void scheduleAt(Event event, Double absoluteTime) {
        event.setTime(absoluteTime);
        events.add(event);
    }

    public void startProcessNow(Process process) {
        process.setTime(time);
        processes.add(process);
    }

    public void startProcessIn(Process process, Double timeToStart) {
        process.setTime(time + timeToStart);
        processes.add(process);
    }

    public void startProcessAt(Process process, Double absoluteTime) {
        process.setTime(absoluteTime);
        processes.add(process);
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
     * retorna referência para instância de Entity
     */
    public Entity getEntity(Integer id) throws Exception {
        throw new Exception("IMPLEMENTAR");
    }


    /*
     * retorna referência para instância de Resource
     */
    public Resource getResource(Integer id) throws Exception {
        throw new Exception("IMPLEMENTAR");
    }

    /*
     * retorna referência para instancia de Process
     */
    public Process getProcess(Integer processId) throws Exception {
        throw new Exception("IMPLEMENTAR");
    }

    public Integer createEvent(Event event) {
        return 1;
    }

    public Integer getId() {
        return ++id;
    }

    /*
     * retorna referência para instancia de Event
     */
    public Event getEvent(Integer eventId) throws Exception {
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

    public double normal(Double meanValue, Double stdDeviationValue) {
        return 0;
    }
}
