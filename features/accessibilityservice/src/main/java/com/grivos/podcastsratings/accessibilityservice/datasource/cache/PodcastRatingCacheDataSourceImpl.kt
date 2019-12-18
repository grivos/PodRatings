package com.grivos.podcastsratings.accessibilityservice.datasource.cache

import com.grivos.cache.ReactiveCache
import com.grivos.podcastsratings.accessibilityservice.data.datasource.PodcastRatingCacheDataSource
import com.grivos.podcastsratings.accessibilityservice.domain.model.PodcastRating
import com.grivos.podcastsratings.accessibilityservice.domain.model.PodcastSearchResult
import com.grivos.podcastsratings.domain.Option
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class PodcastRatingCacheDataSourceImpl constructor(
    private val cache: ReactiveCache<MutableMap<String, PodcastRating>>
) : PodcastRatingCacheDataSource {

    private val key = "Podcast Rating"

    override fun getRating(podcastSearchResult: PodcastSearchResult): Single<Option<PodcastRating>> =
        cache.load(key)
            .map { map ->
                val podcastRating = map[podcastSearchResult.podcastId]
                if (podcastRating != null) {
                    val deltaMillis = System.currentTimeMillis() - podcastRating.timestamp
                    val valid =
                        TimeUnit.HOURS.convert(deltaMillis, TimeUnit.MILLISECONDS) <= maxHours
                    if (valid) {
                        Option(podcastRating)
                    } else {
                        Option.empty()
                    }
                } else {
                    Option.empty()
                }
            }

    override fun setRating(
        podcastSearchResult: PodcastSearchResult,
        podcastRatingOption: Option<PodcastRating>
    ): Single<Option<PodcastRating>> = cache.load(key, mutableMapOf())
        .map { map ->
            if (!podcastRatingOption.isEmpty) {
                map[podcastSearchResult.podcastId] = podcastRatingOption.get()
            }
            map
        }
        .flatMap { cache.save(key, it) }
        .map { podcastRatingOption }
}

private const val maxHours = 24
