package com.grivos.podcastsratings.accessibilityservice.datasource.remote

import com.grivos.podcastsratings.accessibilityservice.data.datasource.PodcastSearchRemoteDataSource
import com.grivos.podcastsratings.accessibilityservice.datasource.model.toDomain
import com.grivos.podcastsratings.accessibilityservice.domain.model.PodcastSearchQuery
import com.grivos.podcastsratings.accessibilityservice.domain.model.PodcastSearchResult
import io.reactivex.Single

class PodcastSearchRemoteDataSourceImpl constructor(
    private val api: PodcastSearchApi
) : PodcastSearchRemoteDataSource {

    override fun getPodcastSearchResult(podcastSearchQuery: PodcastSearchQuery): Single<PodcastSearchResult> =
        api.search("${podcastSearchQuery.title} ${podcastSearchQuery.artistName}")
            .map { wrapper ->
                wrapper.results.first {
                    it.artistName.equals(
                        podcastSearchQuery.artistName,
                        ignoreCase = true
                    )
                }.toDomain()
            }
}
