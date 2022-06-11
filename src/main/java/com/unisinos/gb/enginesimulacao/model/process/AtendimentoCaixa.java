package com.unisinos.gb.enginesimulacao.model.process;

import com.unisinos.gb.enginesimulacao.model.EntitySet;
import com.unisinos.gb.enginesimulacao.model.entity.Entity;
import com.unisinos.gb.enginesimulacao.model.entity.GrupoCliente;
import com.unisinos.gb.enginesimulacao.model.entity.Pedido;
import com.unisinos.gb.enginesimulacao.model.resources.Resource;

public class AtendimentoCaixa extends Process {

    private final EntitySet filaCaixa;
    private final EntitySet filaPedido;
    private final EntitySet filaMesaOuCaixa;
    private Entity entity;
    private final Resource resource;

    public AtendimentoCaixa(int id, String name, Double duration, EntitySet filaCaixa, EntitySet filaPedido, EntitySet filaMesaOuCaixa, Resource resource) {
        super(id, name, duration);
        this.filaCaixa = filaCaixa;
        this.filaPedido = filaPedido;
        this.filaMesaOuCaixa = filaMesaOuCaixa;
        this.resource = resource;
    }

    @Override
    public void executeOnStart() {
        resource.allocate();
        this.entity = filaCaixa.remove();
    }

    @Override
    public void executeOnEnd() {
        filaPedido.insert(new Pedido((GrupoCliente) this.entity));
        filaMesaOuCaixa.insert(this.entity);

        resource.release();
    }

    @Override
    public boolean deveProcessar() {
        return resource.podeAlocarRecurso() && !filaCaixa.isEmpty();
    }
}
