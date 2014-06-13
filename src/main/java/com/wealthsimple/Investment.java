package com.wealthsimple;

import java.math.BigDecimal;

/**
 * A single investment.
 */
public class Investment
{
    String     ticker;
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
        this.ticker = ticker;
        this.targetAllocation = targetAllocation;
        this.actualAllocation = actualAllocation;
        this.sharesOwned = sharesOwned;
        this.sharePrice = sharePrice;
    }

    public BigDecimal getValue()
    {
        return sharePrice.multiply(new BigDecimal(sharesOwned));
    }

}
