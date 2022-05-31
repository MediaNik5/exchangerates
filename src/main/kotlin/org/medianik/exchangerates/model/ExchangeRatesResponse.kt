package org.medianik.exchangerates.model

class ExchangeRatesResponse(private val rates: Map<String, Double>) {
    operator fun get(fromCurrency: String, toCurrency: String): Double {
        /*
         * We get concurrency rates map like this:
         * {USD -> FROM_CURRENCY, USD -> TO_CURRENCY}
         * so if we want FROM_CURRENCY -> TO_CURRENCY, we have to divide FROM_CURRENCY by TO_CURRENCY
         */
        return rates[fromCurrency]!! / rates[toCurrency]!!
    }
}