package com.likith.presentation.books

import com.likith.domain.model.BookSummary
import com.likith.domain.util.DataError

sealed class BookUiState {

    object Ideal : BookUiState()

    object Loading : BookUiState()

    data class SuccessBookData(
        val bookSummary: List<BookSummary>
    ) : BookUiState()

    data class FailureBookData(val error: DataError) : BookUiState()
}