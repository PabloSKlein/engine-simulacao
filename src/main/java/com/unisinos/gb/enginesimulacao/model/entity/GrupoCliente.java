package com.unisinos.gb.enginesimulacao.model.entity;

import static com.unisinos.gb.enginesimulacao.enumeration.QueueModeEnum.randomIntFromInterval;

public class GrupoCliente extends Entity {
    private final int quantidade = randomIntFromInterval(1, 5);

    public GrupoCliente(Integer id, String name) {
        super(name, id);
    }
}
