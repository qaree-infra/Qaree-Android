package com.muhmmad.data.local

import androidx.datastore.core.DataStore
import com.muhmmad.domain.local.LocalDataSource
import com.muhmmad.domain.model.AppMode
import com.muhmmad.domain.model.Card
import com.muhmmad.domain.model.Language
import com.muhmmad.domain.model.User
import com.muhmmad.domain.model.UserData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class LocalDataSourceImpl(
    private val dataStore: DataStore<UserData>,
    private val cardsDataBase: CardsDatabase
) : LocalDataSource {
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
    override suspend fun changeLanguage(lang: Language) {
        dataStore.updateData {
            it.copy(
                language = lang
            )
        }
    }

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

    override suspend fun getPaymentCards(): List<Card> = cardsDataBase.cardsDao().getPaymentCards()
    override suspend fun deletePaymentCard(card: Card) =
        cardsDataBase.cardsDao().deletePaymentCard(card)

    override suspend fun addPaymentCard(card: Card) = cardsDataBase.cardsDao().addPaymentCard(card)
}