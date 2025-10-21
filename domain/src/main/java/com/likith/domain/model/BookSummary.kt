package com.likith.domain.model

data class BookSummary(
    val key: String,
    val coverImageId: Int,
    val title: String,
    val authors: List<String>,
    val firstPublishedYear: Int,
    val loggedEdition: String?,
    val loggedDate: String?
)
