package com.grivos.podcastsratings.accessibilityservice.datasource.model

import com.squareup.moshi.Json

data class SearchResultWrapperEntity(
    @field:Json(name = "results") val results: List<SearchResultEntity>
)
