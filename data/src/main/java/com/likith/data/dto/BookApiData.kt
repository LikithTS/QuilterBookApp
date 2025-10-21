package com.likith.data.dto

data class BookApiData(
    val numFound: Int,
    val page: Int,
    val reading_log_entries: List<ReadingLogEntry>
)