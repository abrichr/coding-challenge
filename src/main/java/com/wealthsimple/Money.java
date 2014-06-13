package com.wealthsimple;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility class for operations on financial values.
 */
public class Money
{
    private static final int          DEFAULT_DIVISION_SCALE = 4;
    private static final RoundingMode DEFAULT_ROUNDING_MODE  = RoundingMode.HALF_EVEN;

    /**
     * Divide one BigDecial by another.
     *
     * @param dividend     The value to divide.
     * @param divisor      The value by which to divide.
     * @param keepFraction A boolean specifying whether to keep the fractional value.
     * @return The quotient of the dividend and the divisor.
     */
    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor, boolean keepFraction)
    {
        return dividend.divide(divisor, keepFraction ? DEFAULT_DIVISION_SCALE : 0, DEFAULT_ROUNDING_MODE);
    }
}