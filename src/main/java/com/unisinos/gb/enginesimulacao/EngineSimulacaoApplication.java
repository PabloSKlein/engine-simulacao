package com.unisinos.gb.enginesimulacao;

import com.unisinos.gb.enginesimulacao.enumeration.QueueModeEnum;
import com.unisinos.gb.enginesimulacao.model.EntitySet;
import com.unisinos.gb.enginesimulacao.model.Scheduler;
import com.unisinos.gb.enginesimulacao.model.event.Chegada;
import com.unisinos.gb.enginesimulacao.model.resource.Balcao;
import com.unisinos.gb.enginesimulacao.model.resource.Mesa;
import com.unisinos.gb.enginesimulacao.model.entity.GrupoCliente;

import java.util.ArrayList;
import java.util.List;

public class EngineSimulacaoApplication {

    public static void main(String[] args) {

        var de = new Scheduler();

        //var recursoCaixa1 = new Caixa(de.getId(), "CAIXA1", 1);
        //var processoAtendimentocaixa = new AtendimentoCaixa(de.getId(), "ATENDIMENTOCAIXA", de.normal(8.0, 2.0), filaCaixa1, recursoCaixa1);
        //de.startProcessAt(processoAtendimentocaixa, tempos.get(0));//Tempo que demora para executar

        var filaCaixa1 = new EntitySet(de.getId(), "FILACAIXA1", QueueModeEnum.FIFO, 10);
        var filaCaixa2 = new EntitySet(de.getId(), "FILACAIXA2", QueueModeEnum.FIFO, 10);

        //Inicializa os recursos de balcão e de mesas
        Balcao balcao = new Balcao(de.getId(),'BALCAO', 6);
        Mesa[] mesas = new Mesa[8];
        for (int i = 0; i< mesas.length;i++){
            if(i < 4)
                mesas[i] = new Mesa(de.getId(), "MESA " + i,2);
            else
                mesas[i] = new Mesa(de.getId(), "MESA " + i,4);
        }

        //Inicializa as filas de balcao e de caixa
        var filaBalcao = new EntitySet(de.getId(), "FILABALCAO", QueueModeEnum.FIFO, 10);
        var filaMesas = new EntitySet(de.getId(), "FILABMESAS", QueueModeEnum.FIFO, 10);
        //para fins de log
        de.addEntitySet(filaCaixa1);
        de.addEntitySet(filaCaixa2);
        de.addEntitySet(filaBalcao);
        de.addEntitySet(filaMesas);

        new ArrayList<>(List.of(0.000997185, 0.009546665, 0.010303237))
                .forEach(it -> criaChegadaFila(de, filaCaixa1, filaCaixa2, it));



        de.simulate();
    }

    private static void criaChegadaFila(Scheduler de, EntitySet filaCaixa1, EntitySet filaCaixa2, double time) {
        de.scheduleAt(new Chegada(de.getId(), "CHEGADA", filaCaixa1, filaCaixa2), time);
    }

    private static void vaiProBlacãoOuMesa(EntitySet filaBalcao, EntitySet filaMesas, Balcao balcao, Mesa[] mesas,GroupClient group){
        boolean achouMesa = false;
        boolean achouBanco = false;
        if(group.quantidade>=2)
            temMesa(mesas,achouMesa, group);
        else
            temBalcao(balcao,achouBanco,group);

        if(!achouMesa){
            filaMesas.inset(group);
        }
        if(!achouBanco){
            filaBalcao.insert(group);
        }
    }

    private static void temMesa(Mesa[] mesas, int achouMesa, Group group) {
        for (int i = 0; i < mesas.length; i++) {
            if (group.getQuantidade == 2) {
                if (i < 4) {
                    if (mesas[i].getIsOccupied() == 0) {
                        mesas[i].setIsOccupied(1);
                        achouMesa = true
                        break;
                    } else if (mesas[3].getIsOccupied() == 1) {
                        achouMesa = false;
                        break;
                    }
                } else {
                    if (mesas[i].getIsOccupied() == 0) {
                        mesas[i].setIsOccupied(1);
                        achouMesa = true
                        break;
                    } else if (mesas[7].getIsOccupied() == 1) {
                        achouMesa = false;
                        break;
                    }
                }
            }
        }
    }

    private static void temBalcao(Balcao balcao, int achouBanco, Group group){
        if(balcao.isOccupied() == false){
            balcao.ocupaBanco();
            achouBanco = true;
        }
    }
}
