package org.medianik.exchangerates.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "api")
@ConstructorBinding
class AppProperties(
    val giphy: Giphy,
    val openExchangeRates: OpenExchangeRates,
) {
    class Giphy(
        var tag: Tag,
        var url: String,
        var token: String,
        var path: String,
    ) {
        class Tag(
            var higher: String,
            var equal: String,
            var lower: String,
        )
    }

    class OpenExchangeRates(
        var base: String,
        var token: String,
        var url: String,
        var path: String,
    )
}