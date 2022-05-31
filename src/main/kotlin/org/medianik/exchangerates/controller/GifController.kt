package org.medianik.exchangerates.controller

import org.medianik.exchangerates.exception.UnsupportedCurrencyException
import org.medianik.exchangerates.service.GiphyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody
import java.io.IOException

@Controller
class GifController(
    @Autowired
    private val giphyService: GiphyService,
) {
    @GetMapping(
        value = ["/currency/{currency}"],
        produces = [MediaType.IMAGE_GIF_VALUE]
    )
    @ResponseBody
    @Throws(IOException::class, UnsupportedCurrencyException::class)
    fun getGif(@PathVariable currency: String): ByteArray {
        return giphyService.getGifFor(currency)
    }
}
