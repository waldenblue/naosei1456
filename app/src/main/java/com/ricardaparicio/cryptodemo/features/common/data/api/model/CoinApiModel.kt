package com.ricardaparicio.cryptodemo.features.common.data.api.model

data class CoinApiModel(
    val id: String,
    val symbol: String,
    val name: String,
    val links: CoinLinksApiModel,
    val image: CoinImagesApiModel,
    val market_data: CoinMarketDataApiModel,
    val description: CoinDescriptionApiModel,
    val market_cap_rank: Int,
)