package com.muhmmad.qaree.ui.fragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.model.Chat
import com.muhmmad.domain.model.Message
import com.muhmmad.domain.model.Room
import com.muhmmad.domain.usecase.AuthUseCase
import com.muhmmad.domain.usecase.CommunityUseCase
import com.muhmmad.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val communityUseCase: CommunityUseCase,
    private val authUseCase: AuthUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(CommunityState())
    val state = _state.asSharedFlow()
    private var mSocket: Socket? = null

    val userId = MutableStateFlow("")
    val message = MutableSharedFlow<Message?>()
    private val _room = MutableStateFlow<Room?>(null)
    val room = _room.asStateFlow()
    var messagesPage: Int = 1
    val rooms = MutableStateFlow<List<Room>?>(null)

    init {
        getUserId()
        connectSocket()
    }

    fun setRoom(room: Room) {
        if (_room.value == room) return
        messagesPage = 1
        _room.update { room }
    }

    private fun connectSocket() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        communityUseCase.connectSocket(authUseCase.getToken()).apply {
            communityUseCase.getSocket().apply {
                mSocket = this
                initSocket()
            }
        }
    }

    private fun initSocket() {
        mSocket?.on(Socket.EVENT_CONNECT, onConnect)
        mSocket?.on(Socket.EVENT_DISCONNECT, onDisconnect)
        mSocket?.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
        mSocket?.on(EVENT_GET_ROOMS, roomsListener)
        mSocket?.on(EVENT_MESSAGE_LIST, chatListener)
        mSocket?.on(EVENT_SEND_MESSAGE, messageListener)
        mSocket?.connect()
        _state.update { it.copy(isLoading = false) }
    }

    fun getRooms(keyword: String = "") = viewModelScope.launch {
        if (keyword.isNotEmpty()) mSocket?.emit(EVENT_GET_ROOMS, JSONObject("{keyword:$keyword}"))
        else mSocket?.emit(EVENT_GET_ROOMS, JSONObject())
    }

    fun getMessages(roomId: String, type: MessageType = MessageType.UNREAD, page: Int = 1) {
        mSocket?.emit(
            EVENT_MESSAGE_LIST,
            JSONObject("{room:$roomId,limit:20,page:$page,type:${type.name}}")
        )
    }

    fun sendMessage(message: String) {
        var roomId = _room.value?._id
        if (_room.value?.lastMessage?.content.isNullOrEmpty()) {
            roomId = "user-${_room.value?.partner?._id}"
        }
        mSocket?.emit(
            EVENT_SEND_MESSAGE,
            JSONObject("{content:\"$message\",to:$roomId}")
        )
    }

    fun deleteChat() = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        communityUseCase.deleteChat(room.value?.roomId.toString(), authUseCase.getToken()).apply {
            _state.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }

    private val onConnect: Emitter.Listener = Emitter.Listener {
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
        rooms.update {
            data
        }
        _state.update {
            it.copy(
                isLoading = false,
            )
        }
    }

    private val chatListener: Emitter.Listener = Emitter.Listener {
        val response = it[0] as JSONObject
        val data = Gson().fromJson(response.get("messages").toString(), Chat::class.java)
        Log.i(TAG, "total pages : ${data.numberOfPages}")
        Log.i(TAG, "current page : ${data.currentPage}")
        if (data.currentPage < data.numberOfPages) messagesPage = data.currentPage + 1
        else messagesPage = -1

        Log.i(TAG, "MesssagePages : $messagesPage")

        if (data.messages.isNotEmpty()) {
            _room.update {
                it?.copy(
                    _id = data.messages[0].room
                )
            }
        }
        Log.i(TAG, data.toString())
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

    fun clearChat() = viewModelScope.launch {
        _state.update {
            it.copy(
                chat = null
            )
        }
    }

    private fun updateLastMessage(message: Message) {
        viewModelScope.launch {
            if (rooms.value == null) return@launch
            try {
                val r = ArrayList<Room>()
                r.addAll(rooms.value!!)
                val room = r.first {
                    it._id == message.room
                }

                room.lastMessage = message

                val position = r.withIndex().first { it.value._id == room._id }.index

                r[position] = room

                rooms.update {
                    r.toList()
                }
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
            }
        }
    }

    private fun getUserId() = viewModelScope.launch {
        userUseCase.getUserId().apply {
            userId.update { this }
        }
    }

    data class CommunityState(
        val error: String? = null,
        val isLoading: Boolean = false,
        val chat: Chat? = null,
        val deleteChatResponse: BaseResponse? = null
    )

    enum class MessageType {
        READ,
        UNREAD
    }
}

private const val TAG = "InboxViewModel"
private const val EVENT_GET_ROOMS = "get-rooms"
private const val EVENT_SEND_MESSAGE = "message"
private const val EVENT_MESSAGE_LIST = "message-list"