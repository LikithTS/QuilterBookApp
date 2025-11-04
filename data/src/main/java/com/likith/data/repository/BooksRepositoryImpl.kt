package com.likith.data.repository

import com.likith.data.remote.BooksApi
import com.likith.data.util.toBookSummaryModel
import com.likith.domain.model.BookSummary
import com.likith.domain.repository.BooksRepository
import com.likith.domain.util.DataError
import com.likith.domain.util.ErrorMapper
import com.likith.domain.util.Result
import kotlinx.coroutines.ensureActive
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class BooksRepositoryImpl @Inject constructor(
    private val booksApi: BooksApi,
    private val errorMapper: ErrorMapper
) : BooksRepository {

    override suspend fun getBooksData(user: String): Result<List<BookSummary>, DataError.Remote> {
        return try {
            val bookApiResponse = booksApi.getBookData(user = user)
            if (bookApiResponse.isSuccessful) {
                val bookSummary = bookApiResponse.body()?.reading_log_entries?.map {
                    it.toBookSummaryModel()
                }
                bookSummary?.let {
                    Result.Success(bookSummary)
                } ?: kotlin.run {
                    Result.Failure(DataError.Remote.SERIALIZATION)
                }
            } else {
                //Since it is just one api call in this project. Error is handled here.
                // In a real project this will be in common place and re-use it in different api calls.
                val errorCode = bookApiResponse.code()
                Result.Failure(errorMapper.fromHttp(errorCode))
            }
        } catch (e: Exception) {
            //Since we're catching generic exception. If coroutines throws cancellation exception.
            //We need to throw the exception back or handle it.
            //I'm handling exception here.
            coroutineContext.ensureActive()
            Result.Failure(DataError.Remote.UNKNOWN)
        }
    }
}