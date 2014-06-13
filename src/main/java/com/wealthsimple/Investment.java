package com.wealthsimple;

import org.apache.commons.lang3.Validate;

import java.io.PrintStream;
import java.math.BigDecimal;

/**
 * A single investment.
 */
public class Investment
{
    final String ticker;
    BigDecimal targetAllocation;
    BigDecimal actualAllocation;
    BigDecimal sharesOwned;
    BigDecimal sharePrice;

    public Investment(String ticker,
                      BigDecimal targetAllocation,
                      BigDecimal actualAllocation,
                      BigDecimal sharesOwned,
                      BigDecimal sharePrice)
    {
        this.ticker = validateTicker(ticker);
        this.targetAllocation = validateAllocation(targetAllocation, "targetAllocation");
        this.actualAllocation = validateAllocation(actualAllocation, "actualAllocation");
        this.sharesOwned = validateShares(sharesOwned, "sharesOwned");
        this.sharePrice = validateMoney(sharePrice, "sharePrice");
    }

    public BigDecimal getValue()
    {
        return sharePrice.multiply(sharesOwned);
    }

    public void adjust(BigDecimal newShares, BigDecimal portfolioValue)
    {
        adjust(newShares, portfolioValue, System.out);
    }

    void adjust(BigDecimal newShares, BigDecimal portfolioValue, PrintStream outputStream)
    {
        validateShares(newShares, "newShares");
        validateMoney(portfolioValue, "portfolioValue");

        BigDecimal diffShares = newShares.subtract(sharesOwned);
        if (!diffShares.equals(BigDecimal.ZERO))
        {
            if (outputStream != null)
            {
                outputStream.println(getTransactionString(diffShares));
            }
            sharesOwned = newShares;
            actualAllocation = Money.divide(sharePrice.multiply(newShares), portfolioValue, true);
        }
    }

    private String validateTicker(String ticker)
    {
        Validate.notNull(ticker, "ticker cannot be null");
        return ticker;
    }

    private BigDecimal validateAllocation(BigDecimal allocation, String name)
    {
        Validate.notNull(allocation, String.format("%s cannot be null", name));
        Validate.inclusiveBetween(BigDecimal.ZERO,
                                  BigDecimal.ONE,
                                  allocation,
                                  String.format("%s must be between 0 and 1", name));
        return allocation;
    }

    private BigDecimal validateShares(BigDecimal sharesOwned, String name)
    {
        Validate.notNull(sharesOwned, String.format("%s cannot be null", name));
        Validate.isTrue(sharesOwned.compareTo(BigDecimal.ZERO) >= 0, String.format("%s cannot be negative", name));
        return sharesOwned;
    }

    private BigDecimal validateMoney(BigDecimal sharePrice, String name)
    {
        Validate.notNull(sharePrice, String.format("%s cannot be null", name));
        Validate.isTrue(sharePrice.compareTo(BigDecimal.ZERO) >= 0, String.format("%s must be positive", name));
        return sharePrice;
    }

    String getTransactionString(BigDecimal diffShares)
    {
        String operation = diffShares.compareTo(BigDecimal.ZERO) > 0 ? "buy" : "sell";
        String noun = diffShares.abs().compareTo(BigDecimal.ONE) > 0 ? "shares" : "share";
        return String.format("%s %s %s of %s",
                             operation,
                             diffShares.abs(),
                             noun,
                             ticker);
    }
}
