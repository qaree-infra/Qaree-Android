package com.muhmmad.domain.usecase

import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.model.User
import com.muhmmad.domain.repo.HomeRepo
import okhttp3.MultipartBody

class HomeUseCase(private val repo: HomeRepo) {
    suspend fun getOffers() = repo.getOffers()
    suspend fun getLastActivity(token: String) = repo.getLastActivity(token)
    suspend fun getTopAuthors() = repo.getTopAuthors()
    suspend fun getNewReleaseBooks() = repo.getNewReleaseBooks()
    suspend fun getBestSellerBooks() = repo.getBestSellerBooks()
    suspend fun getCategories() = repo.getCategories()
    suspend fun getBooksByCategory(categoryId: String) = repo.getBooksByCategory(categoryId)
    suspend fun getLibrary(token: String) = repo.getLibrary(token)
    suspend fun getShelfDetails(name: String, token: String) = repo.getShelfDetails(name, token)
    suspend fun removeBookFromShelf(bookId: String, shelfId: String, token: String) =
        repo.removeBookFromShelf(bookId, shelfId, token)

    suspend fun createShelf(name: String, token: String) = repo.createShelf(name, token)
    suspend fun removeShelf(id: String, token: String) = repo.removeShelf(id, token)
    suspend fun search(name: String) = repo.search(name)
    suspend fun getBookReviews(id: String) = repo.getBookReviews(id)
    suspend fun makeReview(token: String, bookId: String, rate: Float, content: String) =
        repo.makeReview(token, bookId, rate, content)

    suspend fun getUserInfo(token: String) = repo.getUserInfo(token)
    suspend fun uploadUserAvatar(token: String, image: MultipartBody.Part) =
        repo.uploadUserAvatar(token, image)

    suspend fun updateUserName(token: String, name: String) = repo.updateUserName(token, name)
    suspend fun updateUserBio(token: String, bio: String) = repo.updateUserBio(token, bio)
    suspend fun updatePassword(
        token: String,
        oldPassword: String,
        newPassword: String
    ) = repo.updatePassword(token, oldPassword, newPassword)
}