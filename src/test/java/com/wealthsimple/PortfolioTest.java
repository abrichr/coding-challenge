package com.wealthsimple;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

public class PortfolioTest
{
    @Test
    public void testGetValue()
    {
        BigDecimal numShares1 = new BigDecimal("10");
        BigDecimal value1 = new BigDecimal("123.45");
        BigDecimal numShares2 = new BigDecimal("20");
        BigDecimal value2 = new BigDecimal("678.90");

        Portfolio portfolio = new Portfolio(Arrays.asList(
                new Investment("", BigDecimal.ZERO, BigDecimal.ZERO, numShares1, value1),
                new Investment("", BigDecimal.ZERO, BigDecimal.ZERO, numShares2, value2)
        ));

        BigDecimal expectedValue =
                numShares1.multiply(value1).add(numShares2.multiply(value2));
        BigDecimal actualValue = portfolio.getValue();

        Assert.assertEquals("Expected value was not equal to actual value", expectedValue, actualValue);
    }
}
