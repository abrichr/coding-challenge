package com.wealthsimple;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

/**
 * Entrypoint for rebalancing portfolio.
 */
public class PortfolioRebalancer
{
    private final static String DEFAULT_INPUT_FILENAME = "/test-portfolio.csv";
    private static       Logger logger                 = Logger.getLogger(PortfolioRebalancer.class);
    private final Portfolio portfolio;

    public PortfolioRebalancer(String filePath) throws IOException
    {
        portfolio = loadPortfolio(filePath);
    }

    public static void main(String[] args) throws IOException, URISyntaxException
    {
        String filePath =
                args.length > 0 ? args[0] : PortfolioRebalancer.class.getResource(DEFAULT_INPUT_FILENAME).getPath();

        PortfolioRebalancer portfolioRebalancer = new PortfolioRebalancer(filePath);
        portfolioRebalancer.rebalance();
    }

    private static Portfolio loadPortfolio(String filePath) throws IOException
    {
        List<Investment> investments = new LinkedList<>();

        logger.info(String.format("Loading file: %s", filePath));

        BufferedReader
                br =
                new BufferedReader(new InputStreamReader(new FileInputStream(filePath), Charset.forName("UTF-8")));
        String line;
        while ((line = br.readLine()) != null)
        {
            String[] parts = line.split(",");
            String ticker = parts[0];
            BigDecimal targetAllocation = new BigDecimal(parts[1]);
            BigDecimal actualAllocation = new BigDecimal(parts[2]);
            BigDecimal sharesOwned = new BigDecimal(parts[3]);
            BigDecimal sharePrice = new BigDecimal(parts[4]);
            Investment investment = new Investment(ticker, targetAllocation, actualAllocation, sharesOwned, sharePrice);
            investments.add(investment);
        }

        return new Portfolio(investments);
    }

    public void rebalance()
    {
        logger.debug(String.format("Before rebalancing:%n") + portfolio);
        portfolio.rebalance();
        logger.debug(String.format("After rebalancing:%n") + portfolio);
    }

}
