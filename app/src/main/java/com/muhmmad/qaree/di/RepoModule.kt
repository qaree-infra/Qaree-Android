package com.muhmmad.qaree.di

import com.muhmmad.data.repo.AuthRepoImpl
import com.muhmmad.domain.local.LocalDataSource
import com.muhmmad.domain.remote.RemoteDataSource
import com.muhmmad.domain.repo.AuthRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    fun provideAuthRepo(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): AuthRepo {
        return AuthRepoImpl(remoteDataSource, localDataSource)
    }
}