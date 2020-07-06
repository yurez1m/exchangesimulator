package com.yura.ordermatcher.services;

public class NumberUtils {
    private final static int precision = 10;
    private final static double multiplier = Math.pow(10, precision);
    private final static long NanLong = Long.MIN_VALUE;

    public static long toLong(double value) {
        if (Double.isNaN(value)) {
            return NanLong;
        }
        return Math.round(value * multiplier);
    }

    public static double toDouble(double value) {
        if (value == NanLong) {
            return Double.NaN;
        }
        return value / multiplier;
    }
}
