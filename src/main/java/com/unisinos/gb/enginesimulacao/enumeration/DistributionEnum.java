package com.unisinos.gb.enginesimulacao.enumeration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public enum DistributionEnum {
	UNIFORM(1) {
		@Override
		public double getDistribution(Double minValue, Double maxValue, Double meanValue, Double stdDeviationValue, Double time) {
			if (minValue == null || maxValue == null) {
				throw new RuntimeException("min e max Obrigatórios");
			}
			return this.uniform(minValue, maxValue);
		}
	},
	NORMAL(2) {
		public double getDistribution(Double minValue, Double maxValue, Double meanValue, Double stdDeviationValue, Double time) {
			if (meanValue == null || stdDeviationValue == null || time == null) {
				throw new RuntimeException("mean e stdDeviation Obrigatórios");
			}
			return this.normal(meanValue, stdDeviationValue, time);
		}
	},
	EXPONENTIAL(3) {
		@Override
		public double getDistribution(Double minValue, Double maxValue, Double meanValue, Double stdDeviationValue, Double time) {
			if (meanValue == null) {
				throw new RuntimeException("mean Obrigatórios");
			}
			return exponential(meanValue);
		}
	};

	private final int key;
	public static final int number_decimals = 2;

	DistributionEnum(int key) {
		this.key = key;
	}

	public abstract double getDistribution(Double minValue, Double maxValue, Double meanValue, Double stdDeviationValue, Double time);

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
		return round(ThreadLocalRandom.current().nextDouble(minValue, maxValue), number_decimals);
	}

	protected double exponential(double meanValue) {
		double lambda = (double) 1 / meanValue;
		return round(Math.log((1 - new Random().nextDouble())) / (-lambda), number_decimals);
	}

	protected double normal(Double meanValue, Double stdDeviationValue, Double time) {
		return round(Math.abs((time - meanValue) / stdDeviationValue), number_decimals);
	}
}
