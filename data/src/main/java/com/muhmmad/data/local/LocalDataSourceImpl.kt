package com.muhmmad.data.local

import androidx.datastore.core.DataStore
import com.muhmmad.domain.local.LocalDataSource
import com.muhmmad.domain.model.Language
import com.muhmmad.domain.model.UserData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class LocalDataSourceImpl(private val dataStore: DataStore<UserData>) : LocalDataSource {
    override suspend fun setToken(token: String) {
        dataStore.updateData {
            it.copy(
                token = token
            )
        }
    }

    override suspend fun getToken(): String = dataStore.data.map { it.token }.first()

    override suspend fun getLanguage(): Language {
        TODO("Not yet implemented")
    }
}