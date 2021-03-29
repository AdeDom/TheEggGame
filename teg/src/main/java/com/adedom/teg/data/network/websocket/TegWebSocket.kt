package com.adedom.teg.data.network.websocket

import com.adedom.teg.data.network.source.DataSourceProvider
import com.adedom.teg.models.TegLatLng
import com.adedom.teg.models.response.MultiItemResponse
import com.adedom.teg.models.response.RoomsResponse
import com.adedom.teg.models.response.ScoreResponse
import com.adedom.teg.models.websocket.*
import com.adedom.teg.sharedpreference.service.SessionManagerService
import com.adedom.teg.util.TegConstant
import com.adedom.teg.util.fromJson
import com.adedom.teg.util.toJson
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
typealias PlaygroundSinglePlayerSocket = suspend (PlaygroundSinglePlayerOutgoing) -> Unit
typealias RoomPeopleAllSocket = (PeopleAllOutgoing) -> Unit
typealias PlaygroundRoomSocket = (RoomsResponse) -> Unit
typealias RoomInfoTitleSocket = (RoomInfoTitleOutgoing) -> Unit
typealias RoomInfoPlayersSocket = (RoomInfoPlayersOutgoing) -> Unit
typealias RoomInfoTegMultiSocket = (String) -> Unit
typealias MultiPlayerItemsSocket = (MultiItemResponse) -> Unit
typealias MultiPlayerScoreSocket = (ScoreResponse) -> Unit
typealias MultiPlayerEndGameSocket = (String) -> Unit
typealias WebSockets<T> = (T) -> Unit

@KtorExperimentalAPI
class TegWebSocket(
    private val client: HttpClient,
    private val sessionManagerService: SessionManagerService,
) {

    private var singleItem: WebSocketSession? = null
    private var singleSuccessAnnouncement: WebSocketSession? = null
    private var playgroundSinglePlayer: WebSocketSession? = null
    private var playgroundRoom: WebSocketSession? = null
    private var roomInfoPlayers: WebSocketSession? = null
    private var roomInfoTegMulti: WebSocketSession? = null
    private var multiPlayerItems: WebSocketSession? = null
    private var multiPlayerScore: WebSocketSession? = null
    private var multiPlayerEndGame: WebSocketSession? = null

    private suspend fun wss(
        path: String,
        webSockets: suspend DefaultClientWebSocketSession.() -> Unit
    ) {
        client.ws(
            method = HttpMethod.Get,
            host = DataSourceProvider.HOST_NAME,
            port = 8080,
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

    suspend fun incomingPlaygroundSinglePlayer(socket: PlaygroundSinglePlayerSocket) {
        wss("/websocket/single/playground-single-player") {
            playgroundSinglePlayer = this
            try {
                incoming.consumeAsFlow()
                    .onEach { frame ->
                        val response = frame.fromJson<PlaygroundSinglePlayerOutgoing>()
                        socket.invoke(response)
                    }
                    .catch { }
                    .collect()
            } finally {
                playgroundSinglePlayer = null
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
                        val response = (frame as Frame.Text).readText()
                        socket.invoke(response)
                    }
                    .catch { }
                    .collect()
            } finally {
                roomInfoTegMulti = null
            }
        }
    }

    suspend fun incomingMultiPlayerItems(socket: MultiPlayerItemsSocket) {
        wss("/websocket/multi/multi-player-items") {
            multiPlayerItems = this
            try {
                incoming.consumeAsFlow()
                    .onEach { frame ->
                        val response = frame.fromJson<MultiItemResponse>()
                        socket.invoke(response)
                    }
                    .catch { }
                    .collect()
            } finally {
                multiPlayerItems = null
            }
        }
    }

    suspend fun incomingMultiPlayerScore(socket: MultiPlayerScoreSocket) {
        wss("/websocket/multi/multi-player-score") {
            multiPlayerScore = this
            try {
                incoming.consumeAsFlow()
                    .onEach { frame ->
                        val response = frame.fromJson<ScoreResponse>()
                        socket.invoke(response)
                    }
                    .catch { }
                    .collect()
            } finally {
                multiPlayerScore = null
            }
        }
    }

    suspend fun incomingMultiPlayerEndGame(socket: MultiPlayerEndGameSocket) {
        wss("/websocket/multi/multi-player-end-teg") {
            multiPlayerEndGame = this
            try {
                incoming.consumeAsFlow()
                    .onEach { frame ->
                        val response = (frame as Frame.Text).readText()
                        socket.invoke(response)
                    }
                    .catch { }
                    .collect()
            } finally {
                multiPlayerEndGame = null
            }
        }
    }

    suspend fun outgoingSingleItem() {
        singleItem?.outgoing?.send(Frame.Text(""))
    }

    suspend fun outgoingSingleSuccessAnnouncement() {
        singleSuccessAnnouncement?.outgoing?.send(Frame.Text(""))
    }

    suspend fun outgoingPlaygroundSinglePlayer(latLng: TegLatLng) {
        playgroundSinglePlayer?.outgoing?.send(latLng.toJson())
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

    suspend fun outgoingMultiPlayerItems() {
        multiPlayerItems?.outgoing?.send(Frame.Text(""))
    }

    suspend fun outgoingMultiPlayerScore() {
        multiPlayerScore?.outgoing?.send(Frame.Text(""))
    }

    suspend fun outgoingMultiPlayerEndGame() {
        multiPlayerEndGame?.outgoing?.send(Frame.Text(""))
    }

}
