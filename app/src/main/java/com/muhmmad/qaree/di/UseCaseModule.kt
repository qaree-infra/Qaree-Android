package com.muhmmad.qaree.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.muhmmad.data.local.UserDataSerializer
import com.muhmmad.domain.model.UserData
import com.muhmmad.domain.repo.AuthRepo
import com.muhmmad.domain.repo.BookRepo
import com.muhmmad.domain.repo.HomeRepo
import com.muhmmad.domain.repo.LibraryRepo
import com.muhmmad.domain.repo.ReadingViewRepo
import com.muhmmad.domain.repo.UserRepo
import com.muhmmad.domain.usecase.AuthUseCase
import com.muhmmad.domain.usecase.BookUseCase
import com.muhmmad.domain.usecase.HomeUseCase
import com.muhmmad.domain.usecase.LibraryUseCase
import com.muhmmad.domain.usecase.ReadingVIewUseCase
import com.muhmmad.domain.usecase.UserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideAuthUseCase(authRepo: AuthRepo): AuthUseCase = AuthUseCase(authRepo)

    @Provides
    fun provideHomeUseCase(homeRepo: HomeRepo): HomeUseCase = HomeUseCase(homeRepo)

    @Provides
    fun provideBookUseCase(bookRepo: BookRepo): BookUseCase = BookUseCase(bookRepo)

    @Provides
    fun provideLibraryUseCase(libraryRepo: LibraryRepo): LibraryUseCase =
        LibraryUseCase(libraryRepo)

    @Provides
    fun provideUserUseCase(userRepo: UserRepo): UserUseCase = UserUseCase(userRepo)

    @Provides
    fun provideReadingViewUseCase(readingViewRepo: ReadingViewRepo): ReadingVIewUseCase =
        ReadingVIewUseCase(readingViewRepo)
}