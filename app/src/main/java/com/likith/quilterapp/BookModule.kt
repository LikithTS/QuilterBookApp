package com.likith.quilterapp

import com.likith.data.di.dataModule
import com.likith.presentation.di.bookPresentationModule
import org.koin.core.context.startKoin


fun initKoin() {

    startKoin {
        modules(
            dataModule,
            bookPresentationModule
        )
    }
}