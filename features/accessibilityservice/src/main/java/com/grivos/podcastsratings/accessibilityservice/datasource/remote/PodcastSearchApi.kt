package com.grivos.podcastsratings.accessibilityservice.datasource.remote

import com.grivos.podcastsratings.accessibilityservice.datasource.model.SearchResultWrapperEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PodcastSearchApi {

    @GET("search?entity=podcast")
    fun search(@Query("term") searchQuery: String): Single<SearchResultWrapperEntity>
}
