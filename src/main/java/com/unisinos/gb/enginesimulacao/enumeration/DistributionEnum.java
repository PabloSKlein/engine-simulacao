package com.unisinos.gb.enginesimulacao.enumeration;

public enum DistributionEnum {
	UNIFORM(1), NORMAL(2), EXPONENTIAL(3);
	
	private int key;
	
	private DistributionEnum(int key) {
		this.key = key;
	}
	
	
	public int getKey() {
		return this.key;
	}
}
