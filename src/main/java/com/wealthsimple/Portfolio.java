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
        String formatString = "%6s%10.4f%10.4f%10s%10s%10s%n";
        String s = String.format(formatString.replace("10.4f", "10s"),
                                 "Ticker",
                                 "% Target",
                                 "% Actual",
                                 "# Owned",
                                 "Price",
                                 "Value");

        BigDecimal totalTarget = BigDecimal.ZERO;
        BigDecimal totalActual = BigDecimal.ZERO;
        BigDecimal totalValue = BigDecimal.ZERO;
        for (Investment investment : investments)
        {
            s += String.format(formatString,
                               investment.ticker,
                               investment.targetAllocation,
                               investment.actualAllocation,
                               investment.sharesOwned,
                               investment.sharePrice,
                               investment.getValue());
            totalTarget = totalTarget.add(investment.targetAllocation);
            totalActual = totalActual.add(investment.actualAllocation);
            totalValue = totalValue.add(investment.getValue());
        }
        String totalString = String.format(formatString, "Total", totalTarget, totalActual, "-", "-", totalValue);
        s += String.format("%s%n", new String(new char[totalString.length()]).replace("\0", "-"));
        s += totalString;
        return s;
    }
}
