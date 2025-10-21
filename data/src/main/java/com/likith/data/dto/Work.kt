package com.likith.data.dto

data class Work(
    val author_keys: List<String>,
    val author_names: List<String>,
    val cover_edition_key: String,
    val cover_id: Int,
    val edition_key: List<String>,
    val first_publish_year: Int,
    val key: String,
    val lending_edition_s: String?,
    val title: String
)