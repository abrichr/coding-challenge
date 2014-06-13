package com.wealthsimple;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Entrypoint for rebalancing portfolio.
 */
public class PortfolioRebalancer
{
    Logger logger = Logger.getLogger(PortfolioRebalancer.class);

    public static void main(String[] args)
    {
        Portfolio portfolio = new Portfolio(Arrays.asList(
                new Investment("GOOG", new BigDecimal(".60"), new BigDecimal(".5096"), 52L, new BigDecimal("98")),
                new Investment("AAPL", new BigDecimal(".30"), new BigDecimal(".2992"), 136L, new BigDecimal("22")),
                new Investment("TSLA", new BigDecimal(".10"), new BigDecimal(".1912"), 239L, new BigDecimal("8"))
        ));

        PortfolioRebalancer portfolioRebalancer = new PortfolioRebalancer();
        portfolioRebalancer.rebalance(portfolio);
    }

    public void rebalance(Portfolio portfolio)
    {
        logger.debug(String.format("Before rebalancing:%n") + portfolio);
        portfolio.rebalance();
        logger.debug(String.format("After rebalancing:%n") + portfolio);
    }

}
