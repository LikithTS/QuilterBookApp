package com.likith.data.repository

import com.google.gson.Gson
import com.likith.data.dto.BookApiData
import com.likith.data.remote.BooksApi
import com.likith.domain.repository.BooksRepository
import com.likith.domain.util.DataError
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import com.likith.domain.util.Result
import junit.framework.TestCase.assertTrue
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert

class BooksRepositoryImplTest {

    private lateinit var repository: BooksRepository
    @MockK
    lateinit var booksApi: BooksApi

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = BooksRepositoryImpl(booksApi)
    }

    private fun getBookResponseData(): BookApiData? {
        val gson = Gson()
        val jsonFileReader = JsonFileReader()
        val jsonData = jsonFileReader.readSampleJsonFromResource("RecipeSampleResponse.json")
        val response = gson.fromJson(jsonData, BookApiData::class.java)
        return response
    }

    @Test
    fun `returns Success when response is 200 and body is not null`() = runTest {

        coEvery { booksApi.getBookData(user = "mekBot") } returns Response.success(getBookResponseData())

        val result = repository.getBooksData("mekBot")

        assertTrue(result is Result.Success)
        // empty list expected because reading_log_entries is empty
        Assert.assertEquals(100, (result as Result.Success).data.size)
    }

    @Test
    fun `returns Failure, when SERIALIZATION error occurs`() = runTest {
        coEvery { booksApi.getBookData(user = "mekBot") } returns Response.success(null)

        val result = repository.getBooksData("mekBot")

        assertTrue(result is Result.Failure)
        Assert.assertEquals(DataError.Remote.SERIALIZATION, (result as Result.Failure).error)
    }

    @Test
    fun `response with 404 and maps to Failure-NOT_FOUND`() = runTest {
        val errorBody = "{}".toResponseBody("application/json".toMediaTypeOrNull())
        coEvery { booksApi.getBookData(user = "mekBot") } returns Response.error(404, errorBody)

        val result = repository.getBooksData("mekBot")

        assertTrue(result is Result.Failure)
        Assert.assertEquals(DataError.Remote.NOT_FOUND, (result as Result.Failure).error)
    }

    @Test
    fun `returns Failure-UNKNOWN when api throws`() = runTest {
        coEvery { booksApi.getBookData(user = "mekBot") } throws java.io.IOException("Unable to connect to db")

        val result = repository.getBooksData("mekBot")

        assertTrue(result is Result.Failure)
        Assert.assertEquals(DataError.Remote.UNKNOWN, (result as Result.Failure).error)
    }

}