package com.muhmmad.data.local

import androidx.datastore.core.DataStore
import com.muhmmad.domain.local.LocalDataSource
import com.muhmmad.domain.model.AppMode
import com.muhmmad.domain.model.Language
import com.muhmmad.domain.model.User
import com.muhmmad.domain.model.UserData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class LocalDataSourceImpl(private val dataStore: DataStore<UserData>) : LocalDataSource {
    override suspend fun setFirstTime(isFirstTime: Boolean) {
        dataStore.updateData {
            it.copy(isFirstTime = isFirstTime)
        }
    }

    override suspend fun isFirstTime(): Boolean = dataStore.data.map { it.isFirstTime }.first()

    override suspend fun setToken(token: String) {
        dataStore.updateData {
            it.copy(
                token = token
            )
        }
    }

    override suspend fun getToken(): String = dataStore.data.map { it.token }.first()
    override suspend fun saveUserData(user: User) {
        dataStore.updateData {
            it.copy(
                id = user._id,
                name = user.name,
                email = user.email,
            )
        }
    }

    override suspend fun getLanguage(): Language = dataStore.data.map { it.language }.first()
    override suspend fun isUserProfile(userId: String): Boolean =
        dataStore.data.map { it.id }.first() == userId

    override suspend fun logout() {
        dataStore.updateData {
            it.copy(
                id = "",
                name = "",
                email = "",
                token = "",
            )
        }
    }

    override suspend fun getUserId(): String = dataStore.data.map { it.id }.first()
    override suspend fun changeMode(mode: AppMode) {
        dataStore.updateData {
            it.copy(
                uiMode = mode
            )
        }
    }

    override suspend fun getUiMode(): AppMode = dataStore.data.map { it.uiMode }.first()
    override suspend fun deleteUserData() {
        dataStore.updateData {
            it.copy(
                id = "",
                name = "",
                email = "",
                token = "",
            )
        }
    }
}