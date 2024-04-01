package com.muhmmad.domain.usecase

import com.muhmmad.domain.repo.ReadingViewRepo

class ReadingVIewUseCase(private val repo: ReadingViewRepo) {
    suspend fun getBookContent(id: String) = repo.getBookContent(id)
    suspend fun getChapter(token: String, bookId: String, chapter: String) =
        repo.getChapter(token, bookId, chapter)
}