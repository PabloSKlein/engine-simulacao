package com.unisinos.gb.enginesimulacao.model;

public abstract class Executable {
    private double time;

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

	@Override
	public String toString() {
		return "Executable [time=" + time + "]";
	}
}
