package com.unisinos.gb.enginesimulacao.model.process;

import com.unisinos.gb.enginesimulacao.EngineSimulacaoApplication;
import com.unisinos.gb.enginesimulacao.engine.Scheduler;
import com.unisinos.gb.enginesimulacao.enumeration.DistributionEnum;
import com.unisinos.gb.enginesimulacao.model.entity.Entity;
import com.unisinos.gb.enginesimulacao.model.entity.EntitySet;
import com.unisinos.gb.enginesimulacao.model.entity.GrupoCliente;
import com.unisinos.gb.enginesimulacao.model.entity.Pedido;
import com.unisinos.gb.enginesimulacao.model.resources.Cozinheiro;
import com.unisinos.gb.enginesimulacao.model.resources.Mesa;
import com.unisinos.gb.enginesimulacao.model.resources.Resource;

public class Refeicao extends Process {

	private final EntitySet filaPreparados;
	private final Resource local;
	private Entity pedido;

	public Refeicao(Integer id, Scheduler scheduler, EntitySet filaPreparados, Resource local, Pedido pedido) {
		super(id, "REFEICAO " + id, scheduler, DistributionEnum.NORMAL);
		this.filaPreparados = filaPreparados;
		this.local = local;
		this.pedido = pedido;
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
		
//		cozinheiro.allocate();
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
