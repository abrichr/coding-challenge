package com.wealthsimple;

import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A single investment.
 */
public class Investment
{
    final String ticker;
    BigDecimal targetAllocation;
    BigDecimal actualAllocation;
    Long       sharesOwned;
    BigDecimal sharePrice;

    public Investment(String ticker,
                      BigDecimal targetAllocation,
                      BigDecimal actualAllocation,
                      Long sharesOwned,
                      BigDecimal sharePrice)
    {
        this.ticker = validateTicker(ticker);
        this.targetAllocation = validateAllocation(targetAllocation, "targetAllocation");
        this.actualAllocation = validateAllocation(actualAllocation, "actualAllocation");
        this.sharesOwned = validateSharesOwned(sharesOwned);
        this.sharePrice = validateSharePrice(sharePrice);
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

    private Long validateSharesOwned(Long sharesOwned)
    {
        Validate.notNull(sharesOwned, "sharesOwned cannot be null");
        Validate.isTrue(sharesOwned >= 0, "sharesOwned cannot be negative");
        return sharesOwned;
    }

    private BigDecimal validateSharePrice(BigDecimal sharePrice)
    {
        Validate.notNull(sharePrice, "sharePrice cannot be null");
        Validate.isTrue(sharePrice.compareTo(BigDecimal.ZERO) >= 0, "sharePrice must be positive");
        return sharePrice;
    }

    public BigDecimal getValue()
    {
        return sharePrice.multiply(new BigDecimal(sharesOwned));
    }

    public void adjust(Long newShares, BigDecimal portfolioValue)
    {
        Long diffShares = newShares - sharesOwned;
        if (diffShares != 0)
        {
            printTransaction(diffShares);
            sharesOwned = newShares;
            if (portfolioValue != null)
            {
                actualAllocation = sharePrice.multiply(new BigDecimal(newShares))
                                             .divide(portfolioValue, 2, RoundingMode.HALF_EVEN);
            }
        }
    }

    private void printTransaction(Long diffShares)
    {
        String op = diffShares < 0 ? "sell" : "buy";
        System.out.println(String.format("%s %d shares of %s",
                                         op,
                                         Math.abs(diffShares),
                                         ticker));
    }
}
