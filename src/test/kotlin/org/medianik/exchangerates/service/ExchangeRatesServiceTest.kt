package org.medianik.exchangerates.service

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.medianik.exchangerates.config.AppProperties
import org.medianik.exchangerates.repository.OpenExchangeRatesRepository
import org.medianik.exchangerates.model.ExchangeRatesResponse
import org.medianik.exchangerates.model.RatesState
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ExchangeRatesServiceTest(
    @Value("\${api.openexchangerates.base}")
    private val fromCurrency: String,
) {
    private val openExchangeRatesRepository = Mockito.mock(OpenExchangeRatesRepository::class.java)
    private final val appProperties = AppProperties(
        AppProperties.Giphy(
            AppProperties.Giphy.Tag("", "", ""),
            "someUrl", "someToken", "somePath",
        ), AppProperties.OpenExchangeRates(
            "RUB", "someToken", "someUrl", "somePath",
        )
    )
    private val exchangeRatesService = ExchangeRatesService(appProperties, openExchangeRatesRepository)
    @Test
    fun `Test ExchangeRatesService gives correct higher result corner case`() {
        test(100.0, 100.0001, 99.0, 99.0001, RatesState.Higher)
    }
    @Test
    fun `Test ExchangeRatesService gives correct equal result corner case`() {
        test(100.0, 100.0, 1.0, 1.0, RatesState.Equal)
    }
    @Test
    fun `Test ExchangeRatesService gives correct lower result corner case`() {
        test(99.0, 99.0001, 100.0, 100.0001, RatesState.Lower)
    }
    @Test
    fun `Test ExchangeRatesService gives correct higher result`() {
        test(85.0, 100.0, 72.0, 102.0, RatesState.Higher)
    }
    @Test
    fun `Test ExchangeRatesService gives correct equal result`() {
        test(100.0, 100.0, 80.0, 80.0, RatesState.Equal)
    }
    @Test
    fun `Test ExchangeRatesService gives correct lower result`() {
        test(85.0, 100.0, 75.0, 79.0, RatesState.Lower)
    }

    private fun test(todayFrom: Double, todayTo: Double, yesterdayFrom: Double, yesterdayTo: Double, ratesState: RatesState) {
        Mockito.`when`(openExchangeRatesRepository.getExchangeRatesToday("USD")).thenReturn(currencyItem(todayFrom, todayTo))
        Mockito.`when`(openExchangeRatesRepository.getExchangeRatesYesterday("USD")).thenReturn(currencyItem(yesterdayFrom, yesterdayTo))
        Assertions.assertThat(exchangeRatesService.getRatesStateFor("USD")).isEqualTo(ratesState)
    }

    private fun currencyItem(from: Double, to: Double): ExchangeRatesResponse {
        return ExchangeRatesResponse(mapOf(fromCurrency to from, "USD" to to))
    }
}