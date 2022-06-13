package com.unisinos.gb.enginesimulacao.model.event;

import com.unisinos.gb.enginesimulacao.engine.Scheduler;
import com.unisinos.gb.enginesimulacao.model.entity.EntitySet;
import com.unisinos.gb.enginesimulacao.model.entity.GrupoCliente;
import com.unisinos.gb.enginesimulacao.model.resources.Mesa;

public class EntraFilaSentaMesa extends Event {

    private final Mesa[] mesas;
    private final EntitySet filaMesa;
    private final EntitySet filaSaida;
    private final GrupoCliente grupo;

    protected EntraFilaSentaMesa(Integer id, String name, Scheduler scheduler, double time, Mesa[] mesas,
            EntitySet filaMesa, EntitySet filaSaida, GrupoCliente grupo) {
        super(id, name, scheduler, time);
        this.mesas = mesas;
        this.filaMesa = filaMesa;
        this.filaSaida = filaSaida;
        this.grupo = grupo;
    }

    @Override
    public void execute() {
        temMesa();

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
