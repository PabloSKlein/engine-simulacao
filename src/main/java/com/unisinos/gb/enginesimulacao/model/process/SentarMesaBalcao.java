package com.unisinos.gb.enginesimulacao.model.process;

import java.util.List;

import com.unisinos.gb.enginesimulacao.engine.Scheduler;
import com.unisinos.gb.enginesimulacao.enumeration.DistributionEnum;
import com.unisinos.gb.enginesimulacao.model.entity.Entity;
import com.unisinos.gb.enginesimulacao.model.entity.EntitySet;
import com.unisinos.gb.enginesimulacao.model.entity.GrupoCliente;
import com.unisinos.gb.enginesimulacao.model.resources.Resource;

public class SentarMesaBalcao extends Process {

	private final EntitySet fila;
	private final List<Resource> lugares;
	private Entity entity;
	private Resource lugarUsado;

	public SentarMesaBalcao(Integer id, Scheduler scheduler, EntitySet fila, List<Resource> resources) {
		super(id, "SENTARMESABALCAO" + id, scheduler, DistributionEnum.NONE);
		this.lugares = resources;
		this.fila = fila;
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
		return null;
	}

	@Override
	public Double getStdDeviation() {
		return null;
	}

	private boolean check() {
		return this.lugares.stream().anyMatch(l -> l.isNotAllocated());
	}

	@Override
	protected void executeOnStart() {
		// cozinheiro.allocate();
		this.entity = fila.remove();
		GrupoCliente clientes = (GrupoCliente) this.entity;
		// Se tem lugar disponivel
		lugarUsado = this.lugares.stream().filter(l -> l.isNotAllocated()).findAny().orElse(null);
		lugarUsado.allocateSpecific(clientes.getQuantidade());
	}

	@Override
	protected void executeOnEnd() {
	}

	@Override
	public boolean deveProcessar() {
		return !fila.isEmpty() && check();
	}

}