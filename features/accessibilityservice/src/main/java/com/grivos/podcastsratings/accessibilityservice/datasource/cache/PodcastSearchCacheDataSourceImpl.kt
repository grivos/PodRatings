package com.grivos.podcastsratings.accessibilityservice.datasource.cache

import com.grivos.cache.ReactiveCache
import com.grivos.podcastsratings.accessibilityservice.data.datasource.PodcastSearchCacheDataSource
import com.grivos.podcastsratings.accessibilityservice.domain.model.PodcastSearchQuery
import com.grivos.podcastsratings.accessibilityservice.domain.model.PodcastSearchResult
import io.reactivex.Single

class PodcastSearchCacheDataSourceImpl constructor(
    private val cache: ReactiveCache<MutableMap<PodcastSearchQuery, PodcastSearchResult>>
) : PodcastSearchCacheDataSource {

    private val key = "Podcast Search Result"

    override fun getPodcastSearchResult(podcastSearchQuery: PodcastSearchQuery): Single<PodcastSearchResult> =
        cache.load(key)
            .map { map -> map[podcastSearchQuery] }

    override fun setPodcastSearchResult(
        podcastSearchQuery: PodcastSearchQuery,
        result: PodcastSearchResult
    ): Single<PodcastSearchResult> = cache.load(key, mutableMapOf())
        .map { map ->
            map[podcastSearchQuery] = result
            map
        }
        .flatMap { cache.save(key, it) }
        .map { result }
}
