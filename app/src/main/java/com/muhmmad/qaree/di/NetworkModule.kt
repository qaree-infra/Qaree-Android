package com.muhmmad.qaree.di

import androidx.datastore.core.DataStore
import com.apollographql.apollo3.ApolloClient
import com.muhmmad.data.local.LocalDataSourceImpl
import com.muhmmad.data.remote.RemoteDataSourceImpl
import com.muhmmad.domain.local.LocalDataSource
import com.muhmmad.domain.model.UserData
import com.muhmmad.domain.remote.RemoteDataSource
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
    fun provideRemoteDataSource(apolloClient: ApolloClient): RemoteDataSource {
        return RemoteDataSourceImpl(apolloClient)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(dataStore: DataStore<UserData>): LocalDataSource =
        LocalDataSourceImpl(dataStore)
}