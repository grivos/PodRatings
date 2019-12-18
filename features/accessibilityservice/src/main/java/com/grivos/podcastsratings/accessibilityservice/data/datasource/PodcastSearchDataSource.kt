package com.grivos.podcastsratings.accessibilityservice.data.datasource

import com.grivos.podcastsratings.accessibilityservice.domain.model.PodcastSearchQuery
import com.grivos.podcastsratings.accessibilityservice.domain.model.PodcastSearchResult
import io.reactivex.Single

interface PodcastSearchCacheDataSource {
    fun getPodcastSearchResult(podcastSearchQuery: PodcastSearchQuery): Single<PodcastSearchResult>
    fun setPodcastSearchResult(
        podcastSearchQuery: PodcastSearchQuery,
        result: PodcastSearchResult
    ): Single<PodcastSearchResult>
}

interface PodcastSearchRemoteDataSource {
    fun getPodcastSearchResult(podcastSearchQuery: PodcastSearchQuery): Single<PodcastSearchResult>
}
