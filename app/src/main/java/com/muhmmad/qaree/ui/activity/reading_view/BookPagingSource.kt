package com.muhmmad.qaree.ui.activity.reading_view

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.muhmmad.domain.model.BookChapter
import com.muhmmad.domain.model.ContentItem
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.usecase.ReadingVIewUseCase

class BookPagingSource(
    private val useCase: ReadingVIewUseCase,
    private val chaptersList: List<ContentItem>,
    private val bookId: String,
    private val token: String,
    private var position: Int = 0
) : PagingSource<String, NetworkResponse<BookChapter>>() {
    override fun getRefreshKey(state: PagingState<String, NetworkResponse<BookChapter>>): String? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey ?: anchorPage?.nextKey
        }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, NetworkResponse<BookChapter>> =
        try {
            // Start refresh at page 1 if undefined.
            val id: String = params.key ?: chaptersList[position].id

            val response: NetworkResponse<BookChapter> = useCase.getChapter(token, bookId, id)
            LoadResult.Page(
                data = listOf(response),
                prevKey = null, // Only paging forward.
                nextKey = chaptersList[++position].id
            )
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error for
            // expected errors (such as a network failure).
            LoadResult.Error(e)
        }
}