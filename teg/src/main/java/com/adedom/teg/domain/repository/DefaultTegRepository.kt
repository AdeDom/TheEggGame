package com.adedom.teg.domain.repository

import androidx.lifecycle.LiveData
import com.adedom.teg.data.db.entities.BackpackEntity
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.data.network.websocket.*
import com.adedom.teg.domain.Resource
import com.adedom.teg.models.TegLatLng
import com.adedom.teg.models.request.*
import com.adedom.teg.models.response.*
import okhttp3.MultipartBody

interface DefaultTegRepository {

    suspend fun savePlayerInfo(playerInfo: PlayerInfoEntity)

    suspend fun getDbPlayerInfo(): PlayerInfoEntity?

    fun getDbPlayerInfoLiveData(): LiveData<PlayerInfoEntity>

    suspend fun deletePlayerInfo()

    suspend fun saveBackpack(backpack: BackpackEntity)

    suspend fun getDbBackpack(): BackpackEntity?

    fun getDbBackpackLiveData(): LiveData<BackpackEntity>

    suspend fun deleteBackpack()

    suspend fun callSignIn(signIn: SignInRequest): Resource<SignInResponse>

    suspend fun callSignUp(signUp: SignUpRequest): Resource<SignInResponse>

    suspend fun callRefreshToken(refreshToken: RefreshTokenRequest): Resource<SignInResponse>

    suspend fun callFetchPlayerInfo(): Resource<PlayerInfoResponse>

    suspend fun callChangeImageProfile(imageFile: MultipartBody.Part): Resource<BaseResponse>

    suspend fun callPlayerState(state: String): Resource<BaseResponse>

    suspend fun callChangePassword(changePassword: ChangePasswordRequest): Resource<BaseResponse>

    suspend fun callChangeProfile(changeProfile: ChangeProfileRequest): Resource<BaseResponse>

    suspend fun callChangeLatLng(changeLatLngRequest: ChangeLatLngRequest): Resource<BaseResponse>

    suspend fun callFetchRankPlayers(search: String, limit: Int): Resource<RankPlayersResponse>

    suspend fun callLogActiveOn(): Resource<BaseResponse>

    suspend fun callLogActiveOff(): Resource<BaseResponse>

    suspend fun callFetchMission(): Resource<MissionResponse>

    suspend fun callMissionMain(missionRequest: MissionRequest): Resource<BaseResponse>

    suspend fun callChangeCurrentMode(mode: String): Resource<BaseResponse>

    suspend fun callFetchItemCollection(): Resource<BackpackResponse>

    suspend fun callSingleItemCollection(singleId: Int?): Resource<BaseResponse>

    suspend fun callMultiItemCollection(multiItemCollectionRequest: MultiItemCollectionRequest): Resource<BaseResponse>

    suspend fun callCreateRoom(createRoomRequest: CreateRoomRequest): Resource<BaseResponse>

    suspend fun callCurrentRoomNo(): Resource<CurrentRoomNoResponse>

    suspend fun callJoinRoomInfo(joinRoomInfoRequest: JoinRoomInfoRequest): Resource<BaseResponse>

    suspend fun callLeaveRoomInfo(): Resource<BaseResponse>

    suspend fun callChangeTeam(team: String): Resource<BaseResponse>

    suspend fun callChangeStatusRoomInfo(): Resource<BaseResponse>

    suspend fun callRoomInfoTegMulti(): Resource<BaseResponse>

    suspend fun callChangeStatusUnready(): Resource<BaseResponse>

    suspend fun callFetchMultiPlayer(): Resource<FetchMultiPlayerResponse>

    suspend fun callFetchMultiScore(): Resource<ScoreResponse>

    suspend fun callAddMultiScore(): Resource<BaseResponse>

    suspend fun callFetchMultiItem(): Resource<MultiItemResponse>

    suspend fun callAddMultiItem(): Resource<BaseResponse>

    suspend fun incomingSinglePeopleAll(socket: SinglePeopleAllSocket)

    suspend fun incomingSingleItem(socket: SingleItemSocket)

    suspend fun incomingSingleSuccessAnnouncement(socket: SingleSuccessAnnouncementSocket)

    suspend fun incomingPlaygroundSinglePlayer(socket: PlaygroundSinglePlayerSocket)

    suspend fun incomingRoomPeopleAll(socket: RoomPeopleAllSocket)

    suspend fun incomingPlaygroundRoom(socket: PlaygroundRoomSocket)

    suspend fun incomingRoomInfoTitle(socket: RoomInfoTitleSocket)

    suspend fun incomingRoomInfoPlayers(socket: RoomInfoPlayersSocket)

    suspend fun incomingRoomInfoTegMulti(socket: RoomInfoTegMultiSocket)

    suspend fun incomingMultiPlayerItems(socket: MultiPlayerItemsSocket)

    suspend fun outgoingSingleItem()

    suspend fun outgoingSingleSuccessAnnouncement()

    suspend fun outgoingPlaygroundSinglePlayer(latLng: TegLatLng)

    suspend fun outgoingPlaygroundRoom()

    suspend fun outgoingRoomInfoPlayers()

    suspend fun outgoingRoomInfoTegMulti()

    suspend fun outgoingMultiPlayerItems()

}
