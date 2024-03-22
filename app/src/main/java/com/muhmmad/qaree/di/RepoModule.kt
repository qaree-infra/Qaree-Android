package com.muhmmad.qaree.di

import com.muhmmad.data.repo.AuthRepoImpl
import com.muhmmad.data.repo.HomeRepoImpl
import com.muhmmad.domain.local.LocalDataSource
import com.muhmmad.domain.remote.GraphQlDataSource
import com.muhmmad.domain.remote.RetrofitDataSource
import com.muhmmad.domain.repo.AuthRepo
import com.muhmmad.domain.repo.HomeRepo
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
        retrofitDataSource: RetrofitDataSource
    ): HomeRepo = HomeRepoImpl(graphQlDataSource,retrofitDataSource)
}