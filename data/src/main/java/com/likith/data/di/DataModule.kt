package com.likith.data.di

import com.likith.data.remote.BooksApi
import com.likith.data.repository.BooksRepositoryImpl
import com.likith.data.util.NetworkConstant
import com.likith.domain.repository.BooksRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(NetworkConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)
    }

    singleOf(::BooksRepositoryImpl) bind BooksRepository::class

}