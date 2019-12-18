package com.grivos.podcastsratings.accessibilityservice.domain.repository

import com.grivos.podcastsratings.accessibilityservice.domain.model.PodcastRating
import com.grivos.podcastsratings.accessibilityservice.domain.model.PodcastSearchQuery
import com.grivos.podcastsratings.domain.Option
import io.reactivex.Single

interface PodcastRepository {

    fun getPodcast(podcastSearchQuery: PodcastSearchQuery): Single<Option<PodcastRating>>
}
