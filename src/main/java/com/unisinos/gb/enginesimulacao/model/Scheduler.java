package com.unisinos.gb.enginesimulacao.model;

import com.unisinos.gb.enginesimulacao.model.entity.Entity;
import com.unisinos.gb.enginesimulacao.model.event.Event;
import com.unisinos.gb.enginesimulacao.model.process.Process;
import com.unisinos.gb.enginesimulacao.model.resources.Resource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Scheduler {

    private Double time;
    private int id = 0;

    // Adicionado listas
    private final List<Event> eventosAgendados = new ArrayList<>();
    private final List<Process> processosAgendados = new ArrayList<>();

    private final List<EntitySet> entitySetList = new ArrayList<>();

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

    public void startProcessNow(Process process) {
        process.setTime(time);
        processosAgendados.add(process);
    }

    public void startProcessIn(Process process, Double timeToStart) {
        process.setTime(time + timeToStart);
        processosAgendados.add(process);
    }

    public void startProcessAt(Process process, Double absoluteTime) {
        process.setTime(absoluteTime);
        processosAgendados.add(process);
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
            eventosAgendados.stream().min(Comparator.comparing(Event::getTime))
                    .ifPresent(menorEvento -> {
                        this.time = menorEvento.getTime();
                        menorEvento.execute();
                        eventosAgendados.remove(menorEvento);
                    });
        }
        System.out.println("Todos eventos processados.");
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

    /**
     * Retorna uma distribuição uniforme entre o valor minimo e o valor máximo
     */
    public double uniform(Integer minValue, Integer maxValue) throws Exception {
        return ThreadLocalRandom.current().nextDouble(minValue, maxValue);
    }

    /*
     * Retorna uma distribuição exponencial de acordo com a média
     */
    public double exponential(Integer meanValue) {
        double m = 1 / meanValue;
        return m * (Math.exp((-m * this.getTime())));
    }

    /**
     * Retorna uma distribuição normal utilizando a média e o valor de desvio padrão
     */
    public double normal(Double meanValue, Double stdDeviationValue) {
        return (this.getTime() - meanValue) / stdDeviationValue;
    }
}
