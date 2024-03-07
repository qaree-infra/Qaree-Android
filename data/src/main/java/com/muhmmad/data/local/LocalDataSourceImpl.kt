package com.muhmmad.data.local

import android.util.Log
import androidx.datastore.core.DataStore
import com.muhmmad.domain.local.LocalDataSource
import com.muhmmad.domain.model.Language
import com.muhmmad.domain.model.UserData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

private const val TAG = "LocalDataSourceImpl"

class LocalDataSourceImpl(private val dataStore: DataStore<UserData>) : LocalDataSource {
    override suspend fun setFirstTime(isFirstTime: Boolean) {
        dataStore.updateData {
            it.copy(isFirstTime = isFirstTime)
        }
    }

    override suspend fun isFirstTime(): Boolean = dataStore.data.map { it.isFirstTime }.first()

    override suspend fun setToken(token: String) {
        Log.i(TAG, token.toString())
        dataStore.updateData {
            it.copy(
                token = token
            )
        }
    }

    override suspend fun getToken(): String = dataStore.data.map { it.token }.first()

    override suspend fun getLanguage(): Language = dataStore.data.map { it.language }.first()
}