package com.grivos.podcastsratings.presentation

import android.app.Application
import com.grivos.cache.CacheLibrary
import com.grivos.podcastsratings.BuildConfig
import com.grivos.podcastsratings.accessibilityservice.inject.AccessibilityServiceModules
import com.grivos.podcastsratings.main.inject.MainModules
import com.grivos.spanomatic.SpanomaticInterceptor
import io.github.inflationx.viewpump.ViewPump
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class PodcastsRatingsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    SpanomaticInterceptor()
                )
                .build()
        )
        initKoin()
        CacheLibrary.init(this)
    }

    private fun initKoin() {
        startKoin { androidContext(this@PodcastsRatingsApplication) }
        AccessibilityServiceModules.injectFeature()
        MainModules.injectFeature()
    }
}
