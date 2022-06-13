package com.unisinos.gb.enginesimulacao.model.process;

import com.unisinos.gb.enginesimulacao.enumeration.DistributionEnum;
import com.unisinos.gb.enginesimulacao.model.EntitySet;
import com.unisinos.gb.enginesimulacao.model.Scheduler;
import com.unisinos.gb.enginesimulacao.model.entity.GrupoCliente;
import com.unisinos.gb.enginesimulacao.model.resources.Mesa;

public class SentaMesa extends Process {

    Mesa[] mesas;
    EntitySet filaMesa;
    GrupoCliente grupo;
    EntitySet filaSaida;

    public SentaMesa(Integer id, String name, Scheduler scheduler, double time, boolean active,
            DistributionEnum enumDuration, Mesa[] mesas, EntitySet filaMesa, EntitySet filaSaida, GrupoCliente grupo) {
        super(id, name, scheduler, time, active, enumDuration);
        this.mesas = mesas;
        this.filaMesa = filaMesa;
        this.filaSaida = filaSaida;
        this.grupo = grupo;
    }

    @Override
    public void executeOnStart() {
        // TODO Auto-generated method stub

    }

    @Override
    public void executeOnEnd() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean deveProcessar() {
        // TODO Auto-generated method stub
        return false;
    }

    private void temMesa() {
        boolean achouMesa = false;
        for (int i = 0; i < mesas.length; i++) {
            if (this.grupo.getQuantidade() == 2) {
                if (i < 4) {
                    if (mesas[i].isOccupied()) {
                        mesas[i].ocupaMesa();
                        this.filaSaida.insert(this.grupo);
                        achouMesa = true;
                        break;
                    } else if (!mesas[3].isOccupied()) {
                        achouMesa = false;
                        break;
                    }
                } else {
                    if (mesas[i].isOccupied()) {
                        mesas[i].ocupaMesa();
                        this.filaSaida.insert(this.grupo);
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
            filaMesa.insert(this.grupo);
        }
    }
}
