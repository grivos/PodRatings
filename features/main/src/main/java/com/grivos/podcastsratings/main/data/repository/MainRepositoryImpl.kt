package com.grivos.podcastsratings.main.data.repository

import android.content.Context
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import com.grivos.podcastsratings.main.domain.repository.MainRepository

class MainRepositoryImpl constructor(
    private val context: Context
) : MainRepository {

    override val isAccessibilityEnabled: Boolean
        get() {
            val id =
                "${context.packageName}/.accessibilityservice.presentation.PodcastsAccessibilityService"
            val alternativeId =
                "${context.packageName}/com.invariantlabs.podcastsratings.accessibilityservice.presentation.PodcastsAccessibilityService"
            val ids = setOf(id, alternativeId)
            val am = context
                .getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
            val runningServices = am
                .getEnabledAccessibilityServiceList(AccessibilityEvent.TYPES_ALL_MASK)
            return runningServices.any { service -> ids.contains(service.id) }
        }
}
