package com.andreamazzon.exercise6.randomvariables;

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
        return 0;
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
            sum += Math.pow(-1, i)*Math.pow(y,2*i+1)/factorial/(2*i+1);
            factorial *= i+1;
        }
        return 2 / Math.sqrt(Math.PI) * sum;
    }

    @Override
        public double getCumulativeDistributionFunction(double x) {
        return (1 + errorFunction((x - µ) / Math.sqrt(2 * σ2))) / 2;
    }

    @Override
    public double getQuantileFunction(double x) {
        if (x > 0.5) return -getQuantileFunction(1 - x);
        var t = Math.sqrt(Math.log(1 / Math.pow(x, 2)));
        return -(t - (c0 + c1 * t + c2 * t * t) / (1 + d1 * t + d2 * t * t + d3 * t * t * t));
    }
}
