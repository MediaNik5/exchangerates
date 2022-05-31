package org.medianik.exchangerates.service

import org.medianik.exchangerates.config.AppProperties
import org.medianik.exchangerates.exception.UnsupportedCurrencyException
import org.medianik.exchangerates.model.RatesState
import org.medianik.exchangerates.repository.GiphyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class GiphyService(
    @Autowired
    appProperties: AppProperties,
    @Autowired
    private val giphyRepository: GiphyRepository,
    @Autowired
    private val exchangeRatesService: ExchangeRatesService,
) {
    private val ratesToTags = mapOf(
        RatesState.Higher to appProperties.giphy.tag.higher,
        RatesState.Equal to appProperties.giphy.tag.equal,
        RatesState.Lower to appProperties.giphy.tag.lower,
    )

    @Throws(UnsupportedCurrencyException::class)
    fun getGifFor(currency: String): ByteArray {
        val ratesState = exchangeRatesService.getRatesStateFor(currency)
        val tag = ratesToTags[ratesState]!!
        val giphyResponse = giphyRepository.getGiphy(tag)
        return giphyResponse.gif
    }
}