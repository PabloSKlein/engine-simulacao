package com.unisinos.gb.enginesimulacao;

import com.unisinos.gb.enginesimulacao.enumeration.QueueModeEnum;
import com.unisinos.gb.enginesimulacao.model.EntitySet;
import com.unisinos.gb.enginesimulacao.model.Scheduler;
import com.unisinos.gb.enginesimulacao.model.event.Chegada;
import com.unisinos.gb.enginesimulacao.model.process.AtendimentoCaixa;
import com.unisinos.gb.enginesimulacao.model.resources.Caixa;
import com.unisinos.gb.enginesimulacao.model.resources.Balcao;
import com.unisinos.gb.enginesimulacao.model.resources.Mesa;
import com.unisinos.gb.enginesimulacao.model.entity.GrupoCliente;

import java.util.ArrayList;
import java.util.List;

public class EngineSimulacaoApplication {

    public static void main(String[] args) {

        var de = new Scheduler();

       

        //Inicializa os recursos de balcão e de mesas
        var balcao = new Balcao(de.getId(), "BALCAO", 6);
        var mesas = new Mesa[8];
        for (int i = 0; i < mesas.length; i++) {
            if (i < 4)
                mesas[i] = new Mesa(de.getId(), "MESA " + i, 2);
            else
                mesas[i] = new Mesa(de.getId(), "MESA " + i, 4);
        }

        //para fins de log
        de.addEntitySet(filaCaixa1);
        de.addEntitySet(filaCaixa2);
        de.addEntitySet(filaPedido);
        de.addEntitySet(filaBalcao);
        de.addEntitySet(filaMesas);

//        new ArrayList<>(List.of(0.000997185, 0.009546665, 0.010303237))
//                .forEach(it -> criaChegadaFila(de, filaCaixa1, filaCaixa2, it));
        
        //Criação de chegadas na fila
        de.createArrivalByTime(60);

        var recursoCaixa1 = new Caixa(de.getId(), "CAIXA1", 1);
        var recursoCaixa2 = new Caixa(de.getId(), "CAIXA2", 1);
        var processoAtendimentoCaixa1 = new AtendimentoCaixa(de.getId(), "ATENDIMENTOCAIXA", 1.0, filaCaixa1, filaPedido, filaBalcao, recursoCaixa1);
        var processoAtendimentoCaixa2 = new AtendimentoCaixa(de.getId(), "ATENDIMENTOCAIXA", 1.0, filaCaixa1, filaPedido, filaBalcao, recursoCaixa2);

        de.addProcess(processoAtendimentoCaixa1);
        de.addProcess(processoAtendimentoCaixa2);

        de.simulate();
    }

    

    private static void vaiProBlacãoOuMesa(EntitySet filaBalcao, EntitySet filaMesas, Balcao balcao, Mesa[] mesas, GrupoCliente group) {
        boolean achouMesa = false;
        boolean achouBanco = false;
        if (group.getQuantidade() >= 2)
            temMesa(mesas, achouMesa, group, filaMesas);
        else
            temBalcao(balcao, achouBanco, group, filaBalcao);
    }

    private static void temMesa(Mesa[] mesas, boolean achouMesa, GrupoCliente group, EntitySet filaMesas) {
        for (int i = 0; i < mesas.length; i++) {
            if (group.getQuantidade() == 2) {
                if (i < 4) {
                    if (mesas[i].isOccupied()) {
                        mesas[i].ocupaMesa();
                        achouMesa = true;
                        break;
                    } else if (!mesas[3].isOccupied()) {
                        achouMesa = false;
                        break;
                    }
                } else {
                    if (mesas[i].isOccupied()) {
                        mesas[i].ocupaMesa();
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
            filaMesas.insert(group);
        }
    }

    private static void temBalcao(Balcao balcao, boolean achouBanco, GrupoCliente group, EntitySet filaBalcao) {
        if (balcao.isOccupied() == false) {
            balcao.ocupaBanco();
            //Faço o que com o grupo de pessoas?
            achouBanco = true;
        } else {
            filaBalcao.insert(group);
        }
    }

}

