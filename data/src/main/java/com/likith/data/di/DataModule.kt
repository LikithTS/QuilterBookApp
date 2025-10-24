package com.likith.data.di

import com.likith.data.remote.BooksApi
import com.likith.data.repository.BooksRepositoryImpl
import com.likith.data.util.NetworkConstant
import com.likith.domain.repository.BooksRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(NetworkConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideBooksApi(retrofit: Retrofit): BooksApi =
        retrofit.create(BooksApi::class.java)

    @Provides
    @Singleton
    fun provideBooksRepository(
        booksApi: BooksApi
    ): BooksRepository = BooksRepositoryImpl(
        booksApi = booksApi
    )
}