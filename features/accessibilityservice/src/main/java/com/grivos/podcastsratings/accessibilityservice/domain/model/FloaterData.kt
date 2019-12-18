package com.grivos.podcastsratings.accessibilityservice.domain.model

sealed class FloaterData
object FloaterDataLoading : FloaterData()
data class FloaterDataPodcast(val podcastRating: PodcastRating) : FloaterData()
