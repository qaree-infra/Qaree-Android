package com.muhmmad.qaree.di

import com.muhmmad.domain.repo.AuthRepo
import com.muhmmad.domain.usecase.AuthUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideAuthUseCase(authRepo: AuthRepo): AuthUseCase = AuthUseCase(authRepo)
}