package com.andreamazzon.exercise6.randomvariables;

import java.util.Random;

public class NormalRandomVariable extends RandomVariable {

    private double µ;
    private double σ2;

    private double
        c0 = 2.515517,
        c1 = 0.802853,
        c2 = 0.010328,
        d1 = 1.432788,
        d2 = 0.189269,
        d3 = 0.001308;

    private ExponentialRandomVariable exponentialRandomVariableForAR = new ExponentialRandomVariable(1);
    private Random SSampler = new Random();
    private Random ARUniformSampler = new Random();

    public NormalRandomVariable(double µ, double σ2) {
        super();
        this.µ = µ;
        this.σ2 = σ2;
    }

    public NormalRandomVariable(double µ, double σ2, int seed) {
        super(seed);
        this.µ = µ;
        this.σ2 = σ2;
    }

    @Override
    public double getAnalyticMean() {
        return µ;
    }

    @Override
    public double getAnalyticStdDeviation() {
        return Math.sqrt(σ2);
    }

    @Override
    public double getDensityFunction(double x) {
        return Math.exp(-Math.pow(x - µ, 2) / (2 * σ2)) / Math.sqrt(2 * Math.PI * σ2);
    }

    private double errorFunction(double y) {
        double sum = 0;
        int N = 1000;
        double factorial = 1;
        for (int i = 0; i < N; ++i) {
            sum += Math.pow(-1, i) * Math.pow(y, 2 * i + 1) / factorial / (2 * i + 1);
            factorial *= i + 1;
        }
        return 2 / Math.sqrt(Math.PI) * sum;
    }

    @Override
    public double getCumulativeDistributionFunction(double x) {
        return (1 + errorFunction((x - µ) / Math.sqrt(2 * σ2))) / 2;
    }

    private double getQuantileFunctionForStandard(double x) {
        if (x > 0.5) return -getQuantileFunctionForStandard(1 - x);
        var t = Math.sqrt(Math.log(1 / Math.pow(x, 2)));
        var result = (-(t - (c0 + c1 * t + c2 * t * t) / (1 + d1 * t + d2 * t * t + d3 * t * t * t)));
        return result;
    }

    @Override
    public double getQuantileFunction(double x) {
        var r = getAnalyticStdDeviation() * getQuantileFunctionForStandard(x) + µ;
        return r;
    }

    public double generateAR() {
        while (true) {
            var exponentialSample = exponentialRandomVariableForAR.generate();
            if (ARUniformSampler.nextDouble() < Math.exp(-Math.pow(exponentialSample - 1, 2) / 2)) {
                var result = SSampler.nextBoolean() ? exponentialSample : -exponentialSample;
                return result * Math.sqrt(σ2) + µ;
            }
        }
    }

    public double[] generateBivariateNormal() {
        return new double[]{this.generate(), this.generate()};
    }

    public double[] generateBivariateNormalAR() {
        return new double[]{this.generateAR(), this.generateAR()};
    }

    public double[] generateBoxMuller() {
        double u = randomNumberGenerator.nextDouble(), v = randomNumberGenerator.nextDouble();
        var sqrtlog = Math.sqrt(-Math.log(u));
        var cos = Math.cos(2 * Math.PI * v);
        var sin = Math.sin(2 * Math.PI * v);
        return new double[]{getAnalyticStdDeviation() * (sqrtlog * cos) + µ, getAnalyticStdDeviation() * (sqrtlog * sin) + µ};
    }

    public double[] generateARBoxMuller() {
        while (true) {
            double u = randomNumberGenerator.nextDouble() * 2 - 1, v = randomNumberGenerator.nextDouble() * 2 - 1;
            var w = u * u + v * v;
            if (w <= 1) {
                var s = Math.sqrt(-2 * Math.log(w) / w);
                return new double[]{getAnalyticStdDeviation() * (s * u) + µ, getAnalyticStdDeviation() * (s * v) + µ};
            }
        }
    }
}
