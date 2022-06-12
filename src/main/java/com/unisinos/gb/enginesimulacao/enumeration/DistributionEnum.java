package com.unisinos.gb.enginesimulacao.enumeration;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public enum DistributionEnum {
	UNIFORM(1){
		@Override
		public double getDistribution(Double minValue, Double maxValue){
			return this.uniform(minValue, maxValue);
		}
	}, NORMAL(2) {
		public double getDistribution(Double minValue, Double maxValue) {
			return this.normal(minValue, maxValue);
		}
	}, EXPONENTIAL(3) {
		@Override
		public double getDistribution(Double minValue, Double maxValue) {
			return 0;
		}
	};
	
	private final int key;
	
	DistributionEnum(int key) {
		this.key = key;
	}
	public abstract double getDistribution(Double minValue, Double maxValue);

	public int getKey() {
		return this.key;
	}

	public double uniform(Double minValue, Double maxValue) {
		return ThreadLocalRandom.current().nextDouble(minValue, maxValue);
	}

	public double exponential(double meanValue) {
		double lambda = (double) 1 / meanValue;
		return Math.log((1 - new Random().nextDouble())) / (-lambda);
	}
	public double normal(Double meanValue, Double stdDeviationValue) {
		return meanValue / stdDeviationValue;
	}
}
