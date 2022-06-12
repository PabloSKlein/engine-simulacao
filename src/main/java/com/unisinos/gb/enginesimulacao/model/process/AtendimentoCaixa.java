package com.unisinos.gb.enginesimulacao.model.process;

import com.unisinos.gb.enginesimulacao.model.EntitySet;
import com.unisinos.gb.enginesimulacao.model.Scheduler;
import com.unisinos.gb.enginesimulacao.model.entity.Entity;
import com.unisinos.gb.enginesimulacao.model.entity.GrupoCliente;
import com.unisinos.gb.enginesimulacao.model.entity.Pedido;
import com.unisinos.gb.enginesimulacao.model.resources.Balcao;
import com.unisinos.gb.enginesimulacao.model.resources.Caixa;
import com.unisinos.gb.enginesimulacao.model.resources.Mesa;
import com.unisinos.gb.enginesimulacao.model.resources.Resource;

public class AtendimentoCaixa extends Process {

	private final EntitySet filaCaixa;
	private final EntitySet filaPedido;
	private final EntitySet filaMesa;
	private final EntitySet filaBalcao;
	private Entity entity;
	private final Resource caixaRecurso;
	private Balcao balcao;
	private Mesa[] mesas;

	public AtendimentoCaixa(Integer id, String name, Scheduler scheduler, double time, Double duration, EntitySet filaCaixa, EntitySet filaPedido, EntitySet filaMesa,
			EntitySet filaBalcao, Caixa recursoCaixa, Balcao balcao, Mesa[] mesas) {
		super(id, name, scheduler, time, duration, false);
		this.filaCaixa = filaCaixa;
		this.filaPedido = filaPedido;
		this.filaMesa = filaMesa;
		this.filaBalcao = filaBalcao;
		this.balcao = balcao;
		this.mesas = mesas;
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

		if (this.entity.getQuantidade() >= 2)
			temMesa();
		else
			temBalcao();

		caixaRecurso.release();
	}

	public GrupoCliente getEntity() {
		return entity;
	}

	public void setEntity(GrupoCliente entity) {
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

	private void temMesa() {
		boolean achouMesa = false;
		for (int i = 0; i < mesas.length; i++) {
			if (this.entity.getQuantidade() == 2) {
				if (i < 4) {
					if (mesas[i].isOccupied()) {
						mesas[i].ocupaMesa();
						this.filaSaida.insert(this.entity);
						achouMesa = true;
						break;
					} else if (!mesas[3].isOccupied()) {
						achouMesa = false;
						break;
					}
				} else {
					if (mesas[i].isOccupied()) {
						mesas[i].ocupaMesa();
						this.filaSaida.insert(this.entity);
						achouMesa = true;
						break;
					} else if (!mesas[7].isOccupied()) {
						achouMesa = false;
						break;
					}
				}
			}
		}

		if (!achouMesa) {
			filaMesa.insert(this.entity);
		}
	}

	private void temBalcao() {
		if (balcao.isOccupied() == false) {
			balcao.ocupaBanco();
			this.filaSaida.insert(this.entity);
			// Time de saida
			// Faço o que com o grupo de pessoas?
		} else {
			filaBalcao.insert(group);
		}
	}

}
