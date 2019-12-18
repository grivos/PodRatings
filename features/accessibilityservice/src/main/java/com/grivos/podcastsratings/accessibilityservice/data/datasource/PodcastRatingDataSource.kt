package com.grivos.podcastsratings.accessibilityservice.data.datasource

import com.grivos.podcastsratings.accessibilityservice.domain.model.PodcastRating
import com.grivos.podcastsratings.accessibilityservice.domain.model.PodcastSearchResult
import com.grivos.podcastsratings.domain.Option
import io.reactivex.Single

interface PodcastRatingCacheDataSource {
    fun getRating(podcastSearchResult: PodcastSearchResult): Single<Option<PodcastRating>>
    fun setRating(
        podcastSearchResult: PodcastSearchResult,
        podcastRating: Option<PodcastRating>
    ): Single<Option<PodcastRating>>
}

interface PodcastRatingRemoteDataSource {
    fun getRating(podcastSearchResult: PodcastSearchResult): Single<Option<PodcastRating>>
}
