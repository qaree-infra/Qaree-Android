package com.muhmmad.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.muhmmad.domain.model.Card

@Dao
interface PaymentCardDao {
    @Query("SELECT * FROM card")
    suspend fun getPaymentCards(): List<Card>

    @Insert
    suspend fun addPaymentCard(card: Card)

    @Delete
    suspend fun deletePaymentCard(card: Card)
}