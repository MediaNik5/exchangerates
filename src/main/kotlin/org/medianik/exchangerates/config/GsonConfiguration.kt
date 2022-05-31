package org.medianik.exchangerates.config

import feign.codec.Decoder
import feign.codec.Encoder
import feign.gson.GsonDecoder
import feign.gson.GsonEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GsonConfiguration {
    @Bean
    fun feignDecoder(): Decoder = GsonDecoder()

    @Bean
    fun feignEncoder(): Encoder = GsonEncoder()
}