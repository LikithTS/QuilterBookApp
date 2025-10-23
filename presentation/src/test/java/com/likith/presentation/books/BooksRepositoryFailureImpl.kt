package com.likith.presentation.books

import com.likith.domain.model.BookSummary
import com.likith.domain.repository.BooksRepository
import com.likith.domain.util.DataError
import com.likith.domain.util.Result

class BooksRepositoryFailureImpl : BooksRepository {

    override suspend fun getBooksData(user: String): Result<List<BookSummary>, DataError.Remote> {
        return Result.Failure(DataError.Remote.SERVER_ERROR)
    }
}