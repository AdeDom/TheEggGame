package com.adedom.teg.data.network.websocket

import com.adedom.teg.models.response.RoomsResponse
import com.adedom.teg.models.websocket.CreateRoomIncoming
import com.adedom.teg.models.websocket.RoomPeopleAllOutgoing
import com.adedom.teg.util.fromJson
import com.adedom.teg.util.toJson
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.util.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onEach

typealias RoomPeopleAllSocket = (RoomPeopleAllOutgoing) -> Unit
typealias PlaygroundRoomSocket = (RoomsResponse) -> Unit

@KtorExperimentalAPI
class TegWebSocket(private val client: HttpClient) {

    private var roomSocket: WebSocketSession? = null

    suspend fun incomingRoomPeopleAll(socket: RoomPeopleAllSocket) {
        client.wss(
            method = HttpMethod.Get,
            host = "the-egg-game.herokuapp.com",
            port = DEFAULT_PORT,
            path = "/websocket/multi/room-people-all",
        ) {
            incoming.consumeAsFlow()
                .onEach { frame ->
                    val response = frame.fromJson<RoomPeopleAllOutgoing>()
                    socket.invoke(response)
                }
                .catch { }
                .collect()
        }
    }

    suspend fun incomingPlaygroundRoom(socket: PlaygroundRoomSocket) {
        client.wss(
            method = HttpMethod.Get,
            host = "the-egg-game.herokuapp.com",
            port = DEFAULT_PORT,
            path = "/websocket/multi/playground-room",
        ) {
            roomSocket = this
            try {
                incoming.consumeAsFlow()
                    .onEach { frame ->
                        val response = frame.fromJson<RoomsResponse>()
                        socket.invoke(response)
                    }
                    .catch { }
                    .collect()
            } finally {
                roomSocket = null
            }
        }
    }

    suspend fun outgoingCreateRoom(socket: CreateRoomIncoming) {
        roomSocket?.outgoing?.send(socket.toJson())
    }

}
