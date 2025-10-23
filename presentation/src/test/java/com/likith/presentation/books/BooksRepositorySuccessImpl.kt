package com.likith.presentation.books

import com.likith.domain.model.BookSummary
import com.likith.domain.repository.BooksRepository
import com.likith.domain.util.DataError
import com.likith.domain.util.Result

class BooksRepositorySuccessImpl : BooksRepository {

    override suspend fun getBooksData(user: String): Result<List<BookSummary>, DataError.Remote> {
        return Result.Success(
            listOf(
                BookSummary(
                    key = "OL2624319W",
                    coverImageId = 630082,
                    title = "Killing the black body",
                    authors = listOf("Paulo Freire", "Culadasa John Yates"),
                    firstPublishedYear = 1986,
                    loggedEdition = "OL21365864M",
                    loggedDate = "2025/10/20, 05:26:59"
                ),

                BookSummary(
                    key = "OL2624318W",
                    coverImageId = 630083,
                    title = "Harry Wills",
                    authors = listOf("Paulo Freire"),
                    firstPublishedYear = 1954,
                    loggedEdition = "OL21365834H",
                    loggedDate = "2025/01/10, 03:32:43"
                )
            )
        )
    }
}