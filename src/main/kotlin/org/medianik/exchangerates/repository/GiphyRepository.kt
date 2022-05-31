package org.medianik.exchangerates.repository

import org.medianik.exchangerates.config.GsonConfiguration
import org.medianik.exchangerates.model.GiphyResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(
    name = "Giphy",
    url = "\${api.giphy.url}",
    configuration = [GsonConfiguration::class],
    primary = false
)
interface GiphyRepository {
    @Suppress("MVCPathVariableInspection")
    @GetMapping(path = ["\${api.giphy.path}"])
    fun getGiphy(@PathVariable tag: String): GiphyResponse
}

