package com.likith.presentation.di

import com.likith.presentation.books.BookViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val bookPresentationModule = module {
    viewModelOf(::BookViewModel)
}