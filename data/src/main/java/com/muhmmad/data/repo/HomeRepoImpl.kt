package com.muhmmad.data.repo

import com.muhmmad.domain.model.ActivityResponse
import com.muhmmad.domain.model.AuthorsResponse
import com.muhmmad.domain.model.BooksResponse
import com.muhmmad.domain.model.CategoriesResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.model.OffersResponse
import com.muhmmad.domain.remote.RemoteDataSource
import com.muhmmad.domain.repo.HomeRepo

class HomeRepoImpl(private val remoteDataSource: RemoteDataSource) : HomeRepo {
    override suspend fun getOffers(): NetworkResponse<OffersResponse> = remoteDataSource.getOffers()
    override suspend fun getLastActivity(token: String): NetworkResponse<ActivityResponse> =
        remoteDataSource.getLastActivity(token)

    override suspend fun getTopAuthors(): NetworkResponse<AuthorsResponse> =
        remoteDataSource.getTopAuthors()

    override suspend fun getNewReleaseBooks(): NetworkResponse<BooksResponse> =
        remoteDataSource.getNewReleaseBooks()

    override suspend fun getBestSellerBooks(): NetworkResponse<BooksResponse> =
        remoteDataSource.getBestSellerBooks()

    override suspend fun getCategories(): NetworkResponse<CategoriesResponse> =
        remoteDataSource.getCategories()
}