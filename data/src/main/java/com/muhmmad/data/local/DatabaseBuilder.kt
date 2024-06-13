package com.muhmmad.data.local

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {
    private var INSTANCE: CardsDatabase? = null
    fun getInstance(context: Context): CardsDatabase {
        if (INSTANCE == null) {
            synchronized(CardsDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) = Room.databaseBuilder(
        context.applicationContext,
        CardsDatabase::class.java,
        "cards"
    ).build()
}