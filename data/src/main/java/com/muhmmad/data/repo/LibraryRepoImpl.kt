package com.muhmmad.data.repo

import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.model.LibraryResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.model.ShelfResponse
import com.muhmmad.domain.remote.GraphQlDataSource
import com.muhmmad.domain.repo.LibraryRepo

class LibraryRepoImpl(private val graphQlDataSource: GraphQlDataSource) : LibraryRepo {
    override suspend fun createShelf(name: String, token: String): NetworkResponse<BaseResponse> =
        graphQlDataSource.createShelf(name, token)

    override suspend fun removeShelf(id: String, token: String): NetworkResponse<BaseResponse> =
        graphQlDataSource.removeShelf(id, token)

    override suspend fun getLibrary(token: String): NetworkResponse<LibraryResponse> =
        graphQlDataSource.getLibrary(token)

    override suspend fun getShelfDetails(
        name: String,
        token: String
    ): NetworkResponse<ShelfResponse> = graphQlDataSource.getShelfDetails(name, token)

    override suspend fun removeBookFromShelf(
        bookId: String,
        shelfId: String,
        token: String
    ): NetworkResponse<BaseResponse> = graphQlDataSource.removeBookFromShelf(bookId, shelfId, token)
}