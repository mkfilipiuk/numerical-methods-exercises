package com.andreamazzon.exercise4;

/**
 * This class deals with the approximation of Pi from the approximation of the
 * volume of a unit hypersphere in d dimensions by the integration of the
 * indicator \[ 1_{\{x_1^2+x_2^+...+x_d^2 <= 1\}\], see equation (1) of the
 * exercise sheet. The points (x_1^i,\dots,x_d^i), i=1,..,d are sampled by an
 * Halton sequence.
 *
 * @author Andrea Mazzon
 *
 */
public class HaltonSequencePiFromHypersphere {
	private int dimension;
	private int numberOfSamplePoints;
	private HaltonSequence haltonSequence;

	public HaltonSequencePiFromHypersphere(int numberOfSamplePoints, int[] base) {

		this.numberOfSamplePoints = numberOfSamplePoints;
		/*
		 * dimension of the hypersphere, and then of the Halton sequence (and of its
		 * base)
		 */
		this.dimension = base.length;
		this.haltonSequence = new HaltonSequence(base);
	}

	/*
	 * used in order to compute the approximation of pi from the one of the volume
	 * of the unit hypersphere
	 */
	private static int factorial(int n) {
		if (n < 1) {
			return 1; // note: return already exits the method. No need for else
		}
		return n * factorial(n - 1);
	}

	/*
	 * Here we use the formulas V_{2k} = pi^k/k!, V_{2k+1}=2(4*pi)^k*k!/(2k+1)!,
	 * where V_n is the volume of the unit hypersphere in n dimensions
	 */
	private double computePiFromVolume(double volume) {
		double approximatedPi; // only definition here, initialization in the if/else loop
		if (dimension % 2 == 0) { // V_{2k} = pi^k/k!
			int k = dimension / 2;
			approximatedPi = Math.pow(volume * factorial(k), 1.0 / k);
		} else {// V_{2k+1}=2(4*pi)^k*k!/(2k+1)!
			int k = (dimension - 1) / 2;
			approximatedPi = Math.pow(volume * factorial(dimension) / (2.0 * factorial(k)), 1.0 / k) / 4.0;
		}
		return approximatedPi;
	}

	/**
	 * It computes the approximation of pi from the volume of a unit hypersphere of
	 * dimension d. You approximate this volume as 2^d times the fraction of numbers
	 * (x_1,..,x_d) produced with an Halton sequence, such that
	 * (2*(x_1-0.5))^2+...+(2*(x_d-0.5))^2<=1.
	 *
	 * @return the approximation of pi.
	 */
	public double piHalton() {
		int numberOfPointsInsideHypersphere = 0;
		for (int i = 0; i < numberOfSamplePoints; i++) {
			/*
			 * at every iteration, get (x_1,...,x_d) from the Halton sequence and compute
			 * (2*(x_1-0.5))^2+...+(2*(x_d-0.5))^2
			 */
			double[] newPoint = haltonSequence.getSamplePoint(i);// i-th element of the Halton Sequence
			double sumOfSquares = 0;
			for (int j = 0; j < dimension; j++) {
				sumOfSquares += newPoint[j] * newPoint[j];
			}
			if (sumOfSquares <= 1) {
				numberOfPointsInsideHypersphere += 1;
			}
		}
		double volumeApproximation = Math.pow(2.0, dimension) * numberOfPointsInsideHypersphere / numberOfSamplePoints;
		return computePiFromVolume(volumeApproximation);
	}

	/**
	 * It returns the error of the approximation of pi from the volume of a unit
	 * hypersphere of dimension d. You approximate this volume as 2^d times the
	 * fraction of numbers (x_1,..,x_d) produced with an Halton sequence, such that
	 * (2*(x_1-0.5))^2+...+(2*(x_d-0.5))^2<=1.
	 *
	 * @return the approximation error, i.e., approximated pi - Math.PI.
	 */
	public double getError() {
		return Math.abs(piHalton() - Math.PI);
	}

}
