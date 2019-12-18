package com.grivos.podcastsratings.accessibilityservice.data.repository

import com.grivos.podcastsratings.accessibilityservice.data.datasource.PodcastRatingCacheDataSource
import com.grivos.podcastsratings.accessibilityservice.data.datasource.PodcastRatingRemoteDataSource
import com.grivos.podcastsratings.accessibilityservice.data.datasource.PodcastSearchCacheDataSource
import com.grivos.podcastsratings.accessibilityservice.data.datasource.PodcastSearchRemoteDataSource
import com.grivos.podcastsratings.accessibilityservice.domain.model.PodcastRating
import com.grivos.podcastsratings.accessibilityservice.domain.model.PodcastSearchQuery
import com.grivos.podcastsratings.accessibilityservice.domain.model.PodcastSearchResult
import com.grivos.podcastsratings.accessibilityservice.domain.repository.PodcastRepository
import com.grivos.podcastsratings.domain.Option
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class PodcastRepositoryImpl constructor(
    private val searchCacheDataSource: PodcastSearchCacheDataSource,
    private val searchRemoteDataSource: PodcastSearchRemoteDataSource,
    private val ratingCacheDataSource: PodcastRatingCacheDataSource,
    private val ratingRemoteDataSource: PodcastRatingRemoteDataSource
) : PodcastRepository {

    override fun getPodcast(podcastSearchQuery: PodcastSearchQuery): Single<Option<PodcastRating>> {
        return getPodcastSearchResult(podcastSearchQuery)
            .subscribeOn(Schedulers.io())
            .flatMap { getPodcastRating(it) }
    }

    private fun getPodcastSearchResult(podcastSearchQuery: PodcastSearchQuery): Single<PodcastSearchResult> =
        searchCacheDataSource.getPodcastSearchResult(podcastSearchQuery)
            .onErrorResumeNext {
                searchRemoteDataSource.getPodcastSearchResult(podcastSearchQuery)
                    .flatMap { remoteResult ->
                        searchCacheDataSource.setPodcastSearchResult(
                            podcastSearchQuery,
                            remoteResult
                        )
                    }
            }

    private fun getPodcastRating(podcastSearchResult: PodcastSearchResult): Single<Option<PodcastRating>> =
        ratingCacheDataSource.getRating(podcastSearchResult)
            .map { option ->
                if (option.isEmpty) {
                    throw IllegalArgumentException()
                } else {
                    option
                }
            }
            .onErrorResumeNext {
                ratingRemoteDataSource.getRating(podcastSearchResult)
                    .flatMap { remoteResult ->
                        ratingCacheDataSource.setRating(podcastSearchResult, remoteResult)
                    }
            }
}
