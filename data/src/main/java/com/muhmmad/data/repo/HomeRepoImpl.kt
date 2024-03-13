package com.muhmmad.data.repo

import com.muhmmad.domain.model.ActivityResponse
import com.muhmmad.domain.model.AuthorsResponse
import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.model.BooksResponse
import com.muhmmad.domain.model.CategoriesResponse
import com.muhmmad.domain.model.LibraryResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.model.OffersResponse
import com.muhmmad.domain.model.ShelfResponse
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

    override suspend fun getBooksByCategory(categoryId: String): NetworkResponse<BooksResponse> =
        remoteDataSource.getBooksByCategory(categoryId)

    override suspend fun getLibrary(token: String): NetworkResponse<LibraryResponse> =
        remoteDataSource.getLibrary(token)

    override suspend fun getShelfDetails(
        name: String,
        token: String
    ): NetworkResponse<ShelfResponse> =
        remoteDataSource.getShelfDetails(name, token)

    override suspend fun removeBookFromShelf(
        bookId: String,
        shelfId: String,
        token: String
    ): NetworkResponse<BaseResponse> = remoteDataSource.removeBookFromShelf(bookId, shelfId, token)

    override suspend fun createShelf(name: String, token: String): NetworkResponse<BaseResponse> =
        remoteDataSource.createShelf(name, token)

    override suspend fun removeShelf(id: String, token: String): NetworkResponse<BaseResponse> =
        remoteDataSource.removeShelf(id, token)

    override suspend fun search(name: String): NetworkResponse<BooksResponse> =
        remoteDataSource.search(name)
}