package com.unisinos.gb.enginesimulacao.model.process;

import com.unisinos.gb.enginesimulacao.enumeration.DistributionEnum;
import com.unisinos.gb.enginesimulacao.model.Scheduler;
import com.unisinos.gb.enginesimulacao.model.event.Event;

public abstract class Process extends Event {

	private DistributionEnum enumDuration;
	private boolean active;

	public Process(Integer id, String name, Scheduler scheduler, double time, boolean active, DistributionEnum enumDuration) {
		super(id, name, scheduler, time);
		this.enumDuration = enumDuration;
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Double getDuration() {
		return this.getScheduler().chooseDistribution(enumDuration);
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
				this.getScheduler().reAgendarProcesso(this.getId(), getDuration());
			} else {
				this.getScheduler().reAgendarProcessoProximoCiclo(this.getId());
			}
		}
	}

	public abstract boolean deveProcessar();
}
