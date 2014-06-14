# Portfolio Rebalancer

A simple portfolio rebalancer module, featuring:

* Support for fractional shares
* Thorough parameter validation on all public methods
* Dependency management (Gradle)
* Logging (log4j)
  
## Checkout and Build

<pre>
$ git clone https://github.com/abrichr/coding-challenge.git
$ cd coding-challenge
$ ./gradlew -q
</pre>

## Description
  
<code>Portfolio</code>s consists of one or more <code>Investment</code>s, which are read from a CSV file and instantiated by <code>PortfolioRebalancer</code>. A <code>Money</code> utility class is also included for maintaining consistency across operations.

## Usage

A sample portfolio file is included under <code>resources/test-portfolio.csv</code>, although you may specify the full path to your own CSV file as a single command line parameter to <code>PortfolioRebalancer.main()</code> or programmatically as a parameter to <code>PortfolioBalancer</code>'s constructor. The row format is as follows:

<pre>&lt;Ticker&gt;,&lt;Target allocation&gt;,&lt;Actual allocation&gt;,&lt;Shares owned&gt;,&lt;Share price&gt;</pre>

Allocations must be specified as a decimal value between 0 and 1 (inclusive).

To enable verbose logging, uncomment line 3 in the <code>log4j.properties</code> file.

To enable fractional shares, set <code>.DEFAULT_ALLOW_FRACTIONAL_SHARES = true</code> in <code>Portfolio.java</code>.

## Technical Discussion

The rebalancing logic is contained within <code>Portfolio.rebalance()</code>, which in turn calls <code>Investment.adjust()</code> to adjust each Investment. The rebalancing logic is very simple: for each investment, simply calculate the target value as the product of the share price and the target allocation. Then determine the number of shares required by dividing the target value by the share price.

If fractional shares are enabled, this results in a perfectly rebalanced portfolio, i.e. with 100% value utilization. If fractional shares are not enabled, this may result in a rebalanced portfolio whose value is less than the original portfolio value by some value greater than an individual share price. Essentially, this means that money that could be used to invest is being unused. A slightly more sophisticated approach to mitigate this situation might involve attempting to purchase additional shares until the remaining total value is less than the lowest share price, although this may violate the specified target allocation.

In a multi-user system, concurrency issues would also need to be addressed, with critical sections in <code>Portfolio.rebalance()</code>, <code>Portfolio.value()</code>, and <code>Investment.adjust()</code>.

In this design, a <code>Portfolio</code> and its <code>Investment</code>s are somewhat tightly coupled, since an <code>Investment</code> must know about the total value of the portfolio to which it belongs in order to maintain its actual allocation. While sufficient for the purposes of this module, a less leaky approach might involve an adapter class representing the value of an <code>Investment</code> within a <code>Portfolio</code>. 

Additionally, in a well designed system it is highly unlikely that a <code>Portfolio</code> would be stored as a CSV. If it were, a CSV library would be preferable to the current implementation of <code>PortfolioRebalancer.loadPortfolio()</code>, which is not robust to variations in input. However, a significantly better approach would likely be to store this information within a database, or retrieve it from a web service. Similarly, transaction output and logging would likely not be printed to the console, and configuration such as <code>Portfolio.DEFAULT_ALLOW_FRACTIONAL_SHARES</code> would be loaded from a file.

Testing of <code>Portfolio.rebalance()</code> is quite sparse; edge cases should be considered.

Finally, instead of a utility class with static methods, encapsulating logic for operations on financial values in a Money object will be easier to maintain and extend as the project grows. For a project this size, however, the current design is simpler.