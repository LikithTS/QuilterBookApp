package com.likith.presentation.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likith.domain.repository.BooksRepository
import com.likith.domain.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookViewModel(
    private val booksRepository: BooksRepository
) : ViewModel() {

    private val _bookUiState = MutableStateFlow<BookUiState>(BookUiState.Ideal)
    val bookUiState = _bookUiState.asStateFlow()

    fun getBookData() {
        viewModelScope.launch {
//            if(_bookUiState.value != BookUiState.Ideal) return@launch
            _bookUiState.update {
                BookUiState.Loading
            }

            //User is hardcoded for now
            val bookData = booksRepository.getBooksData("mekBot")
            when (bookData) {
                is Result.Success -> {
                    _bookUiState.update { BookUiState.SuccessBookData(bookData.data) }
                }

                is Result.Failure -> {
                    _bookUiState.update { BookUiState.FailureBookData(bookData.error) }
                }
            }
        }
    }
}