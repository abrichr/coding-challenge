package com.wealthsimple;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

public class InvestmentTest
{
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testConstructorNullTicker()
    {
        exception.expect(NullPointerException.class);
        Investment investment = new Investment(null, BigDecimal.ZERO, BigDecimal.ZERO, 0L, BigDecimal.ZERO);
    }

    @Test
    public void testConstructorNullTargetAllocation()
    {
        exception.expect(NullPointerException.class);
        Investment investment = new Investment("", null, BigDecimal.ZERO, 0L, BigDecimal.ZERO);
    }

    @Test
    public void testConstructorInvalidTargetAllocation()
    {
        exception.expect(IllegalArgumentException.class);
        Investment investment = new Investment("", new BigDecimal("1.1"), BigDecimal.ZERO, 0L, BigDecimal.ZERO);
    }

    @Test
    public void testConstructorNullActualAllocation()
    {
        exception.expect(NullPointerException.class);
        Investment investment = new Investment("", BigDecimal.ZERO, null, 0L, BigDecimal.ZERO);
    }

    @Test
    public void testConstructorInvalidActualAllocation()
    {
        exception.expect(IllegalArgumentException.class);
        Investment investment = new Investment("", BigDecimal.ZERO, new BigDecimal("1.1"), 0L, BigDecimal.ZERO);
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
        Investment investment = new Investment("", BigDecimal.ZERO, BigDecimal.ZERO, -1L, BigDecimal.ZERO);
    }

    @Test
    public void testConstructorNullSharePrice()
    {
        exception.expect(NullPointerException.class);
        Investment investment = new Investment("", BigDecimal.ZERO, BigDecimal.ZERO, 0L, null);
    }

}
