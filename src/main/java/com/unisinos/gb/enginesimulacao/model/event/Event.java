package com.unisinos.gb.enginesimulacao.model.event;

import com.unisinos.gb.enginesimulacao.engine.Scheduled;
import com.unisinos.gb.enginesimulacao.engine.Scheduler;

public abstract class Event extends Scheduled {
	private double time;
	// Dis a prioridade deste evento perante os outros no ciclo
	private Integer priority = 0;
	private boolean removeEvent = true;

	protected Event(Integer id, String name, Scheduler scheduler, double time) {
		super(id, name, scheduler);
		this.time = time;
	}

	public double getTempo() {
		return time;
	}

	public abstract void execute();

	public void setTime(double time) {
		this.time = time;
	}

	public Integer getPriority() {
		return priority;
	}

	public void renewPriority() {
		this.priority = 0;
	}

	public void addPriority() {
		this.priority++;
	}

	public boolean isRemoveEvent() {
		return removeEvent;
	}

	public void setRemoveEvent(boolean removeEvent) {
		this.removeEvent = removeEvent;
	}

}
