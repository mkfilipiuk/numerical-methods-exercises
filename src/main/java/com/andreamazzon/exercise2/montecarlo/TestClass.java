package com.andreamazzon.exercise2.montecarlo;

import java.text.DecimalFormat;

/**
 * This class tests the Monte-Carlo method applied to the computation of the
 * price of a digital option with underlying given by a binomial model
 * 
 * @author Andrea Mazzon
 *
 */
public class TestClass {

	private final static DecimalFormat formatterDouble = new DecimalFormat("0.00000");

	public static void main(String[] args) {

		// values for the simulation of the binomial process
		double initialValue = 100;
		double increaseIfUp = 1.5;
		double decreaseIfDown = 0.5;
		int lastTime = 2;
		int numberOfSimulations = 100000;
		int specifiedSeed = 1897;
		// threshold for the option
		double threshold = 74;
		// maturity for the option
		int maturityIndex = lastTime;// the time is discrete, the time and the index coincide

		MonteCarloWithBinomial monteCarloWithBinomial = new MonteCarloWithBinomial(initialValue, increaseIfUp,
				decreaseIfDown, lastTime, numberOfSimulations, threshold, maturityIndex);

		// for now, we give the seed. It is fixed
		double priceWithGivenSeed = monteCarloWithBinomial.getPriceForGivenSeed(specifiedSeed);

		System.out.println("The price of the digital option with seed equal to " + specifiedSeed + " and "
				+ numberOfSimulations + " simulations is " + priceWithGivenSeed);

		System.out.println("\n");

		// now we see what happens when we compute some prices for different seeds
		int numberOfPriceComputations = 100;
		int numberOfBins = 10;

		HistogramData histogramData = monteCarloWithBinomial.getHistogram(numberOfBins, numberOfPriceComputations);

		// getters of the container class HistogramData
		int[] histogram = histogramData.getHistogram();
		double minPrice = histogramData.getMinBin();
		double maxPrice = histogramData.getMaxBin();

		System.out.println("Results with " + numberOfSimulations + " simulations:");

		System.out.println();

		System.out.println("Min price: " + minPrice);
		System.out.println("Max price: " + maxPrice);

		System.out.println();

		System.out.println("Histogram:");

		System.out.println();

		/*
		 * we need it in order to print the intervals identified by the bins
		 */
		double binSize = (maxPrice - minPrice) / numberOfBins;

		/*
		 * the first entry is given by prices smaller than the min: not possible, that's
		 * why we start from the second entry
		 */
		for (int i = 1; i < histogram.length; i++) {
			System.out.println("The price has been " + histogram[i] + " times between "
					+ formatterDouble.format((i - 1) * binSize + minPrice) + " and "
					+ formatterDouble.format(i * binSize + minPrice));
		}

		System.out.println();

		System.out.println(
				"Now we see how the number of simulations of the process affects the accuracy of the results:");

		System.out.println();

		numberOfSimulations = 10;

		while (numberOfSimulations <= 1000000) {
			MonteCarloWithBinomial monteCarlo = new MonteCarloWithBinomial(initialValue, increaseIfUp, decreaseIfDown,
					lastTime, numberOfSimulations, threshold, maturityIndex);
			// min price and max price
			double[] minAndMax = monteCarlo.getMinAndMax(numberOfPriceComputations);
			System.out
					.println(numberOfSimulations + " simulations: min = " + minAndMax[0] + ",  max = " + minAndMax[1]);
			numberOfSimulations *= 10;
		}
	}
}
