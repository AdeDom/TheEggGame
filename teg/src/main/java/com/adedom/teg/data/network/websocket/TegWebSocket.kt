package com.adedom.teg.data.network.websocket

import com.adedom.teg.models.websocket.RoomListSocket
import com.adedom.teg.util.fromJson
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onEach

typealias RoomSocket = (RoomListSocket) -> Unit

@KtorExperimentalAPI
class TegWebSocket {

    suspend fun incomingRoomPeopleAll(socket: RoomSocket) {
        val client = HttpClient(OkHttp) {
            install(WebSockets)
        }

        client.wss(
            method = HttpMethod.Get,
            host = "the-egg-game.herokuapp.com",
            port = DEFAULT_PORT,
            path = "/websocket/multi/room-people-all",
        ) {
            incoming.consumeAsFlow()
                .onEach { frame ->
                    val response = frame.fromJson<RoomListSocket>()
                    socket.invoke(response)
                }
                .catch { }
                .collect()
        }
    }

}
