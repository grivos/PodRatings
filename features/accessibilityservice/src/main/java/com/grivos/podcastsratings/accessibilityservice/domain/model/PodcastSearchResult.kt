package com.grivos.podcastsratings.accessibilityservice.domain.model

data class PodcastSearchResult(
    val podcastName: String,
    val podcastUrl: String,
    val podcastId: String,
    val artworkUrl: String?
)
