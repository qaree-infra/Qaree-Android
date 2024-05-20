package com.muhmmad.data.remote

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject

object SocketHandler {
    private lateinit var mSocket: Socket

    @Synchronized
    fun getSocket(): Socket = mSocket

    @Synchronized
    fun connectSocket(token: String) {
        val opts = IO.Options()
        opts.extraHeaders = mapOf("Authorization" to listOf(token))
        mSocket = IO.socket("https://publishingcompany-backend.onrender.com", opts)
    }

    @Synchronized
    fun closeConnection() {
        mSocket.disconnect()
    }
}

private const val TAG = "SocketHandler"