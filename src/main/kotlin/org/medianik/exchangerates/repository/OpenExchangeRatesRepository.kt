package org.medianik.exchangerates.repository

import org.medianik.exchangerates.config.GsonConfiguration
import org.medianik.exchangerates.model.ExchangeRatesResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.time.Clock
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@FeignClient(
    name = "OpenExchangeRates",
    url = "\${api.openexchangerates.url}",
    configuration = [GsonConfiguration::class]
)
interface OpenExchangeRatesRepository {
    @Suppress("MVCPathVariableInspection")
    @GetMapping(path = ["\${api.openexchangerates.path}"])
    fun getExchangeRates(@PathVariable date: String, @PathVariable currency: String): ExchangeRatesResponse

    fun getExchangeRatesYesterday(@PathVariable currency: String): ExchangeRatesResponse {
        return getExchangeRates(
            LocalDate.now(Clock.systemUTC())
                .minusDays(1)
                .format(DateTimeFormatter.ISO_LOCAL_DATE),
            currency
        )
    }

    fun getExchangeRatesToday(@PathVariable currency: String): ExchangeRatesResponse {
        return getExchangeRates(
            LocalDate.now(Clock.systemUTC())
                .format(DateTimeFormatter.ISO_LOCAL_DATE),
            currency
        )
    }
}