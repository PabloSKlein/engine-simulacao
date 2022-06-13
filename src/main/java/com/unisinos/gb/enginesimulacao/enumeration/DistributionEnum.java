package com.unisinos.gb.enginesimulacao.enumeration;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public enum DistributionEnum {
	UNIFORM(1) {
		@Override
		public double getDistribution(Double minValue, Double maxValue, Double meanValue, Double stdDeviationValue) {
			if (minValue == null || maxValue == null) {
				throw new RuntimeException("min e max Obrigatórios");
			}
			return this.uniform(minValue, maxValue);
		}
	},
	NORMAL(2) {
		public double getDistribution(Double minValue, Double maxValue, Double meanValue, Double stdDeviationValue) {
			if (meanValue == null || stdDeviationValue == null) {
				throw new RuntimeException("mean e stdDeviation Obrigatórios");
			}
			return this.normal(meanValue, stdDeviationValue);
		}
	},
	EXPONENTIAL(3) {
		@Override
		public double getDistribution(Double minValue, Double maxValue, Double meanValue, Double stdDeviationValue) {
			if (meanValue == null) {
				throw new RuntimeException("mean Obrigatórios");
			}
			return exponential(meanValue);
		}
	};

	private final int key;

	DistributionEnum(int key) {
		this.key = key;
	}

	public abstract double getDistribution(Double minValue, Double maxValue, Double meanValue, Double stdDeviationValue);

	public int getKey() {
		return this.key;
	}

	protected double uniform(Double minValue, Double maxValue) {
		return ThreadLocalRandom.current().nextDouble(minValue, maxValue);
	}

	protected double exponential(double meanValue) {
		double lambda = (double) 1 / meanValue;
		return Math.log((1 - new Random().nextDouble())) / (-lambda);
	}

	protected double normal(Double meanValue, Double stdDeviationValue) {
		return meanValue / stdDeviationValue;
	}
}
