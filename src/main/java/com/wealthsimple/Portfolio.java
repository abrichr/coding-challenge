package com.wealthsimple;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        BigDecimal totalValue = getValue();
        for (Investment investment : investments)
        {
            BigDecimal targetValue = totalValue.multiply(investment.targetAllocation);
            Long newShares = targetValue.divide(investment.sharePrice, 2, RoundingMode.HALF_EVEN).longValue();
            investment.adjust(newShares, totalValue);
        }
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
        s += String.format("%s%n", StringUtils.repeat("-", totalString.length()));
        s += totalString;
        return s;
    }
}
