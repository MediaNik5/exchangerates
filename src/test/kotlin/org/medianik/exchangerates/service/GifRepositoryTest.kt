package org.medianik.exchangerates.service

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.medianik.exchangerates.config.AppProperties
import org.medianik.exchangerates.model.*
import org.medianik.exchangerates.repository.GiphyRepository
import org.medianik.exchangerates.repository.OpenExchangeRatesRepository
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GifRepositoryTest{
    private val higherTag: String = "rich"
    private val equalTag: String = "nothing"
    private val lowerTag: String = "broke"

    private val giphyRepository = Mockito.mock(GiphyRepository::class.java)
    private val openExchangeRatesRepository = Mockito.mock(OpenExchangeRatesRepository::class.java)
    private val appProperties = AppProperties(
        AppProperties.Giphy(
            AppProperties.Giphy.Tag(higherTag, equalTag, lowerTag),
            "someUrl", "someToken", "somePath"
        ),
        AppProperties.OpenExchangeRates("RUB", "someToken", "someUrl", "somePath")
    )
    private val exchangeRatesService = ExchangeRatesService(appProperties, openExchangeRatesRepository)
    private val gifRepository = GiphyService(appProperties, giphyRepository, exchangeRatesService)

    @Test
    fun `Test higher, equal and lower tags`(){
        Mockito.`when`(giphyRepository.getGiphy(higherTag)).thenReturn(higherItem())
        Mockito.`when`(giphyRepository.getGiphy(equalTag)).thenReturn(equalItem())
        Mockito.`when`(giphyRepository.getGiphy(lowerTag)).thenReturn(lowerItem())

        Mockito.`when`(openExchangeRatesRepository.getExchangeRatesToday("USD")).thenReturn(ExchangeRatesResponse(mapOf("USD" to 5.0, "RUB" to 1.0)))
        Mockito.`when`(openExchangeRatesRepository.getExchangeRatesToday("EUR")).thenReturn(ExchangeRatesResponse(mapOf("EUR" to 5.0, "RUB" to 1.0)))
        Mockito.`when`(openExchangeRatesRepository.getExchangeRatesToday("JPY")).thenReturn(ExchangeRatesResponse(mapOf("JPY" to 5.0, "RUB" to 1.0)))

        Mockito.`when`(openExchangeRatesRepository.getExchangeRatesYesterday("USD")).thenReturn(ExchangeRatesResponse(mapOf("USD" to 6.0, "RUB" to 1.0)))
        Mockito.`when`(openExchangeRatesRepository.getExchangeRatesYesterday("EUR")).thenReturn(ExchangeRatesResponse(mapOf("EUR" to 5.0, "RUB" to 1.0)))
        Mockito.`when`(openExchangeRatesRepository.getExchangeRatesYesterday("JPY")).thenReturn(ExchangeRatesResponse(mapOf("JPY" to 4.5, "RUB" to 1.0)))

        Assertions.assertThat(gifRepository.getGifFor("USD")).isEqualTo(higherItem().gif).hasSize(287678)
        Assertions.assertThat(gifRepository.getGifFor("EUR")).isEqualTo(equalItem().gif).hasSize(466377)
        Assertions.assertThat(gifRepository.getGifFor("JPY")).isEqualTo(lowerItem().gif).hasSize(312820)
    }
    private fun higherItem()=
        GiphyResponse(GifObject(Images(RawGif("https://media1.giphy.com/media/LdOyjZ7io5Msw/giphy.webp?cid=790b7611402225ba3a59677cb82c9bcfbd4a20436a21578c&rid=giphy.webp&ct=g", "287678"))))

    private fun equalItem() =
        GiphyResponse(GifObject(Images(RawGif("https://media0.giphy.com/media/78V9H1GW150m4/giphy.webp?cid=790b76119bf5a9c0db264c732277e604b956bb494bd875a4&rid=giphy.webp&ct=g", "466377"))))


    private fun lowerItem() =
        GiphyResponse(GifObject(Images(RawGif("https://media2.giphy.com/media/ZGH8VtTZMmnwzsYYMf/giphy.webp?cid=790b761183e8ba85b6018c460db7f6045b8e8802a5452710&rid=giphy.webp&ct=g", "312820"))))

    @Test
    fun `Test media0 link`(){
        Mockito.`when`(giphyRepository.getGiphy(higherTag)).thenReturn(media0())
        Assertions.assertThat(giphyRepository.getGiphy(higherTag).gif).isEqualTo(media0().gif).hasSize(466377)
    }

    @Test
    fun `Test media1 link`(){
        Mockito.`when`(giphyRepository.getGiphy(higherTag)).thenReturn(media1())
        Assertions.assertThat(giphyRepository.getGiphy(higherTag).gif).isEqualTo(media1().gif).hasSize(1099028)
    }

    @Test
    fun `Test media2 link`(){
        Mockito.`when`(giphyRepository.getGiphy(higherTag)).thenReturn(media2())
        Assertions.assertThat(giphyRepository.getGiphy(higherTag).gif).isEqualTo(media2().gif).hasSize(1858810)
    }

    @Test
    fun `Test media3 link`(){
        Mockito.`when`(giphyRepository.getGiphy(higherTag)).thenReturn(media3())
        Assertions.assertThat(giphyRepository.getGiphy(higherTag).gif).isEqualTo(media3().gif).hasSize(287678)
    }

    @Test
    fun `Test media4 link`(){
        Mockito.`when`(giphyRepository.getGiphy(higherTag)).thenReturn(media4())
        Assertions.assertThat(giphyRepository.getGiphy(higherTag).gif).isEqualTo(media4().gif).hasSize(267180)
    }
    private fun media0() =
        GiphyResponse(GifObject(Images(RawGif("https://media0.giphy.com/media/78V9H1GW150m4/giphy.webp?cid=790b76119bf5a9c0db264c732277e604b956bb494bd875a4&rid=giphy.webp&ct=g", "466377"))))
    private fun media1() =
        GiphyResponse(GifObject(Images(RawGif("https://media1.giphy.com/media/l1J9EZEsT79Bbe16E/giphy.webp?cid=790b7611658267cbd499d5042220fda1339e093012397cf7&rid=giphy.webp&ct=g", "1099028"))))
    private fun media2() =
        GiphyResponse(GifObject(Images(RawGif("https://media2.giphy.com/media/lptjRBxFKCJmFoibP3/giphy.webp?cid=790b7611c75bf74c08af39497aca01a7bebd5ed5b6e0cfaa&rid=giphy.webp&ct=g", "1858810"))))
    private fun media3() =
        GiphyResponse(GifObject(Images(RawGif("https://media3.giphy.com/media/LdOyjZ7io5Msw/giphy.webp?cid=790b761128e64e3758941003a536875a28217f83fd25e1b8&rid=giphy.webp&ct=g", "287678"))))
    private fun media4() =
        GiphyResponse(GifObject(Images(RawGif("https://media4.giphy.com/media/z7W3Ljw7oslTq/giphy.webp?cid=790b761133f0f7d76b0d9237b01a1b7af7931841bb5bbf05&rid=giphy.webp&ct=g", "267180"))))
}