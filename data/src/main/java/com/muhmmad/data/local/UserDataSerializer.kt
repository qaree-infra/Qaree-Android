package com.muhmmad.data.local

import androidx.datastore.core.Serializer
import com.muhmmad.domain.model.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object UserDataSerializer : Serializer<UserData> {
    override val defaultValue: UserData
        get() = UserData()

    override suspend fun readFrom(input: InputStream): UserData {
        return try {
            Json.decodeFromString(
                UserData.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (ex: SerializationException) {
            ex.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserData, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(
                    serializer = UserData.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        }
    }
}