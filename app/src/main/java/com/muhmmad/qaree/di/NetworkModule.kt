package com.muhmmad.qaree.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.apollographql.apollo3.ApolloClient
import com.muhmmad.data.local.DatabaseBuilder
import com.muhmmad.data.local.LocalDataSourceImpl
import com.muhmmad.data.remote.GraphQlDataSourceImpl
import com.muhmmad.data.remote.RetrofitClient
import com.muhmmad.domain.local.LocalDataSource
import com.muhmmad.domain.model.UserData
import com.muhmmad.domain.remote.GraphQlDataSource
import com.muhmmad.domain.remote.RetrofitDataSource
import com.muhmmad.qaree.utils.Constants.GRAPHQL_BASEURL
import com.muhmmad.qaree.utils.Constants.RETROFIT_BASEURL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(GRAPHQL_BASEURL)
            .build()
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(apolloClient: ApolloClient): GraphQlDataSource {
        return GraphQlDataSourceImpl(apolloClient)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(
        dataStore: DataStore<UserData>,
        @ApplicationContext context: Context
    ): LocalDataSource =
        LocalDataSourceImpl(dataStore, DatabaseBuilder.getInstance(context))

    @Provides
    @Singleton
    fun provideRetrofitDataSource(): RetrofitDataSource = RetrofitClient.retrofitDataSource(
        RETROFIT_BASEURL
    )
}