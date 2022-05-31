package org.medianik.exchangerates

import org.medianik.exchangerates.config.AppProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties(AppProperties::class)
class ExchangeRatesApplication

fun main(args: Array<String>) {
    runApplication<ExchangeRatesApplication>(*args)
}
