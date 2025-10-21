package com.likith.domain.repository

import com.likith.domain.model.BookSummary
import com.likith.domain.util.DataError
import com.likith.domain.util.Result

interface BooksRepository {

    suspend fun getBooksData(user: String): Result<List<BookSummary>, DataError.Remote>
}