fun maxProfit(prices: IntArray, fee: Int): Int {
    var profit = 0
    var buyPrice = prices[0]

    for (i in 1 until prices.size) {
        if (prices[i] < buyPrice) {
            // Update buy price if lower than previous
            buyPrice = prices[i]
        } else if (prices[i] > buyPrice + fee) {
            // Sell the stock and update the profit
            profit += prices[i] - buyPrice - fee
            buyPrice = prices[i] - fee // Update buyPrice to reflect the effective cost post-sale
        }
    }

    return profit
}

fun main() {
    val prices = intArrayOf(1, 3, 2, 8, 4, 10)
    val fee = 2
    println("Maximum Profit: ${maxProfit(prices, fee)}")
}
