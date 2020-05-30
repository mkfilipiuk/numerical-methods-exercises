package com.andreamazzon.exercise6.randomvariables;

public interface RandomVariableInterface {
    double generate();
    double getAnalyticMean();
    double getAnalyticStdDeviation();
    double getSampleMean(int n);
    double getSampleStdDeviation(int n);
    double getDensityFunction(double x);
    double getCumulativeDistributionFunction(double x);
    double getQuantileFunction(double x);
}
