package com.likith.presentation.books

import app.cash.turbine.test
import com.likith.domain.repository.BooksRepository
import com.likith.domain.util.DataError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BookViewModelTest {

    private lateinit var viewModel: BookViewModel
    private lateinit var booksRepository: BooksRepository
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `get books data based on username`() = runTest {
        booksRepository = BooksRepositorySuccessImpl()
        viewModel = BookViewModel(booksRepository)

        viewModel.bookUiState.test {

            val bookDataSuccessEmission = awaitItem()
            when (bookDataSuccessEmission) {
                is BookUiState.FailureBookData -> {
                    Assert.fail("Expected success but got failed")
                }

                BookUiState.Ideal -> {
                    Assert.fail("Expected success but got Ideal")
                }

                BookUiState.Loading -> {
                    Assert.fail("Expected success but got loading")
                }

                is BookUiState.SuccessBookData -> {
                    Assert.assertEquals(
                        "OL2624319W",
                        bookDataSuccessEmission.bookSummary.first().key
                    )
                    Assert.assertEquals(2, bookDataSuccessEmission.bookSummary.size)
                }
            }
        }
    }

    @Test
    fun `get failure response when server is down`() = runTest {
        booksRepository = BooksRepositoryFailureImpl()
        viewModel = BookViewModel(booksRepository)

        viewModel.bookUiState.test {

            val bookDataFailureEmission = awaitItem()
            when (bookDataFailureEmission) {
                is BookUiState.FailureBookData -> {
                    Assert.assertEquals(DataError.Remote.SERVER_ERROR, bookDataFailureEmission.error)
                }

                BookUiState.Ideal -> {
                    Assert.fail("Expected success but got Ideal")
                }

                BookUiState.Loading -> {
                    Assert.fail("Expected success but got loading")
                }

                is BookUiState.SuccessBookData -> {
                    Assert.fail("Expected failure but got success")
                }
            }
        }
    }
}