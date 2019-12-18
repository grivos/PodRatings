package com.grivos.podcastsratings.accessibilityservice.presentation

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import com.grivos.podcastsratings.accessibilityservice.domain.model.FloaterData
import com.grivos.podcastsratings.accessibilityservice.domain.model.FloaterDataPodcast
import io.mattcarroll.hover.HoverView
import io.mattcarroll.hover.window.WindowViewController
import org.koin.android.ext.android.inject

class PodcastsAccessibilityService : AccessibilityService(), PodcastsAccessibilityView {

    private lateinit var menu: HoverPodcastsMenu
    private lateinit var hoverView: HoverView
    private val presenter: PodcastsAccessibilityPresenter by inject()

    override fun onServiceConnected() {
        super.onServiceConnected()
        hoverView = HoverView.createForWindow(
            this,
            WindowViewController(
                getSystemService(Context.WINDOW_SERVICE) as WindowManager
            )
        )
        hoverView.setOnExitListener { presenter.onUserDroppedHoverView() }
        menu = HoverPodcastsMenu(this)
        hoverView.addToWindow()
    }

    override fun onCreate() {
        super.onCreate()
        presenter.attachView(this)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        presenter.onAccessibilityEvent(event)
    }

    override fun onInterrupt() {
        // no-op
    }

    override fun showFloater(floaterData: FloaterData) {
        hoverView.isCanExpand = floaterData is FloaterDataPodcast
        menu.setData(listOf(floaterData))
        hoverView.setMenu(menu)
        hoverView.collapse()
    }

    override fun hideFloater() {
        hoverView.close()
    }

    override fun showToast(stringResId: Int) {
        Toast.makeText(this, stringResId, Toast.LENGTH_SHORT).show()
    }
}
