package com.grivos.podcastsratings.accessibilityservice.presentation

import android.view.accessibility.AccessibilityEvent
import com.grivos.podcastsratings.BuildConfig
import com.grivos.podcastsratings.R
import com.grivos.podcastsratings.accessibilityservice.appparser.PocketCastsParser
import com.grivos.podcastsratings.accessibilityservice.appparser.PodcastAppParser.Companion.packagePocketCasts
import com.grivos.podcastsratings.accessibilityservice.domain.model.*
import com.grivos.podcastsratings.accessibilityservice.domain.repository.PodcastRepository
import com.grivos.presentation.base.BasePresenter
import com.grivos.presentation.base.MvpView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import timber.log.Timber

interface PodcastsAccessibilityView : MvpView {
    fun showFloater(floaterData: FloaterData)
    fun hideFloater()
    fun showToast(stringResId: Int)
}

class PodcastsAccessibilityPresenter(
    private val podcastRepository: PodcastRepository
) : BasePresenter<PodcastsAccessibilityView>() {

    private var disposable: Disposable? = null
    private var lastQuery: PodcastSearchQuery? = null
    private var lastRating: PodcastRating? = null
    private var displayStatus = DisplayStatus.Hidden

    fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (BuildConfig.DEBUG) {
            Timber.d("onAccessibilityEvent: ${AccessibilityEvent.eventTypeToString(event.eventType)}, ${event.packageName}, ${event.action}, ${event.text}, ${event.className}")
        }

        if (supportedPackages.contains(event.packageName)) {
            val info = event.source ?: return
            parsers[info.packageName]?.let { parser ->
                val title = parser.getTitle(event, info)?.toString()
                val artist = parser.getArtist(event, info)?.toString()
                val query = if (title.isNullOrEmpty() || artist.isNullOrEmpty()) {
                    null
                } else {
                    PodcastSearchQuery(artistName = artist, title = title)
                }
                handleQuery(query)
            }
        }
    }

    private fun handleQuery(query: PodcastSearchQuery?) {
        when (query) {
            null -> { /* no-op */
            }
            lastQuery -> {
                if (displayStatus == DisplayStatus.Hidden) {
                    lastRating?.let { rating ->
                        displayStatus = DisplayStatus.Displaying
                        mvpView?.showFloater(FloaterDataPodcast(rating))
                    }
                }
            }
            else -> {
                lastQuery = query
                lastRating = null
                getPodcastRating(query)
            }
        }
    }

    private fun getPodcastRating(podcastSearchQuery: PodcastSearchQuery) {
        disposable?.dispose()
        mvpView?.showFloater(FloaterDataLoading)
        displayStatus = DisplayStatus.Displaying
        disposable = podcastRepository.getPodcast(podcastSearchQuery)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { option ->
                    if (!option.isEmpty) {
                        val rating = option.get()
                        lastRating = rating
                        mvpView?.showFloater(FloaterDataPodcast(rating))
                    }
                },
                { error ->
                    Timber.e(error, "Error searching podcast")
                    displayStatus = DisplayStatus.Hidden
                    mvpView?.hideFloater()
                    mvpView?.showToast(R.string.podcast_not_fount)
                }
            )
    }

    fun onUserDroppedHoverView() {
        disposable?.dispose()
        displayStatus = DisplayStatus.UserDroppedHoverView
    }

    companion object {

        val supportedPackages = setOf(packagePocketCasts)
        val parsers = mapOf(packagePocketCasts to PocketCastsParser())
    }
}

private enum class DisplayStatus {
    Displaying,
    Hidden,
    UserDroppedHoverView,
}
