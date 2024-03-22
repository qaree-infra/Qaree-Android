package com.muhmmad.data.repo

import com.muhmmad.data.utils.checkResponse
import com.muhmmad.domain.model.ActivityResponse
import com.muhmmad.domain.model.AuthorsResponse
import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.model.BooksResponse
import com.muhmmad.domain.model.CategoriesResponse
import com.muhmmad.domain.model.LibraryResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.model.OffersResponse
import com.muhmmad.domain.model.ReviewsResponse
import com.muhmmad.domain.model.ShelfResponse
import com.muhmmad.domain.model.User
import com.muhmmad.domain.remote.GraphQlDataSource
import com.muhmmad.domain.remote.RetrofitDataSource
import com.muhmmad.domain.repo.HomeRepo
import okhttp3.MultipartBody

class HomeRepoImpl(
    private val graphQlDataSource: GraphQlDataSource,
    private val retrofitDataSource: RetrofitDataSource
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

    override suspend fun getBooksByCategory(categoryId: String): NetworkResponse<BooksResponse> =
        graphQlDataSource.getBooksByCategory(categoryId)

    override suspend fun getLibrary(token: String): NetworkResponse<LibraryResponse> =
        graphQlDataSource.getLibrary(token)

    override suspend fun getShelfDetails(
        name: String,
        token: String
    ): NetworkResponse<ShelfResponse> =
        graphQlDataSource.getShelfDetails(name, token)

    override suspend fun removeBookFromShelf(
        bookId: String,
        shelfId: String,
        token: String
    ): NetworkResponse<BaseResponse> = graphQlDataSource.removeBookFromShelf(bookId, shelfId, token)

    override suspend fun createShelf(name: String, token: String): NetworkResponse<BaseResponse> =
        graphQlDataSource.createShelf(name, token)

    override suspend fun removeShelf(id: String, token: String): NetworkResponse<BaseResponse> =
        graphQlDataSource.removeShelf(id, token)

    override suspend fun search(name: String): NetworkResponse<BooksResponse> =
        graphQlDataSource.search(name)

    override suspend fun getBookReviews(id: String): NetworkResponse<ReviewsResponse> =
        graphQlDataSource.getBookReviews(id)

    override suspend fun makeReview(
        token: String,
        bookId: String,
        rate: Float,
        content: String
    ): NetworkResponse<BaseResponse> = graphQlDataSource.makeReview(token, bookId, rate, content)

    override suspend fun getUserInfo(token: String): NetworkResponse<User> =
        graphQlDataSource.getUserInfo(token)

    override suspend fun uploadUserAvatar(
        token: String,
        image: MultipartBody.Part
    ): NetworkResponse<Any?> {
        return retrofitDataSource.uploadUserAvatar(token, image).checkResponse()
    }

    override suspend fun updateUserName(token: String, name: String): NetworkResponse<User> =
        graphQlDataSource.updateUserName(token, name)

    override suspend fun updateUserBio(token: String, bio: String): NetworkResponse<User> =
        graphQlDataSource.updateUserBio(token, bio)
}