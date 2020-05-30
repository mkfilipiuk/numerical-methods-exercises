package com.andreamazzon.exercise6.randomvariables;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.DoubleStream;

public abstract class RandomVariable implements RandomVariableInterface {

    protected Random randomNumberGenerator;

    protected RandomVariable(){
        randomNumberGenerator = new Random();
    }

    protected RandomVariable(int seed){
        randomNumberGenerator = new Random(seed);
    }

    protected DoubleStream generateFromDistribution(int n){
        var list = new ArrayList<Double>();
        for (int i = 0; i < n; ++i) {
            list.add(generate());
        }
        return list.stream().mapToDouble(Double::doubleValue);
    }

    @Override
    public double generate() {
        return getQuantileFunction(randomNumberGenerator.nextDouble());
    }

    @Override
    public double getSampleMean(int n) {
        return generateFromDistribution(n).
            average().
            orElseThrow();
    }

    @Override
    public double getSampleStdDeviation(int n) {
        var sampled = generateFromDistribution(n);
        var mean = sampled.
            average().
            orElseThrow();
        return Math.sqrt(sampled.map(x -> Math.pow(x-mean, 2)).average().orElseThrow()*(((float)n)/(n-1)));
    }
}
