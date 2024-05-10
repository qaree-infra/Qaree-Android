package com.muhmmad.domain.usecase

import com.muhmmad.domain.repo.LibraryRepo

class LibraryUseCase(private val repo: LibraryRepo) {
    suspend fun getLibrary(userId: String? = null, token: String) = repo.getLibrary(userId, token)
    suspend fun getShelfDetails(name: String, token: String) = repo.getShelfDetails(name, token)
    suspend fun createShelf(name: String, token: String) = repo.createShelf(name, token)
    suspend fun removeShelf(id: String, token: String) = repo.removeShelf(id, token)
    suspend fun removeBookFromShelf(bookId: String, shelfId: String, token: String) =
        repo.removeBookFromShelf(bookId, shelfId, token)

    suspend fun addBookToShelf(token: String, shelfId: String, bookId: String) =
        repo.addBookToShelf(token, shelfId, bookId)
}