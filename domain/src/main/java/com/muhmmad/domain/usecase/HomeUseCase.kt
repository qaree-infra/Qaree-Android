package com.muhmmad.domain.usecase

import com.muhmmad.domain.repo.HomeRepo

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
}