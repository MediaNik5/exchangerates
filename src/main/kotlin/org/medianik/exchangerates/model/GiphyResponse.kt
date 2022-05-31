package org.medianik.exchangerates.model

import java.net.URL

data class GiphyResponse(private val data: GifObject) {
    val gif
        get() = data.images.original.downloadGif()
}

data class GifObject(val images: Images)
data class Images(val original: RawGif)
data class RawGif(private val webp: String, private val webp_size: String) {
    private val link
        // Giphy gives link with its metainfo, but the link without this metainfo is with 'i' in the start instead of media1/media2/...
        // Dont know how to make it more clear
        get() = webp.replace("media\\d+".toRegex(), "i")
    private val size
        get() = webp_size.toInt()

    fun downloadGif(): ByteArray =
        URL(link).openStream().readNBytes(size)
}
