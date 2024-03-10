package com.muhmmad.domain.repo

import com.muhmmad.domain.model.ActivityResponse
import com.muhmmad.domain.model.AuthorsResponse
import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.model.BooksResponse
import com.muhmmad.domain.model.CategoriesResponse
import com.muhmmad.domain.model.LibraryResponse
import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.model.OffersResponse
import com.muhmmad.domain.model.ShelfResponse

interface HomeRepo {
    suspend fun getOffers(): NetworkResponse<OffersResponse>
    suspend fun getLastActivity(token: String): NetworkResponse<ActivityResponse>
    suspend fun getTopAuthors(): NetworkResponse<AuthorsResponse>
    suspend fun getNewReleaseBooks(): NetworkResponse<BooksResponse>
    suspend fun getBestSellerBooks(): NetworkResponse<BooksResponse>
    suspend fun getCategories(): NetworkResponse<CategoriesResponse>
    suspend fun getLibrary(token: String): NetworkResponse<LibraryResponse>
    suspend fun getShelfDetails(name: String, token: String): NetworkResponse<ShelfResponse>
    suspend fun createShelf(name: String, token: String): NetworkResponse<BaseResponse>
    suspend fun removeShelf(id: String, token: String): NetworkResponse<BaseResponse>
}