package com.muhmmad.domain.repo

import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.model.LibraryResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.model.ShelfResponse

interface LibraryRepo {
    suspend fun createShelf(name: String, token: String): NetworkResponse<BaseResponse>
    suspend fun removeShelf(id: String, token: String): NetworkResponse<BaseResponse>
    suspend fun getLibrary(userId: String?, token: String): NetworkResponse<LibraryResponse>
    suspend fun getShelfDetails(name: String, token: String): NetworkResponse<ShelfResponse>
    suspend fun removeBookFromShelf(
        bookId: String,
        shelfId: String,
        token: String
    ): NetworkResponse<BaseResponse>

    suspend fun addBookToShelf(
        token: String,
        shelfId: String,
        bookId: String
    ): NetworkResponse<BaseResponse>
}