package com.muhmmad.qaree.ui.fragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.muhmmad.domain.model.Chat
import com.muhmmad.domain.model.Message
import com.muhmmad.domain.model.Room
import com.muhmmad.domain.usecase.AuthUseCase
import com.muhmmad.domain.usecase.CommunityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val communityUseCase: CommunityUseCase,
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(CommunityState())
    val state = _state.asStateFlow()
    private lateinit var mSocket: Socket

    val userId = MutableStateFlow("")
    val message = MutableSharedFlow<Message?>()
    val _room = MutableStateFlow<Room?>(null)
    val room = _room.asStateFlow()

    init {
        connectSocket()
    }

    fun setRoom(room: Room) {
        _room.update { room }
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
        mSocket.on(EVENT_MESSAGE_LIST, chatListener)
        mSocket.on(EVENT_SEND_MESSAGE, messageListener)
        mSocket.connect()
    }

    fun getRooms(keyword: String = "") = viewModelScope.launch {
        if (keyword.isNotEmpty()) mSocket.emit(EVENT_GET_ROOMS, JSONObject("{keyword:$keyword}"))
        else mSocket.emit(EVENT_GET_ROOMS, JSONObject())
    }

    fun getMessages(roomId: String, type: MessageType) {
        mSocket.emit(EVENT_MESSAGE_LIST, JSONObject("{room:$roomId,type:${type.name}}"))
    }

    fun sendMessage(message: String) {
        mSocket.emit(
            EVENT_SEND_MESSAGE,
            JSONObject("{content:\"$message\",to:${_room.value?._id}}")
        )
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
            Gson().fromJson(response.get("rooms").toString(), Array<Room>::class.java).asList()
        _state.update {
            it.copy(
                isLoading = false,
                rooms = data,
            )
        }
    }

    private val chatListener: Emitter.Listener = Emitter.Listener {
        val response = it[0] as JSONObject
        val id: String = response.get("userId").toString()
        val data = Gson().fromJson(response.get("messages").toString(), Chat::class.java)
        Log.i(TAG, data.toString())
        userId.update { id }
        _state.update {
            it.copy(
                isLoading = false,
                chat = data
            )
        }
    }

    private val messageListener: Emitter.Listener = Emitter.Listener {
        viewModelScope.launch {
            val response = it[0] as JSONObject
            Log.i(TAG, "MESSAGES : $response")
            val data = Gson().fromJson(response.toString(), Message::class.java)
            //  updateLastMessage(data)
            message.emit(data)
        }
    }

    private fun updateLastMessage(message: Message) {
        viewModelScope.launch {
            if (_state.value.rooms == null) return@launch
            try {
                val rooms = ArrayList<Room>()
                rooms.addAll(_state.value.rooms!!)
                val room = rooms.first {
                    it._id == message.room
                }

                room.lastMessage = message

                val position = rooms.withIndex().first { it.value._id == room._id }.index

                rooms[position] = room

                _state.update {
                    it.copy(
                        rooms = rooms.toList()
                    )
                }
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
            }
        }
    }

    data class CommunityState(
        val error: String? = null,
        val isLoading: Boolean = false,
        val rooms: List<Room>? = null,
        val chat: Chat? = null,
    )

    enum class MessageType {
        READ,
        UNREAD
    }
}

private const val TAG = "InboxViewModel"
private const val EVENT_GET_ROOMS = "get-rooms"
private const val EVENT_MESSAGE_LIST = "message-list"
private const val EVENT_SEND_MESSAGE = "message"