package com.wealthsimple;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;

public class PortfolioTest
{

    @Rule
    public ExpectedException exception = ExpectedException.none();

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

    @Test
    public void testConstructorNullInvestments()
    {
        exception.expect(NullPointerException.class);
        Portfolio portfolio = new Portfolio(null);
    }

    @Test
    public void testRebalance()
    {
        Portfolio
                portfolio =
                new Portfolio(Arrays.asList(new Investment("A",
                                                           new BigDecimal("1"),
                                                           new BigDecimal("0.1"),
                                                           new BigDecimal("1"),
                                                           new BigDecimal("2")),
                                            new Investment("B",
                                                           new BigDecimal("0"),
                                                           new BigDecimal("0.1"),
                                                           new BigDecimal("1"),
                                                           new BigDecimal("1")))
                );
        portfolio.rebalance(null);
        for (Investment investment : portfolio.investments)
        {
            if (investment.ticker.equals("A"))
            {
                Assert.assertThat(investment.sharesOwned, is(new BigDecimal("2")));
            }
            else if (investment.ticker.equals("B"))
            {
                Assert.assertThat(investment.sharesOwned, is(new BigDecimal("0")));
            }
        }
    }

}
