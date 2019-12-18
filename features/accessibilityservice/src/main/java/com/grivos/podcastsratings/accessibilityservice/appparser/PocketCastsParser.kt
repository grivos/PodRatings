package com.grivos.podcastsratings.accessibilityservice.appparser

import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.grivos.podcastsratings.accessibilityservice.appparser.PodcastAppParser.Companion.packagePocketCasts
import com.grivos.podcastsratings.accessibilityservice.presentation.children

class PocketCastsParser : PodcastAppParser {

    override val appId: String
        get() = packagePocketCasts

    override fun getTitle(event: AccessibilityEvent, info: AccessibilityNodeInfo): CharSequence? =
        info.findAccessibilityNodeInfosByViewId(idFullName("podcast_header"))
            ?.firstOrNull()
            ?.let { podcastHeader ->
                podcastHeader.children.firstOrNull { child ->
                    child.viewIdResourceName == idFullName(
                        "title"
                    )
                }
            }
            ?.text

    override fun getArtist(event: AccessibilityEvent, info: AccessibilityNodeInfo): CharSequence? =
        info.findAccessibilityNodeInfosByViewId(idFullName("podcast_header"))
            ?.firstOrNull()
            ?.let { podcastHeader ->
                podcastHeader.children.firstOrNull { child ->
                    child.viewIdResourceName == idFullName(
                        "author_text"
                    )
                }
            }
            ?.text
}
