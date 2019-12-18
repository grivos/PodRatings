package com.grivos.podcastsratings.accessibilityservice.presentation

import android.content.Context
import android.view.View
import android.webkit.WebView
import io.mattcarroll.hover.Content

class HoverPodcastContent(context: Context, url: String) : Content {

    private val webView = WebView(context)

    init {
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.loadUrl(url)
    }

    override fun onShown() {
        // no-op
    }

    override fun getView(): View {
        return webView
    }

    override fun onHidden() {
        // no-op
    }

    override fun isFullscreen(): Boolean {
        return true
    }
}
