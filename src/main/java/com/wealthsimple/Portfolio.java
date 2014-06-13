package com.wealthsimple;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;

/**
 * A collection of investments and logic for re-balancing them.
 */
public class Portfolio
{
    private final static boolean DEFAULT_ALLOW_FRACTIONAL_SHARES = false;
    List<Investment> investments;
    private boolean allowFractionalShares;

    /**
     * Create a portfolio out of the given Investments. Does not allow fractional shares.
     *
     * @param investments
     */
    public Portfolio(List<Investment> investments)
    {
        this(investments, DEFAULT_ALLOW_FRACTIONAL_SHARES);
    }

    /**
     * Create a Portfolio out of the given Investments, with the option of specifying whether to allow fractional shares.
     *
     * @param investments           A list of Investments
     * @param allowFractionalShares A boolean indicating whether to allow fractional shares
     */
    public Portfolio(List<Investment> investments, boolean allowFractionalShares)
    {
        Validate.notNull(investments, "investments cannot be null");
        this.investments = investments;
        this.allowFractionalShares = allowFractionalShares;
    }

    /**
     * Rebalance this portfolio.
     */
    public void rebalance()
    {
        rebalance(System.out);
    }

    /**
     * Rebalance this portfolio, while specifying the output stream.
     *
     * @param outputStream The stream to which to print output.
     */
    public void rebalance(PrintStream outputStream)
    {
        BigDecimal totalValue = getValue();
        for (Investment investment : investments)
        {
            BigDecimal targetValue = totalValue.multiply(investment.targetAllocation);
            BigDecimal newShares = Money.divide(targetValue, investment.sharePrice, allowFractionalShares);
            investment.adjust(newShares, totalValue, outputStream);
        }
    }

    /**
     * Get the total value of all the investments in this portfolio.
     *
     * @return Sum of values of investments
     */
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
        String formatString = "%6s%10.4f%10.4f%10s%10s%10.2f%n";
        String s = String.format(formatString.replaceAll("10.[2,4]f", "10s"),
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
