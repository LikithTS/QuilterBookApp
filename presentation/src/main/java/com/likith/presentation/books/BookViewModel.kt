package com.likith.presentation.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likith.domain.repository.BooksRepository
import com.likith.domain.util.DataError
import com.likith.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.rx3.rxSingle
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val booksRepository: BooksRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _bookUiState = MutableStateFlow<BookUiState>(BookUiState.Ideal)
    val bookUiState = _bookUiState
        .onStart {
            loadBooksUsingRx()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BookUiState.Ideal)

    fun loadBooksUsingRx(user: String = "mekBot") {
        val d = rxSingle {
            booksRepository.getBooksData(user)
        }
            .map { result ->
                when (result) {
                    is Result.Success ->
                        BookUiState.SuccessBookData(result.data)

                    is Result.Failure ->
                        BookUiState.FailureBookData(result.error)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { ui -> _bookUiState.value = ui },
                { _bookUiState.value = BookUiState.FailureBookData(DataError.Remote.UNKNOWN) }
            )

        disposables.add(d)
    }


    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}