package com.likith.data.remote

import com.likith.data.dto.BookApiData
import com.likith.data.util.NetworkConstant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BooksApi {

    @GET(NetworkConstant.BOOK_LIST_API)
    suspend fun getBookData(
        @Path("user") user: String
    ): Response<BookApiData>

}