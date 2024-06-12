package com.muhmmad.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Card(
    @PrimaryKey val id: Int=1,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "number") val number: String,
    @ColumnInfo(name = "cvv") val cvv: String,
    @ColumnInfo(name = "expire_month") val expireMonth: String,
    @ColumnInfo(name = "expire_year") val expireYear: String,
    @ColumnInfo(name = "image") val image: Int,
    var isChecked: Boolean = false
)
