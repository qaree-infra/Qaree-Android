package com.muhmmad.qaree.di

import com.muhmmad.domain.repo.AuthRepo
import com.muhmmad.domain.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideLoginUseCase(authRepo: AuthRepo): LoginUseCase {
        return LoginUseCase(authRepo)
    }
}