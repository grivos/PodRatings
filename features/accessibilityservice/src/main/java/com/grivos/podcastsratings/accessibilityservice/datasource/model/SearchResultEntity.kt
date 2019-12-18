package com.grivos.podcastsratings.accessibilityservice.datasource.model

import com.grivos.podcastsratings.accessibilityservice.domain.model.PodcastSearchResult
import com.squareup.moshi.Json

data class SearchResultEntity(
    @field:Json(name = "collectionViewUrl") val collectionViewUrl: String,
    @field:Json(name = "collectionName") val collectionName: String,
    @field:Json(name = "collectionId") val collectionId: String,
    @field:Json(name = "artistName") val artistName: String,
    @field:Json(name = "artworkUrl100") val artworkUrl: String?
)

fun SearchResultEntity.toDomain(): PodcastSearchResult {
    return PodcastSearchResult(
        podcastName = collectionName,
        podcastUrl = collectionViewUrl,
        podcastId = collectionId,
        artworkUrl = artworkUrl
    )
}
