package com.unisinos.gb.enginesimulacao;

import com.unisinos.gb.enginesimulacao.enumeration.QueueModeEnum;
import com.unisinos.gb.enginesimulacao.model.EntitySet;
import com.unisinos.gb.enginesimulacao.model.Scheduler;
import com.unisinos.gb.enginesimulacao.model.event.Chegada;
import com.unisinos.gb.enginesimulacao.model.process.AtendimentoCaixa;
import com.unisinos.gb.enginesimulacao.model.resources.Caixa;

import java.util.ArrayList;
import java.util.List;

public class EngineSimulacaoApplication {

    public static void main(String[] args) {

        var de = new Scheduler();

        //Inicializa as filas
        var filaBalcao = new EntitySet(de.getId(), "FILABALCAO", QueueModeEnum.FIFO, 10);
        var filaMesas = new EntitySet(de.getId(), "FILAMESAS", QueueModeEnum.FIFO, 10);
        var filaCaixa1 = new EntitySet(de.getId(), "FILACAIXA1", QueueModeEnum.FIFO, 10);
        var filaCaixa2 = new EntitySet(de.getId(), "FILACAIXA2", QueueModeEnum.FIFO, 10);
        var filaPedido = new EntitySet(de.getId(), "FILAPEDIDO", QueueModeEnum.FIFO, 10);

        //Inicializa os recursos de balcão e de mesas
        /*Balcao balcao = new Balcao(de.getId(),'BALCAO', 6);
        Mesa[] mesas = new Mesa[8];
        for (int i = 0; i< mesas.length;i++){
            if(i < 4)
                mesas[i] = new Mesa(de.getId(), "MESA " + i,2);
            else
                mesas[i] = new Mesa(de.getId(), "MESA " + i,4);
        }*/

        //para fins de log
        de.addEntitySet(filaCaixa1);
        de.addEntitySet(filaCaixa2);
        de.addEntitySet(filaPedido);
        de.addEntitySet(filaBalcao);
        de.addEntitySet(filaMesas);

        new ArrayList<>(List.of(0.000997185, 0.009546665, 0.010303237))
                .forEach(it -> criaChegadaFila(de, filaCaixa1, filaCaixa2, it));

        var recursoCaixa1 = new Caixa(de.getId(), "CAIXA1", 1);
        var recursoCaixa2 = new Caixa(de.getId(), "CAIXA2", 1);
        var processoAtendimentoCaixa1 = new AtendimentoCaixa(de.getId(), "ATENDIMENTOCAIXA", 1.0, filaCaixa1, filaPedido, filaBalcao, recursoCaixa1);
        var processoAtendimentoCaixa2 = new AtendimentoCaixa(de.getId(), "ATENDIMENTOCAIXA", 1.0, filaCaixa1, filaPedido, filaBalcao, recursoCaixa2);

        de.addProcess(processoAtendimentoCaixa1);
        de.addProcess(processoAtendimentoCaixa2);

        de.simulate();
    }

    private static void criaChegadaFila(Scheduler de, EntitySet filaCaixa1, EntitySet filaCaixa2, double time) {
        de.scheduleAt(new Chegada(de.getId(), "CHEGADA", filaCaixa1, filaCaixa2), time);
    }

    /*private static void vaiProBlacãoOuMesa(EntitySet filaBalcao, EntitySet filaMesas, Balcao balcao, Mesa[] mesas,GroupClient group){
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
    }}*/
}
