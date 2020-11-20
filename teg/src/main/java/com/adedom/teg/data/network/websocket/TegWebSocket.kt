package com.adedom.teg.data.network.websocket

import com.adedom.teg.models.response.RoomsResponse
import com.adedom.teg.models.websocket.*
import com.adedom.teg.sharedpreference.service.SessionManagerService
import com.adedom.teg.util.TegConstant
import com.adedom.teg.util.fromJson
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.util.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onEach

typealias SinglePeopleAllSocket = (PeopleAllOutgoing) -> Unit
typealias SingleItemSocket = (SingleItemOutgoing) -> Unit
typealias SingleSuccessAnnouncementSocket = (SingleSuccessAnnouncementOutgoing) -> Unit
typealias RoomPeopleAllSocket = (PeopleAllOutgoing) -> Unit
typealias PlaygroundRoomSocket = (RoomsResponse) -> Unit
typealias RoomInfoTitleSocket = (RoomInfoTitleOutgoing) -> Unit
typealias RoomInfoPlayersSocket = (RoomInfoPlayersOutgoing) -> Unit
typealias RoomInfoTegMultiSocket = (RoomInfoTegMultiOutgoing) -> Unit
typealias WebSockets<T> = (T) -> Unit

@KtorExperimentalAPI
class TegWebSocket(
    private val client: HttpClient,
    private val sessionManagerService: SessionManagerService,
) {

    private var singleItem: WebSocketSession? = null
    private var singleSuccessAnnouncement: WebSocketSession? = null
    private var playgroundRoom: WebSocketSession? = null
    private var roomInfoPlayers: WebSocketSession? = null
    private var roomInfoTegMulti: WebSocketSession? = null

    private suspend fun wss(
        path: String,
        webSockets: suspend DefaultClientWebSocketSession.() -> Unit
    ) {
        client.wss(
            method = HttpMethod.Get,
            host = TegConstant.BASE_HOST,
            port = DEFAULT_PORT,
            path = path,
            request = {
                header(TegConstant.ACCESS_TOKEN, sessionManagerService.accessToken)
            }
        ) {
            webSockets.invoke(this)
        }
    }

    private suspend inline fun <reified T : Any> wss(
        path: String,
        crossinline socket: WebSockets<T>
    ) {
        wss(path) {
            incoming.consumeAsFlow()
                .onEach { frame ->
                    val response = frame.fromJson<T>()
                    socket.invoke(response)
                }
                .catch { }
                .collect()
        }
    }

    suspend fun incomingSinglePeopleAll(socket: SinglePeopleAllSocket) {
        wss<PeopleAllOutgoing>("/websocket/single/single-people-all") {
            socket.invoke(it)
        }
    }

    suspend fun incomingSingleItem(socket: SingleItemSocket) {
        wss("/websocket/single/single-item") {
            singleItem = this
            try {
                incoming.consumeAsFlow()
                    .onEach { frame ->
                        val response = frame.fromJson<SingleItemOutgoing>()
                        socket.invoke(response)
                    }
                    .catch { }
                    .collect()
            } finally {
                singleItem = null
            }
        }
    }

    suspend fun incomingSingleSuccessAnnouncement(socket: SingleSuccessAnnouncementSocket) {
        wss("/websocket/single/single-success-announcement") {
            singleSuccessAnnouncement = this
            try {
                incoming.consumeAsFlow()
                    .onEach { frame ->
                        val response = frame.fromJson<SingleSuccessAnnouncementOutgoing>()
                        socket.invoke(response)
                    }
                    .catch { }
                    .collect()
            } finally {
                singleSuccessAnnouncement = null
            }
        }
    }

    suspend fun incomingRoomPeopleAll(socket: RoomPeopleAllSocket) {
        wss<PeopleAllOutgoing>("/websocket/multi/room-people-all") {
            socket.invoke(it)
        }
    }

    suspend fun incomingPlaygroundRoom(socket: PlaygroundRoomSocket) {
        wss("/websocket/multi/playground-room") {
            playgroundRoom = this
            try {
                incoming.consumeAsFlow()
                    .onEach { frame ->
                        val response = frame.fromJson<RoomsResponse>()
                        socket.invoke(response)
                    }
                    .catch { }
                    .collect()
            } finally {
                playgroundRoom = null
            }
        }
    }

    suspend fun incomingRoomInfoTitle(socket: RoomInfoTitleSocket) {
        wss("/websocket/multi/room-info-title") {
            incoming.consumeAsFlow()
                .onEach { frame ->
                    val response = frame.fromJson<RoomInfoTitleOutgoing>()
                    socket.invoke(response)
                }
                .catch { }
                .collect()
        }
    }

    suspend fun incomingRoomInfoPlayers(socket: RoomInfoPlayersSocket) {
        wss("/websocket/multi/room-info-players") {
            roomInfoPlayers = this
            try {
                incoming.consumeAsFlow()
                    .onEach { frame ->
                        val response = frame.fromJson<RoomInfoPlayersOutgoing>()
                        socket.invoke(response)
                    }
                    .catch { }
                    .collect()
            } finally {
                roomInfoPlayers = null
            }
        }
    }

    suspend fun incomingRoomInfoTegMulti(socket: RoomInfoTegMultiSocket) {
        wss("/websocket/multi/room-info-teg-multi") {
            roomInfoTegMulti = this
            try {
                incoming.consumeAsFlow()
                    .onEach { frame ->
                        val response = frame.fromJson<RoomInfoTegMultiOutgoing>()
                        socket.invoke(response)
                    }
                    .catch { }
                    .collect()
            } finally {
                roomInfoTegMulti = null
            }
        }
    }

    suspend fun outgoingSingleItem() {
        singleItem?.outgoing?.send(Frame.Text(""))
    }

    suspend fun outgoingSingleSuccessAnnouncement() {
        singleSuccessAnnouncement?.outgoing?.send(Frame.Text(""))
    }

    suspend fun outgoingPlaygroundRoom() {
        playgroundRoom?.outgoing?.send(Frame.Text(""))
    }

    suspend fun outgoingRoomInfoPlayers() {
        roomInfoPlayers?.outgoing?.send(Frame.Text(""))
    }

    suspend fun outgoingRoomInfoTegMulti() {
        roomInfoTegMulti?.outgoing?.send(Frame.Text(""))
    }

}
