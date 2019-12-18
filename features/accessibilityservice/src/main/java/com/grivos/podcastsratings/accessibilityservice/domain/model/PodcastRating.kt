package com.grivos.podcastsratings.accessibilityservice.domain.model

data class PodcastRating(
    val id: String,
    val title: String,
    val rating: Float,
    val ratingsCount: Int,
    val podcastUrl: String,
    val artworkUrl: String?,
    val timestamp: Long
)
