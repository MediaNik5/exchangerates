package org.medianik.exchangerates.service

import org.medianik.exchangerates.config.AppProperties
import org.medianik.exchangerates.exception.UnsupportedCurrencyException
import org.medianik.exchangerates.model.RatesState
import org.medianik.exchangerates.repository.OpenExchangeRatesRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ExchangeRatesService(
    @Autowired appProperties: AppProperties,
    @Autowired private val openExchangeRatesRepository: OpenExchangeRatesRepository,
) {
    private val logger = LoggerFactory.getLogger(ExchangeRatesService::class.java)
    private val fromCurrency = appProperties.openExchangeRates.base

    @Throws(UnsupportedCurrencyException::class)
    fun getRatesStateFor(toCurrency: String): RatesState {
        val (todayRate, yesterdayRate) = getTodayAndYesterdayRates(toCurrency)

        return if (todayRate > yesterdayRate)
            RatesState.Higher
        else if (todayRate < yesterdayRate)
            RatesState.Lower
        else RatesState.Equal
    }

    @Throws(UnsupportedCurrencyException::class)
    private fun getTodayAndYesterdayRates(toCurrency: String): Pair<Double, Double> {
        val ratesToday = openExchangeRatesRepository.getExchangeRatesToday(toCurrency)
        val ratesYesterday = openExchangeRatesRepository.getExchangeRatesYesterday(toCurrency)
        val today: Double
        val yesterday: Double
        try {
            today = ratesToday[fromCurrency, toCurrency]
            yesterday = ratesYesterday[fromCurrency, toCurrency]
        } catch (e: NullPointerException) {
            throw UnsupportedCurrencyException(fromCurrency, toCurrency)
        }
        logger.debug("Yesterday: 1 $toCurrency = $yesterday $fromCurrency")
        logger.debug("Today: 1 $toCurrency = $today $fromCurrency")
        return today to yesterday
    }

}
