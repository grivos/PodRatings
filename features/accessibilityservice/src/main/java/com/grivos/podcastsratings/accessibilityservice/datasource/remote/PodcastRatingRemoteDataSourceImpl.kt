package com.grivos.podcastsratings.accessibilityservice.datasource.remote

import com.grivos.podcastsratings.accessibilityservice.data.datasource.PodcastRatingRemoteDataSource
import com.grivos.podcastsratings.accessibilityservice.domain.model.PodcastRating
import com.grivos.podcastsratings.accessibilityservice.domain.model.PodcastSearchResult
import com.grivos.podcastsratings.domain.Option
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import io.reactivex.Single
import org.jsoup.Jsoup

class PodcastRatingRemoteDataSourceImpl constructor(
    moshi: Moshi
) : PodcastRatingRemoteDataSource {

    private val adapter = moshi.adapter(PodcastShowScript::class.java)

    override fun getRating(podcastSearchResult: PodcastSearchResult) =
        Single.fromCallable {
            val document = Jsoup.connect(podcastSearchResult.podcastUrl).get()
            val podcastShowScript =
                document.getElementsByAttributeValue("name", "schema:podcast-show")
                    .firstOrNull()
                    ?.let { podcastShowScriptElement ->
                        val json = podcastShowScriptElement.data()
                        adapter.fromJson(json)
                    }
            val rating = if (podcastShowScript != null) {
                val url = "${podcastSearchResult.podcastUrl}#see-all/reviews"
                PodcastRating(
                    podcastSearchResult.podcastId,
                    podcastSearchResult.podcastName,
                    podcastShowScript.aggregateRating.ratingValue,
                    podcastShowScript.aggregateRating.ratingCount,
                    url,
                    podcastSearchResult.artworkUrl,
                    System.currentTimeMillis()
                )
            } else {
                null
            }
            Option(rating)
        }
}

data class PodcastShowScript(
    @field:Json(name = "aggregateRating") val aggregateRating: AggregateRating
)

data class AggregateRating(
    @field:Json(name = "ratingCount") val ratingCount: Int,
    @field:Json(name = "ratingValue") val ratingValue: Float
)
