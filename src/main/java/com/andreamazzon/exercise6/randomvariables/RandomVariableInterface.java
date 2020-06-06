package com.andreamazzon.exercise6.randomvariables;

import java.util.function.DoubleUnaryOperator;

public interface RandomVariableInterface {
    double generate();

    double getAnalyticMean();

    double getAnalyticStdDeviation();

    double getSampleMean(int n);

    double getSampleStdDeviation(int n);

    double getDensityFunction(double x);

    double getCumulativeDistributionFunction(double x);

    double getQuantileFunction(double x);

    double generate(DoubleUnaryOperator function);

    double getSampleMean(int n, DoubleUnaryOperator function);

    double getSampleStandardDeviation(int n, DoubleUnaryOperator function);

    double getSampleMeanWithWeightedMonteCarlo(int n, DoubleUnaryOperator function,
                                               RandomVariable otherRandomVariable);
}
