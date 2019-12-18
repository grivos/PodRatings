package com.grivos.podcastsratings.accessibilityservice.presentation

import android.view.accessibility.AccessibilityNodeInfo

val AccessibilityNodeInfo.children: Sequence<AccessibilityNodeInfo>
    get() = object : Sequence<AccessibilityNodeInfo> {
        override fun iterator() = this@children.iterator()
    }

operator fun AccessibilityNodeInfo.iterator() = object : Iterator<AccessibilityNodeInfo> {
    private var index = 0
    override fun hasNext() = index < childCount
    override fun next() = getChild(index++) ?: throw IndexOutOfBoundsException()
}
