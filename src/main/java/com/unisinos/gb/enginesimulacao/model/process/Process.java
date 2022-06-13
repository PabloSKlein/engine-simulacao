package com.unisinos.gb.enginesimulacao.model.process;

import com.unisinos.gb.enginesimulacao.engine.Scheduler;
import com.unisinos.gb.enginesimulacao.enumeration.DistributionEnum;
import com.unisinos.gb.enginesimulacao.model.event.Event;

public abstract class Process extends Event {

	private final DistributionEnum distributionEnum;
	private boolean active;

	protected Process(Integer id, String name, Scheduler scheduler, DistributionEnum distributionEnum) {
		super(id, name, scheduler, 1.0);
		this.distributionEnum = distributionEnum;
		this.active = false;
	}

	public boolean isActive() {
		return active;
	}

	public abstract Double getMin();

	public abstract Double getMax();

	public abstract Double getStdDeviation();

	public abstract Double getMean();

	public void setActive(boolean active) {
		this.active = active;
	}

	public Double getDuration() {
		return distributionEnum.getDistribution(getMin(), getMax(), getMean(), getStdDeviation());
	}

	public abstract void executeOnStart();

	public abstract void executeOnEnd();

	public void execute() {
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

	@Override
	public String toString() {
		return "PROCESSO (" + this.getName() + ") -> " + "ATIVO: " + isActive() + " DELAY: " + getDuration() + " DISTIBUIÇÃO: " + distributionEnum;
	}
}
