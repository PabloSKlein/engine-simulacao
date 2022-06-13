package com.unisinos.gb.enginesimulacao.enumeration;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
	public static final int NUMBER_DECIMALS = 2;

	DistributionEnum(int key) {
		this.key = key;
	}

	public abstract double getDistribution(Double minValue, Double maxValue, Double meanValue, Double stdDeviationValue);

	public int getKey() {
		return this.key;
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.DOWN);
		return bd.doubleValue();
	}

	protected double uniform(Double minValue, Double maxValue) {
		return round(ThreadLocalRandom.current().nextDouble(minValue, maxValue), NUMBER_DECIMALS);
	}

	protected double exponential(double meanValue) {
		double lambda = (double) 1 / meanValue;
		return round(Math.log((1 - new Random().nextDouble())) / (-lambda), NUMBER_DECIMALS);
	}

	protected double normal(Double meanValue, Double stdDeviationValue) {
		Random r = new Random();
		return round(r.nextGaussian() * stdDeviationValue + meanValue, NUMBER_DECIMALS);
	}
}
