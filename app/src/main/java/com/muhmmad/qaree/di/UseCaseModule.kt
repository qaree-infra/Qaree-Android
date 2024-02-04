package com.muhmmad.qaree.di

import com.muhmmad.domain.repo.AuthRepo
import com.muhmmad.domain.usecase.LoginUseCase
import com.muhmmad.domain.usecase.RegisterUseCase
import com.muhmmad.domain.usecase.VerificationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideLoginUseCase(authRepo: AuthRepo): LoginUseCase {
        return LoginUseCase(authRepo)
    }

    @Provides
    fun provideRegisterUseCase(authRepo: AuthRepo): RegisterUseCase {
        return RegisterUseCase(authRepo)
    }

    @Provides
    fun provideVerificationUseCase(authRepo: AuthRepo): VerificationUseCase {
        return VerificationUseCase(authRepo)
    }
}