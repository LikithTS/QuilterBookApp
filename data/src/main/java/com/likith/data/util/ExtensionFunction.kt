package com.likith.data.util

import com.likith.data.dto.ReadingLogEntry
import com.likith.domain.model.BookSummary

fun ReadingLogEntry.toBookSummaryModel(): BookSummary {
    return BookSummary(
        key = this.work.key.replace("/works/", ""),
        coverImageId = this.work.cover_id,
        title = this.work.title,
        authors = this.work.author_names,
        firstPublishedYear = this.work.first_publish_year,
        loggedDate = this.logged_date,
        loggedEdition = this.work.lending_edition_s
    )
}