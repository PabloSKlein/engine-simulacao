package com.unisinos.gb.enginesimulacao.model.process;

import com.unisinos.gb.enginesimulacao.model.EntitySet;
import com.unisinos.gb.enginesimulacao.model.Scheduler;
import com.unisinos.gb.enginesimulacao.model.entity.Entity;
import com.unisinos.gb.enginesimulacao.model.entity.GrupoCliente;
import com.unisinos.gb.enginesimulacao.model.entity.Pedido;
import com.unisinos.gb.enginesimulacao.model.resources.Caixa;
import com.unisinos.gb.enginesimulacao.model.resources.Resource;

public class AtendimentoCaixa extends Process {

	private final EntitySet filaCaixa;
	private final EntitySet filaPedido;
	private final EntitySet filaMesa;
	private final EntitySet filaBalcao;
	private Entity entity;
	private final Resource caixaRecurso;

	public AtendimentoCaixa(Integer id, String name, Scheduler scheduler, double time, Double duration, EntitySet filaCaixa, EntitySet filaPedido,
			EntitySet filaMesa, EntitySet filaBalcao, Caixa recursoCaixa) {
		super(id, name, scheduler, time, duration, false);
		this.filaCaixa = filaCaixa;
		this.filaPedido = filaPedido;
		this.filaMesa = filaMesa;
		this.filaBalcao = filaBalcao;
		this.caixaRecurso = recursoCaixa;
	}

	@Override
	public void executeOnStart() {
		caixaRecurso.allocate();
		this.entity = filaCaixa.remove();
	}

	@Override
	public void executeOnEnd() {
		filaPedido.insert(new Pedido((GrupoCliente) this.entity));
		// filaMesaOuCaixa.insert(this.entity);

		caixaRecurso.release();
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public EntitySet getFilaCaixa() {
		return filaCaixa;
	}

	public EntitySet getFilaPedido() {
		return filaPedido;
	}

	public EntitySet getFilaMesa() {
		return filaMesa;
	}

	public EntitySet getFilaBalcao() {
		return filaBalcao;
	}

	public Resource getResource() {
		return caixaRecurso;
	}

	@Override
	public boolean deveProcessar() {
		return caixaRecurso.podeAlocarRecurso() && !filaCaixa.isEmpty();
	}
}
