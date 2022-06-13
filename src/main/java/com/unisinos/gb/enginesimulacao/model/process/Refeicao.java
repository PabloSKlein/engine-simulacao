package com.unisinos.gb.enginesimulacao.model.process;

import com.unisinos.gb.enginesimulacao.EngineSimulacaoApplication;
import com.unisinos.gb.enginesimulacao.engine.Scheduler;
import com.unisinos.gb.enginesimulacao.enumeration.DistributionEnum;
import com.unisinos.gb.enginesimulacao.model.entity.Entity;
import com.unisinos.gb.enginesimulacao.model.entity.EntitySet;
import com.unisinos.gb.enginesimulacao.model.resources.Resource;

public class Refeicao extends Process {

	private final EntitySet filaPreparados;
	private final Resource local;
	private Entity pedido;

	public Refeicao(Integer id, Scheduler scheduler, String name, EntitySet filaPreparados, Resource local) {

		super(id, "REFEICAO" + name + +id, scheduler, DistributionEnum.NORMAL);

		this.filaPreparados = filaPreparados;
		this.local = local;
	}

	@Override
	public Double getMin() {
		return null;
	}

	@Override
	public Double getMax() {
		return null;
	}

	@Override
	public Double getMean() {
		return 20.0;
	}

	@Override
	public Double getStdDeviation() {
		return 8.0;
	}

	@Override
	protected void executeOnStart() {
		this.pedido = filaPreparados.remove();
	}

	@Override
	protected void executeOnEnd() {
		local.releaseAll();
		EngineSimulacaoApplication.criaSaida(getScheduler().getTempo());
	}

	@Override
	public boolean deveProcessar() {
		return local.isAllocated() && !filaPreparados.isEmpty();
	}

}
