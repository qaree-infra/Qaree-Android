package com.muhmmad.qaree.ui.fragment.inbox

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.muhmmad.domain.model.Chat
import com.muhmmad.domain.usecase.AuthUseCase
import com.muhmmad.domain.usecase.CommunityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class InboxViewModel @Inject constructor(
    private val communityUseCase: CommunityUseCase,
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(InboxState())
    val state = _state.asStateFlow()
    private lateinit var mSocket: Socket

    init {
        connectSocket()
    }

    private fun connectSocket() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        communityUseCase.connectSocket(authUseCase.getToken()).apply {
            communityUseCase.getSocket().apply {
                mSocket = this
                initSocket()
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun initSocket() {
        mSocket.on(Socket.EVENT_CONNECT, onConnect)
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect)
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
        mSocket.on(EVENT_GET_ROOMS, roomsListener)
        mSocket.connect()
    }

    private val onConnect: Emitter.Listener = Emitter.Listener {
        Log.i(TAG, "Connect")
        getRooms()
    }

    private val onDisconnect: Emitter.Listener = Emitter.Listener {
        Log.i(TAG, "DisConnect")
    }

    private val onConnectError: Emitter.Listener = Emitter.Listener { errors ->
        _state.update {
            it.copy(
                isLoading = false, error = errors[0].toString()
            )
        }
    }

    private val roomsListener: Emitter.Listener = Emitter.Listener {
        val response = it[0] as JSONObject
        Log.i(TAG, response.toString())
        val data =
            Gson().fromJson(response.get("rooms").toString(), Array<Chat>::class.java).asList()
        _state.update {
            it.copy(
                isLoading = false,
                chats = data,
            )
        }
    }

    fun getRooms(keyword: String = "") = viewModelScope.launch {
        if (keyword.isNotEmpty()) mSocket.emit(EVENT_GET_ROOMS, JSONObject("{keyword:$keyword}"))
        else mSocket.emit(EVENT_GET_ROOMS, JSONObject())
    }

    data class InboxState(
        val error: String? = null,
        val isLoading: Boolean = false,
        val chats: List<Chat>? = null
    )
}

private const val TAG = "InboxViewModel"
private const val EVENT_GET_ROOMS = "get-rooms"