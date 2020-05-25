package com.andreamazzon.exercise5.discrepancy;

import java.util.Arrays;

/**
 * This class is devoted to the computation of the discrepancy and of the star
 * discrepancy of a set of points in one dimension. The points of the set are
 * given as a one-dimensional array, which is not supposed to be already sorted
 * when passed in the argument list of the methods.
 *
 * @author Andrea Mazzon
 */
public class DiscrepancyOneDimension {

    /**
     * It computes and return the discrepancy of a set of points in one dimension.
     * In particular, the discrepancy of the one-dimensional set is computed as
     * max_{a \in set} (max_{b \in set} max(|{x_i \in [a,b]}|/n - (b-a), (b-a)-|{x_i
     * in (a,b)}|/n)), where set is the one-dimensional array of the points of the
     * set whose discrepancy must be computed, n is the length of the array, and x_i
     * are points of the array. So we can check intervals [set[position],b], where
     * position runs from 0 (first element of the set) to totalNumberOfPoints - 1
     * (second last) and b is bigger than set[position]
     *
     * @param set, a one-dimensional array giving the points of the set whose
     *             discrepancy must be computed. It is not supposed to be already
     *             sorted when passed in the argument list, so it must be sorted at
     *             the beginning by the Java method Arrays.sort.
     */
    public static double getDiscrepancy(double[] set) {
        Arrays.sort(set);
        double result = getMaximumValue(set, 0); // starDiscrepancy
        for (var a : set) {
            result = Math.max(result, getMaximumValue(set, a));
        }
        return result;
    }

    /*
     * Returns max_{b \in set} max(|{x_i in [set[position],b]}|/n-(b-set[position]),
     * (b-set[position])-|{x_i \in (set[position],b)}|/n), where set is the
     * one-dimensional array of the points of the set whose discrepancy must be
     * computed, n is the length of set, and x_i are points of the set.
     */
    private static double getMaximumValue(double[] set, double a) { // Minor change
        double max = 0;
        for (var b : set) {
            max = Math.max(max, getMaximumForFixedAAndB(set, a, b));
        }
        max = Math.max(max, getMaximumForFixedAAndB(set, a, 1));
        return max;
    }

    private static double getMaximumForFixedAAndB(double[] set, double a, double b) {
        var bMinusA = b - a;
        return Math.max(
            bMinusA - (getNumberOfElementsInRange(set, a, b, false) / set.length),
            (getNumberOfElementsInRange(set, a, b, true) / set.length) - bMinusA
        );
    }

    public static double getNumberOfElementsInRange(double[] set, double start, double stop, boolean including) {
        if (start == stop) {
            return Arrays.binarySearch(set, start) > 0 ? 1 : 0;
        }

        int add = 0;

        var indexStart = Arrays.binarySearch(set, start);
        if (indexStart < 0) {
            indexStart = -(indexStart+1);
        } else {
            add = 1;
        }

        var indexStop = Arrays.binarySearch(set, stop);
        if (indexStop < 0) {
            indexStop = -(indexStop + 1);
        } else {
            add = 1;
        }
        return indexStop - indexStart + add;
    }

    /**
     * It computes and return the star discrepancy of a set of points in one
     * dimension. The star discrepancy of the set of points set is computed as
     * max_{b \in set} max (b - |{x_i \in (0,b)}|/n), |{x_i \in [0,b]}|/n -b ),
     * where set is the one-dimensional array of the points of the set whose
     * discrepancy must be computed, n is the length of the array, and x_i are
     * points of the array.
     *
     * @param set, a one-dimensional array giving the points of the set whose
     *             discrepancy must be computed. It is not supposed to be already
     *             sorted when passed in the argument list, so it must be sorted at
     *             the beginning by the Java method Arrays.sort.
     */
    public static double getStarDiscrepancy(double[] set) {
        // return the star discrepancy of set
        Arrays.sort(set);
        return getMaximumValue(set, 0);
    }
}
