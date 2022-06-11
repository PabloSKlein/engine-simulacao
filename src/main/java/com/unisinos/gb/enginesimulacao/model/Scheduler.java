package com.unisinos.gb.enginesimulacao.model;

import com.unisinos.gb.enginesimulacao.model.entity.Entity;
import com.unisinos.gb.enginesimulacao.model.entity.GrupoCliente;
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
    private final List<Process> processes = new ArrayList<>();

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


            processes.stream()
                    .filter(Process::deveProcessar)
                    .forEach(Process::excute);
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
    public double exponential(double meanValue, double time) {
        double m = (double)1 / meanValue;
        return m * (Math.exp((-m * time)));
    }
    
    /**
     * 
     */
    public double exponentialScheduler(double meanValue) {
    	return this.exponential(meanValue,this.getTime());
    }
    
    
    public List<GrupoCliente> createArrivalByTime(double time) {
    	List<GrupoCliente> gcList = new ArrayList<>();
    	for(int i=(int)time;i>0;i = i-3) {
    		double timeArrival = this.exponential((i/3), i);
    		GrupoCliente gc = this.createGroup();
    		gc.setCreationTime(timeArrival);
    		System.out.println(gc);
    		gcList.add(gc);
    	}
    	return gcList;
    }
    
    /**
     * Método de exemplo para retornar um tempo de chegada para um usuário, a cada 3 minutos
     * a média vai ser 1 fixada pelo fato de estar gerando apenas 1 a cada 3 minutos.
     * Se fosse gerar por exemplo por uma hora, então a média seria 60/3, e seria necessário
     * então reduzir de 3 em 3 o 60 e fazer a média e então calcular o horário,
     * se precisar faço mais adiante.
     */
    public GrupoCliente createGroup() {
    	int idCliente = this.getId();
    	GrupoCliente gc = new GrupoCliente(idCliente, "Grupo Cliente "+ idCliente);
    	return gc;
    }

    /**
     * Retorna uma distribuição normal utilizando a média e o valor de desvio padrão
     */
    public double normal(Double meanValue, Double stdDeviationValue, double time) {
        return (time - meanValue) / stdDeviationValue;
    }
    
    /**
     * 
     */
    public double normalScheduler(Double meanValue, Double stdDeviationValue) {
    	return this.normal(meanValue, stdDeviationValue, this.getTime());
    }
    
    public static void main(String[] args) {
		new Scheduler().createArrivalByTime(60);
	}

    public void addProcess(Process process) {
        processes.add(process);
    }
}
