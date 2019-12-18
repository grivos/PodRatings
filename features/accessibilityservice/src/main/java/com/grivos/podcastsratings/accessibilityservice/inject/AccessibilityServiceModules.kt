package com.grivos.podcastsratings.accessibilityservice.inject

import com.grivos.cache.ReactiveCache
import com.grivos.podcastsratings.BuildConfig
import com.grivos.podcastsratings.accessibilityservice.data.datasource.PodcastRatingCacheDataSource
import com.grivos.podcastsratings.accessibilityservice.data.datasource.PodcastRatingRemoteDataSource
import com.grivos.podcastsratings.accessibilityservice.data.datasource.PodcastSearchCacheDataSource
import com.grivos.podcastsratings.accessibilityservice.data.datasource.PodcastSearchRemoteDataSource
import com.grivos.podcastsratings.accessibilityservice.data.repository.PodcastRepositoryImpl
import com.grivos.podcastsratings.accessibilityservice.datasource.cache.PodcastRatingCacheDataSourceImpl
import com.grivos.podcastsratings.accessibilityservice.datasource.cache.PodcastSearchCacheDataSourceImpl
import com.grivos.podcastsratings.accessibilityservice.datasource.remote.PodcastRatingRemoteDataSourceImpl
import com.grivos.podcastsratings.accessibilityservice.datasource.remote.PodcastSearchApi
import com.grivos.podcastsratings.accessibilityservice.datasource.remote.PodcastSearchRemoteDataSourceImpl
import com.grivos.podcastsratings.accessibilityservice.domain.model.PodcastRating
import com.grivos.podcastsratings.accessibilityservice.domain.model.PodcastSearchResult
import com.grivos.podcastsratings.accessibilityservice.domain.repository.PodcastRepository
import com.grivos.podcastsratings.accessibilityservice.presentation.PodcastsAccessibilityPresenter
import com.grivos.podcastsratings.network.createNetworkClient
import com.squareup.moshi.Moshi
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

object AccessibilityServiceModules {
    fun injectFeature() = loadFeature
}

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            presenterModule,
            networkModule,
            repositoryModule,
            dataSourceModule,
            cacheModule
        )
    )
}

val presenterModule: Module = module {
    single { PodcastsAccessibilityPresenter(podcastRepository = get()) }
}

val networkModule: Module = module {
    single { moshi }
    single { podcastSearchApi }
    single { PodcastRatingRemoteDataSourceImpl(moshi = get()) }
}

val repositoryModule: Module = module {
    single {
        PodcastRepositoryImpl(
            searchCacheDataSource = get(),
            searchRemoteDataSource = get(),
            ratingCacheDataSource = get(),
            ratingRemoteDataSource = get()
        ) as PodcastRepository
    }
}

val dataSourceModule: Module = module {
    single { PodcastSearchCacheDataSourceImpl(cache = get(qualifier = named(KEY_CACHE_SEARCH))) as PodcastSearchCacheDataSource }
    single { PodcastSearchRemoteDataSourceImpl(api = podcastSearchApi) as PodcastSearchRemoteDataSource }
    single { PodcastRatingCacheDataSourceImpl(cache = get(qualifier = named(KEY_CACHE_RATING))) as PodcastRatingCacheDataSource }
    single { PodcastRatingRemoteDataSourceImpl(moshi = moshi) as PodcastRatingRemoteDataSource }
}

val cacheModule: Module = module {
    single(qualifier = named(KEY_CACHE_SEARCH)) { ReactiveCache<MutableMap<String, PodcastSearchResult>>() }
    single(qualifier = named(KEY_CACHE_RATING)) { ReactiveCache<MutableMap<String, PodcastRating>>() }
}

private val moshi: Moshi = Moshi.Builder().build()
private const val ITUNES_URL = "https://itunes.apple.com/"
private val itunesRetrofit: Retrofit =
    createNetworkClient(moshi = moshi, baseUrl = ITUNES_URL, debug = BuildConfig.DEBUG)
private val podcastSearchApi: PodcastSearchApi = itunesRetrofit.create(PodcastSearchApi::class.java)
private const val KEY_CACHE_SEARCH = "search_cache"
private const val KEY_CACHE_RATING = "rating_cache"
