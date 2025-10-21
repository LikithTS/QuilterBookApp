package com.likith.data.dto

data class ReadingLogEntry(
    val logged_date: String,
    val logged_edition: String,
    val work: Work
)