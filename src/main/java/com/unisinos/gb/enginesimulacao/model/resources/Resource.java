package com.unisinos.gb.enginesimulacao.model.resources;

public abstract class Resource {
    private final String name;
    private final int id;
    private final int quantity;
    private int recursosAlocados = 0;

    protected Resource(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public boolean podeAlocarRecurso(){
       return recursosAlocados < quantity;
    }
    
    public boolean isAllocated() {
    	return recursosAlocados > 0;
    }

    public boolean allocate() {
        if(this.podeAlocarRecurso()){
            recursosAlocados ++;
            return true;
        }
        return true;
    }

    public boolean release() {
        if(recursosAlocados > 0){
            recursosAlocados --;
        }
        return true;
    }
    
    public void allocateSpecific(int quant) {
    	for(int i=0;i<quant;i++) {
    		this.allocate();
    	}
    }
    
    public void releaseAll() {
    	this.recursosAlocados = 0;
    }

    public double allocationRate() {
        return 0;
    }

    public double allocationAverage() {
        return 0;
    }

    public Integer getId() {
        return this.id;
    }
}
