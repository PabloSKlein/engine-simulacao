package com.unisinos.gb.enginesimulacao.model.process;

import com.unisinos.gb.enginesimulacao.model.Scheduler;
import com.unisinos.gb.enginesimulacao.model.event.Event;

public abstract class Process extends Event {

	private final Double duration;
	private boolean active;

	public Process(Integer id, String name, Scheduler scheduler, double time, Double duration, boolean active) {
		super(id, name, scheduler, time);
		this.duration = duration;
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Double getDuration() {
		return duration;
	}

	public abstract void executeOnStart();

	public abstract void executeOnEnd();

	public void excute() {
		// Se tiver ativo e por que ja foi iniciado e esta em delay
		if (isActive()) {
			this.executeOnEnd();
			this.setActive(false);
			this.getScheduler().reAgendarProcessoProximoCiclo(this.getId());
		} else {
			if (deveProcessar()) {
				this.setActive(true);
				this.executeOnStart();
				this.getScheduler().reAgendarProcesso(this.getId(), this.duration);
			} else {
				this.getScheduler().reAgendarProcessoProximoCiclo(this.getId());
			}
		}
	}

	public abstract boolean deveProcessar();
}
