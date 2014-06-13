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
        Long numShares1 = 10L;
        BigDecimal value1 = new BigDecimal("123.45");
        Long numShares2 = 20L;
        BigDecimal value2 = new BigDecimal("678.90");

        Portfolio portfolio = new Portfolio(Arrays.asList(
                new Investment(null, null, null, numShares1, value1),
                new Investment(null, null, null, numShares2, value2)
        ));

        BigDecimal expectedValue =
                (new BigDecimal(numShares1)).multiply(value1).add((new BigDecimal(numShares2)).multiply(value2));
        BigDecimal actualValue = portfolio.getValue();

        Assert.assertEquals("Expected value was not equal to actual value", expectedValue, actualValue);
    }
}
