package com.unisinos.gb.enginesimulacao.model.process;

import com.unisinos.gb.enginesimulacao.engine.Scheduler;
import com.unisinos.gb.enginesimulacao.enumeration.DistributionEnum;
import com.unisinos.gb.enginesimulacao.model.event.Event;

public abstract class Process extends Event {

	private final DistributionEnum distributionEnum;
	private boolean active;
	private Double durationTime = 0.0;

	protected Process(Integer id, String name, Scheduler scheduler, DistributionEnum distributionEnum) {
		super(id, name, scheduler, 1.0);
		this.distributionEnum = distributionEnum;
		this.active = false;
		this.setRemoveEvent(false);
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

	public DistributionEnum getDistributionEnum() {
		return distributionEnum;
	}

	private Double getDuration() {
		return distributionEnum.getDistribution(getMin(), getMax(), getMean(), getStdDeviation());
	}

	protected abstract void executeOnStart();

	protected abstract void executeOnEnd();

	@Override
	public void execute() {
		// Se tiver ativo e por que ja foi iniciado e esta em delay
		if (isActive()) {
			this.executeOnEnd();
			this.setActive(false);
		} else {
			if (deveProcessar()) {
				this.setActive(true);
				this.executeOnStart();
				this.durationTime = getDuration();
				this.getScheduler().reAgendarProcesso(this.getId(), this.durationTime);
				this.renewPriority();
			} else {
				// Verifica se é para desativar
				if (!getScheduler().isProcessosLigados()) {
					setRemoveEvent(true);
				} else {
					// Se não só aumenta prioridade.
					this.addPriority();
				}
			}
		}
	}

	public abstract boolean deveProcessar();

	@Override
	public String toString() {
		return "PROCESSO (" + this.getName() + ") -> " + "(TEMPO: " + this.getTempo() + ")\t" + (isActive() ? "ATIVO   " : "INATIVO ")
				+ (isActive() ? "(DELAY: " + this.durationTime + " - " + distributionEnum + ")" : "(PRIORIDADE: " + this.getPriority() + ")");
	}
}
