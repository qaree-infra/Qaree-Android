package com.muhmmad.domain.repo

import com.muhmmad.domain.model.BookChapter
import com.muhmmad.domain.model.BookContent
import com.muhmmad.domain.model.NetworkResponse

interface ReadingViewRepo {
    suspend fun getBookContent(id: String): NetworkResponse<BookContent>
    suspend fun getChapter(
        token: String,
        bookId: String,
        chapter: String
    ): NetworkResponse<BookChapter?>
}