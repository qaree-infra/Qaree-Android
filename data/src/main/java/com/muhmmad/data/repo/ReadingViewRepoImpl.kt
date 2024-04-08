package com.muhmmad.data.repo

import com.muhmmad.data.utils.checkResponse
import com.muhmmad.domain.model.BookChapter
import com.muhmmad.domain.model.BookContent
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.remote.GraphQlDataSource
import com.muhmmad.domain.remote.RetrofitDataSource
import com.muhmmad.domain.repo.ReadingViewRepo

class ReadingViewRepoImpl(
    private val graphQlDataSource: GraphQlDataSource,
    private val retrofitDataSource: RetrofitDataSource
) : ReadingViewRepo {
    override suspend fun getBookContent(id: String): NetworkResponse<BookContent> =
        graphQlDataSource.getBookContent(id)

    override suspend fun getChapter(
        token: String,
        bookId: String,
        chapter: String
    ): NetworkResponse<BookChapter> =
        retrofitDataSource.getBookChapter(token, bookId, chapter).checkResponse()
}