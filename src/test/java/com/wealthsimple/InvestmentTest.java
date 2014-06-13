package com.wealthsimple;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;

public class InvestmentTest
{
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testConstructorNullTicker()
    {
        exception.expect(NullPointerException.class);
        Investment
                investment =
                new Investment(null, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    @Test
    public void testConstructorNullTargetAllocation()
    {
        exception.expect(NullPointerException.class);
        Investment investment = new Investment("", null, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    @Test
    public void testConstructorInvalidTargetAllocation()
    {
        exception.expect(IllegalArgumentException.class);
        Investment
                investment =
                new Investment("", new BigDecimal("1.1"), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    @Test
    public void testConstructorNullActualAllocation()
    {
        exception.expect(NullPointerException.class);
        Investment investment = new Investment("", BigDecimal.ZERO, null, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    @Test
    public void testConstructorInvalidActualAllocation()
    {
        exception.expect(IllegalArgumentException.class);
        Investment
                investment =
                new Investment("", BigDecimal.ZERO, new BigDecimal("1.1"), BigDecimal.ZERO, BigDecimal.ZERO);
    }

    @Test
    public void testConstructorNullSharesOwned()
    {
        exception.expect(NullPointerException.class);
        Investment investment = new Investment("", BigDecimal.ZERO, BigDecimal.ZERO, null, BigDecimal.ZERO);
    }

    @Test
    public void testConstructorInvalidSharesOwned()
    {
        exception.expect(IllegalArgumentException.class);
        Investment
                investment =
                new Investment("", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE.negate(), BigDecimal.ZERO);
    }

    @Test
    public void testConstructorNullSharePrice()
    {
        exception.expect(NullPointerException.class);
        Investment investment = new Investment("", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, null);
    }

    @Test
    public void testAdjust()
    {
        final BigDecimal SHARE_PRICE = new BigDecimal("11.1");
        final BigDecimal NUM_SHARES_BEFORE = BigDecimal.ONE;
        final BigDecimal NUM_SHARES_AFTER = BigDecimal.TEN;
        Investment
                investment =
                new Investment("TICKER", BigDecimal.ZERO, BigDecimal.ZERO, NUM_SHARES_BEFORE, SHARE_PRICE);
        investment.adjust(NUM_SHARES_AFTER, BigDecimal.ONE, null);
        Assert.assertThat(investment.getValue(), is(SHARE_PRICE.multiply(NUM_SHARES_AFTER)));
    }

    @Test
    public void testGetTransactionString()
    {
        Investment
                investment =
                new Investment("TICKER", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

        String plusOne = investment.getTransactionString(BigDecimal.ONE);
        Assert.assertThat(plusOne, is("buy 1 share of TICKER"));

        String plusTen = investment.getTransactionString(BigDecimal.TEN);
        Assert.assertThat(plusTen, is("buy 10 shares of TICKER"));

        String minusOne = investment.getTransactionString(BigDecimal.ONE.negate());
        Assert.assertThat(minusOne, is("sell 1 share of TICKER"));

        String minusTen = investment.getTransactionString(BigDecimal.TEN.negate());
        Assert.assertThat(minusTen, is("sell 10 shares of TICKER"));

        String onePointOne = investment.getTransactionString(new BigDecimal("1.1"));
        Assert.assertThat(onePointOne, is("buy 1.1 shares of TICKER"));

        String minusOnePointOne = investment.getTransactionString(new BigDecimal("-1.1"));
        Assert.assertThat(minusOnePointOne, is("sell 1.1 shares of TICKER"));

    }


}
