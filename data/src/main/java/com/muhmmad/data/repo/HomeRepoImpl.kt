package com.muhmmad.data.repo

import com.muhmmad.domain.model.ActivityResponse
import com.muhmmad.domain.model.AuthorsResponse
import com.muhmmad.domain.model.BooksResponse
import com.muhmmad.domain.model.CategoriesResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.model.OffersResponse
import com.muhmmad.domain.remote.GraphQlDataSource
import com.muhmmad.domain.repo.HomeRepo

class HomeRepoImpl(
    private val graphQlDataSource: GraphQlDataSource,
) : HomeRepo {
    override suspend fun getOffers(): NetworkResponse<OffersResponse> =
        graphQlDataSource.getOffers()

    override suspend fun getLastActivity(token: String): NetworkResponse<ActivityResponse> =
        graphQlDataSource.getLastActivity(token)

    override suspend fun getTopAuthors(): NetworkResponse<AuthorsResponse> =
        graphQlDataSource.getTopAuthors()

    override suspend fun getNewReleaseBooks(): NetworkResponse<BooksResponse> =
        graphQlDataSource.getNewReleaseBooks()

    override suspend fun getBestSellerBooks(): NetworkResponse<BooksResponse> =
        graphQlDataSource.getBestSellerBooks()

    override suspend fun getCategories(): NetworkResponse<CategoriesResponse> =
        graphQlDataSource.getCategories()
}