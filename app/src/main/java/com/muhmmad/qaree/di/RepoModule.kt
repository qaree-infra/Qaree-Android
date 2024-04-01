package com.muhmmad.qaree.di

import com.muhmmad.data.repo.AuthRepoImpl
import com.muhmmad.data.repo.BookRepoImpl
import com.muhmmad.data.repo.HomeRepoImpl
import com.muhmmad.data.repo.LibraryRepoImpl
import com.muhmmad.data.repo.ReadingViewRepoImpl
import com.muhmmad.data.repo.UserRepoImpl
import com.muhmmad.domain.local.LocalDataSource
import com.muhmmad.domain.remote.GraphQlDataSource
import com.muhmmad.domain.remote.RetrofitDataSource
import com.muhmmad.domain.repo.AuthRepo
import com.muhmmad.domain.repo.BookRepo
import com.muhmmad.domain.repo.HomeRepo
import com.muhmmad.domain.repo.LibraryRepo
import com.muhmmad.domain.repo.ReadingViewRepo
import com.muhmmad.domain.repo.UserRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    fun provideAuthRepo(
        graphQlDataSource: GraphQlDataSource,
        localDataSource: LocalDataSource
    ): AuthRepo = AuthRepoImpl(graphQlDataSource, localDataSource)

    @Provides
    fun provideHomeRepo(
        graphQlDataSource: GraphQlDataSource,
    ): HomeRepo = HomeRepoImpl(graphQlDataSource)

    @Provides
    fun provideBookRepo(
        graphQlDataSource: GraphQlDataSource,
    ): BookRepo = BookRepoImpl(graphQlDataSource)

    @Provides
    fun provideUserRepo(
        graphQlDataSource: GraphQlDataSource,
        retrofitDataSource: RetrofitDataSource
    ): UserRepo = UserRepoImpl(graphQlDataSource, retrofitDataSource)

    @Provides
    fun provideLibraryRepo(
        graphQlDataSource: GraphQlDataSource,
    ): LibraryRepo = LibraryRepoImpl(graphQlDataSource)

    @Provides
    fun provideReadingViewRepo(
        graphQlDataSource: GraphQlDataSource,
        retrofitDataSource: RetrofitDataSource
    ): ReadingViewRepo = ReadingViewRepoImpl(graphQlDataSource, retrofitDataSource)
}