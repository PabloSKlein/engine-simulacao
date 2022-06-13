package com.unisinos.gb.enginesimulacao.model.process;

import com.unisinos.gb.enginesimulacao.engine.Scheduler;
import com.unisinos.gb.enginesimulacao.enumeration.DistributionEnum;
import com.unisinos.gb.enginesimulacao.model.entity.Entity;
import com.unisinos.gb.enginesimulacao.model.entity.EntitySet;
import com.unisinos.gb.enginesimulacao.model.entity.Pedido;
import com.unisinos.gb.enginesimulacao.model.resources.Cozinheiro;
import com.unisinos.gb.enginesimulacao.model.resources.Resource;

public class PreparaRefeicao extends Process {

	private final EntitySet filaPedido;
	private final EntitySet filaPreparados;
	private final Resource cozinheiro;
	private Entity entity;

	public PreparaRefeicao(Integer id, Scheduler scheduler, EntitySet filaPedido, EntitySet filaPreparados, Cozinheiro cozinheiro) {
		super(id, "PREPARAREFEICAO " + id, scheduler, DistributionEnum.NORMAL);
		this.filaPedido = filaPedido;
		this.filaPreparados = filaPreparados;
		this.cozinheiro = cozinheiro;
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
		return 14.0;
	}

	@Override
	public Double getStdDeviation() {
		return 5.0;
	}

	@Override
	protected void executeOnStart() {
		cozinheiro.allocate();
		this.entity = filaPedido.remove();
	}

	@Override
	protected void executeOnEnd() {
		Pedido pedido = (Pedido) this.entity;
		filaPreparados.insert(pedido);
		cozinheiro.release();
	}

	@Override
	public boolean deveProcessar() {
		return cozinheiro.podeAlocarRecurso() && !filaPedido.isEmpty();
	}

}