package com.grivos.podcastsratings.accessibilityservice.presentation

import android.content.Context
import android.view.View
import io.mattcarroll.hover.Content

class HoverPodcastLoadingContent(context: Context) : Content {

    private val view = View(context)

    override fun onShown() {
        // no-op
    }

    override fun getView(): View {
        return view
    }

    override fun onHidden() {
        // no-op
    }

    override fun isFullscreen(): Boolean {
        return false
    }
}
