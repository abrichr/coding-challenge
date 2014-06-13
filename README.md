# Portfolio Rebalancer

A simple Portfolio rebalancer module, featuring:

    * Support for fractional shares
    * Thorough parameter validation on all public methods
    * Dependency management (Gradle)
    * Logging (log4j)
    
Portfolios consists of one or more Investments, which are read from a CSV file and instantiated by PortfolioRebalancer. A Money utility class is also included for maintaining consistency across operations.

A sample portfolio file is included under <code>resources/test-portfolio.csv</code>, although you may specify the full path to your own CSV file as the first command line parameter to PortfolioRebalancer. The column order is as follows:

<pre>Ticker,Target allocation,Actual allocation,Shares owned,Share price</pre>

Allocations must be specified as a decimal value between 0 and 1 (inclusive).

To enable verbose logging, uncomment line 3 in the <code>log4j.properties</code> file.

## Technical Discussion

The rebalancing logic is contained within Portfolio.rebalance(), which in turn calls Investment.adjust() to adjust each Investment. The rebalancing logic is very simple: for each investment, simply calculate the target value as the product of the share price and the target allocation. Then determine the number of shares required by dividing the target value by the share price.

If fractional shares are enabled, this results in a perfectly rebalanced portfolio, i.e. with 100% value utilization. If fractional shares are not enabled, this may result in a rebalanced portfolio whose value is less than the original portfolio value by some value greater than an individual share price. Essentially, this means that money that could be used to invest is being unused. A slightly more sophisticated approach might involve attempting to purchase additional shares until the remaining total value is less than the lowest share price, although this may violate the specified target allocation.

In this design, a Portfolio and its Investments are somewhat tightly coupled, since an Investment must know about the total value of the portfolio in order to maintain its actual allocation. While sufficient for the purposes of this module, a less leaky approach might involve an adapter class representing the value of an Investment within a Portfolio. 

Additionally, in a well designed system it is highly unlikely that a Portfolio would be stored as a CSV. If it were, a CSV library would be preferable to the current implementation of PortfolioRebalancer.loadPortfolio(). However, a significantly better approach would be to store this information within a database. Similarly, transaction output and logging would likely not be printed to the console.