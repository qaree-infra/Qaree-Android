package com.muhmmad.data.remote

import io.socket.client.IO
import io.socket.client.Socket

object SocketHandler {
    private lateinit var mSocket: Socket

    @Synchronized
    fun setSocket() {
        mSocket = IO.socket("https://publishingcompany-backend.onrender.com")
    }

    @Synchronized
    fun getSocket(): Socket {
        return mSocket
    }

    @Synchronized
    fun establishConnection() {
        mSocket.connect()
    }

    @Synchronized
    fun closeConnection() {
        mSocket.disconnect()
    }
}