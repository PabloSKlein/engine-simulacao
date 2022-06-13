package com.unisinos.gb.enginesimulacao.model.process;

import com.unisinos.gb.enginesimulacao.engine.Scheduler;
import com.unisinos.gb.enginesimulacao.enumeration.DistributionEnum;
import com.unisinos.gb.enginesimulacao.model.entity.Entity;
import com.unisinos.gb.enginesimulacao.model.entity.EntitySet;
import com.unisinos.gb.enginesimulacao.model.entity.GrupoCliente;
import com.unisinos.gb.enginesimulacao.model.entity.Pedido;
import com.unisinos.gb.enginesimulacao.model.resources.Caixa;
import com.unisinos.gb.enginesimulacao.model.resources.Resource;

public class AtendimentoCaixa extends Process {

	private final EntitySet filaCaixa;
	private final EntitySet filaPedido;
	private final EntitySet filaMesa;
	private final EntitySet filaBalcao;
	private final Resource caixaRecurso;
	private Entity entity;

	public AtendimentoCaixa(Integer id, Scheduler scheduler, EntitySet filaCaixa, EntitySet filaPedido, EntitySet filaMesa, EntitySet filaBalcao, Caixa recursoCaixa) {
		super(id, "ATENDIMENTOCAIXA" + id, scheduler, DistributionEnum.NORMAL);
		this.filaCaixa = filaCaixa;
		this.filaPedido = filaPedido;
		this.filaMesa = filaMesa;
		this.filaBalcao = filaBalcao;
		this.caixaRecurso = recursoCaixa;
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
		return 8.0;
	}

	@Override
	public Double getStdDeviation() {
		return 2.0;
	}

	@Override
	protected void executeOnStart() {
		caixaRecurso.allocate();
		this.entity = filaCaixa.remove();
	}

	@Override
	protected void executeOnEnd() {
		filaPedido.insert(new Pedido(getScheduler().generateId(), (GrupoCliente) this.entity));
		caixaRecurso.release();
	}

	@Override
	public boolean deveProcessar() {
		return caixaRecurso.podeAlocarRecurso() && !filaCaixa.isEmpty();
	}

}