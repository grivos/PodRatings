package com.grivos.podcastsratings.accessibilityservice.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import coil.api.load
import coil.transform.CircleCropTransformation
import com.grivos.podcastsratings.R
import com.grivos.podcastsratings.accessibilityservice.domain.model.FloaterData
import com.grivos.podcastsratings.accessibilityservice.domain.model.FloaterDataLoading
import com.grivos.podcastsratings.accessibilityservice.domain.model.FloaterDataPodcast
import com.grivos.podcastsratings.accessibilityservice.domain.model.PodcastRating
import io.mattcarroll.hover.HoverMenu
import kotlinx.android.synthetic.main.floating_rating_view.view.*
import java.text.NumberFormat
import java.util.*

class HoverPodcastsMenu(private val context: Context) : HoverMenu() {

    private val layoutInflater = LayoutInflater.from(context)

    private var ratingsSections = emptyList<Section>()

    fun setData(datas: List<FloaterData>) {
        ratingsSections = datas.map { data ->
            when (data) {
                is FloaterDataPodcast -> {
                    val rating = data.podcastRating
                    Section(
                        SectionId(rating.id),
                        createTabView(rating),
                        HoverPodcastContent(context, rating.podcastUrl)
                    )
                }
                FloaterDataLoading -> Section(
                    SectionId("TabDataLoading"),
                    createTabViewForLoading(),
                    HoverPodcastLoadingContent(context)
                )
            }
        }
        notifyMenuChanged()
    }

    @SuppressLint("InflateParams")
    private fun createTabView(rating: PodcastRating): View {
        val view = layoutInflater.inflate(R.layout.floating_rating_view, null)
        view.ratingViewScore.text = rating.rating.toString()
        view.ratingViewCount.text =
            context.getString(R.string.raters_count_format, intToShortText(rating.ratingsCount))
        view.ratingViewArtwork.load(rating.artworkUrl) {
            transformations(CircleCropTransformation())
        }
        return view
    }

    @SuppressLint("InflateParams")
    private fun createTabViewForLoading(): View {
        return layoutInflater.inflate(R.layout.floating_rating_view_loading, null)
    }

    override fun getSections(): MutableList<Section> {
        return ratingsSections.toMutableList()
    }

    override fun getId(): String {
        return "ratings"
    }

    override fun getSection(index: Int): Section? {
        return if (index < sectionCount) ratingsSections[index] else null
    }

    override fun getSection(sectionId: SectionId): Section? {
        return ratingsSections.firstOrNull { it.id == sectionId }
    }

    override fun getSectionCount(): Int {
        return ratingsSections.size
    }

    private fun intToShortText(points: Int): String {
        return if (points < 1000) {
            numberFormat.format(points)
        } else {
            val thousands = points / 1000
            val thousandsStr = numberFormat.format(thousands)
            val hundreds = points % 1000 / 100
            if (hundreds == 0) {
                String.format("%sk", thousandsStr)
            } else {
                String.format("%s.%dk", thousandsStr, hundreds)
            }
        }
    }

    companion object {
        private val numberFormat = NumberFormat.getNumberInstance(Locale.US)
    }
}
