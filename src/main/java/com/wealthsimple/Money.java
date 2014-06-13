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

    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor, boolean keepFraction)
    {
        return dividend.divide(divisor, keepFraction ? DEFAULT_DIVISION_SCALE : 0, DEFAULT_ROUNDING_MODE);
    }
}