package com.muhmmad.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muhmmad.domain.model.Card

@Database(entities = [Card::class], version = 1)
abstract class CardsDatabase : RoomDatabase() {
    abstract fun cardsDao(): PaymentCardDao
}