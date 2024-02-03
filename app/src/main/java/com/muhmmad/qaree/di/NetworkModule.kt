package com.muhmmad.qaree.di

import com.apollographql.apollo3.ApolloClient
import com.muhmmad.data.remote.ApolloAuthClient
import com.muhmmad.data.repo.AuthRepoImpl
import com.muhmmad.domain.remote.AuthClient
import com.muhmmad.domain.repo.AuthRepo
import com.muhmmad.domain.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://publishingcompany-backend.onrender.com/graphql")
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthClient(apolloClient: ApolloClient): AuthClient {
        return ApolloAuthClient(apolloClient)
    }
}