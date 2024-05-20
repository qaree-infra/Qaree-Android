package com.muhmmad.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class Cover(
    val id: String = "",
    val path: String = "",
    val size: Double = 0.0
) : Parcelable
