package com.wealthsimple;

import java.math.BigDecimal;
import java.util.List;

/**
 * A collection of investments and logic for re-balancing them.
 */
public class Portfolio
{
    private List<Investment> investments;

    public Portfolio(List<Investment> investments)
    {
        this.investments = investments;
    }

    public void rebalance()
    {
        // TODO: implement
    }

    public BigDecimal getValue()
    {
        BigDecimal value = BigDecimal.ZERO;
        for (Investment investment : investments)
        {
            value = value.add(investment.getValue());
        }
        return value;
    }

    @Override
    public String toString()
    {
        String formatString = "%s\t%10s\t%10s\t%10s\t%10s\t%10s%n";
        String s = String.format(formatString,
                                 "Ticker",
                                 "% Target",
                                 "% Actual",
                                 "# Owned",
                                 "Price",
                                 "Value");
        for (Investment investment : investments)
        {
            s += String.format(formatString,
                               investment.ticker,
                               investment.targetAllocation,
                               investment.actualAllocation,
                               investment.sharesOwned,
                               investment.sharePrice,
                               investment.getValue());
        }
        return s;
    }
}
