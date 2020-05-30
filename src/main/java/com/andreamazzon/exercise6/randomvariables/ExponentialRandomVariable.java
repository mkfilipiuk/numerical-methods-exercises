package com.andreamazzon.exercise6.randomvariables;

public class ExponentialRandomVariable extends RandomVariable{

    private double λ;

    public ExponentialRandomVariable(double λ){
        super();
        this.λ = λ;
    }

    public ExponentialRandomVariable(double λ, int seed){
        super(seed);
        this.λ = λ;
    }

    @Override
    public double getAnalyticMean() {
        return 1/λ;
    }

    @Override
    public double getAnalyticStdDeviation() {
        return 1/λ;
    }

    @Override
    public double getDensityFunction(double x) {
        return λ*Math.exp(-λ*x);
    }

    @Override
    public double getCumulativeDistributionFunction(double x) {
        return 1 - Math.exp(-λ*x);
    }

    @Override
    public double getQuantileFunction(double x) {
        return -Math.log(1-x)/λ;
    }
}
