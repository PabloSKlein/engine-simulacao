package com.unisinos.gb.enginesimulacao.engine;

import com.unisinos.gb.enginesimulacao.enumeration.DistributionEnum;
import com.unisinos.gb.enginesimulacao.enumeration.QueueModeEnum;
import com.unisinos.gb.enginesimulacao.model.EntitySet;
import com.unisinos.gb.enginesimulacao.model.entity.Entity;
import com.unisinos.gb.enginesimulacao.model.entity.GrupoCliente;
import com.unisinos.gb.enginesimulacao.model.entity.Pedido;
import com.unisinos.gb.enginesimulacao.model.event.Chegada;
import com.unisinos.gb.enginesimulacao.model.event.Event;
import com.unisinos.gb.enginesimulacao.model.process.AtendimentoCaixa;
import com.unisinos.gb.enginesimulacao.model.process.Process;
import com.unisinos.gb.enginesimulacao.model.resources.Balcao;
import com.unisinos.gb.enginesimulacao.model.resources.Caixa;
import com.unisinos.gb.enginesimulacao.model.resources.Mesa;
import com.unisinos.gb.enginesimulacao.model.resources.Resource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Scheduler {

    public static double meanValue = 2;
    public static double stdDeviationValue = 5;
    public static double minValue = 1;
    public static double maxValue = 6;

    private Double time;
    private static int id = 0;

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
//		processosAgendados.add(process);
    }

    public void startProcessIn(Process process, Double timeToStart) {
        process.setTime(time + timeToStart);
//		processosAgendados.add(process);
    }

    public void startProcessAt(Process process, Double absoluteTime) {
        process.setTime(absoluteTime);
//		processosAgendados.add(process);
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

    // random variates

    /**
     * Cria chegada na fila pelo tempo passado em minutos
     */

    public Double getProximoCiclo() {
        return eventosAgendados.stream().min(Comparator.comparing(Event::getTime)).orElseThrow().getTime();
    }

    public void reAgendarProcessoProximoCiclo(Integer processID) {
        reAgendarProcesso(processID, getProximoCiclo());
    }

    public void reAgendarProcesso(Integer processID, Double time) {
        Event eventProcess = eventosAgendados.stream().filter(proc -> proc.getId() == processID.intValue()).findAny().orElseThrow();
        if (!(eventProcess instanceof Process)) {
            throw new RuntimeException("Event " + eventProcess.getId() + " Nao e processo!!");
        }
        eventProcess.setTime(this.time + time);
    }
}