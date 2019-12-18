package com.grivos.podcastsratings.accessibilityservice.appparser

import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

interface PodcastAppParser {

    val appId: String
    fun idFullName(id: String) = "$appId:id/$id"
    fun getTitle(event: AccessibilityEvent, info: AccessibilityNodeInfo): CharSequence?
    fun getArtist(event: AccessibilityEvent, info: AccessibilityNodeInfo): CharSequence?

    fun findText(
        info: AccessibilityNodeInfo,
        viewId: String
    ): List<CharSequence> {
        return info.findAccessibilityNodeInfosByViewId(idFullName(viewId))
            .filter { it.text?.isNotEmpty() == true }
            .map { it.text }
    }

    companion object {
        const val packagePocketCasts = "au.com.shiftyjelly.pocketcasts"
    }
}
