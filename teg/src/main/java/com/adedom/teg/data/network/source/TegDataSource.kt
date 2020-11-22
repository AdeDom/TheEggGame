package com.adedom.teg.data.network.source

import androidx.lifecycle.LiveData
import com.adedom.teg.data.db.entities.BackpackEntity
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.data.network.websocket.*
import com.adedom.teg.models.request.*
import com.adedom.teg.models.response.*
import okhttp3.MultipartBody

interface TegDataSource {

    suspend fun savePlayerInfo(playerInfo: PlayerInfoEntity)

    suspend fun getDbPlayerInfo(): PlayerInfoEntity?

    fun getDbPlayerInfoLiveData(): LiveData<PlayerInfoEntity>

    suspend fun deletePlayerInfo()

    suspend fun saveBackpack(backpack: BackpackEntity)

    suspend fun getDbBackpack(): BackpackEntity?

    fun getDbBackpackLiveData(): LiveData<BackpackEntity>

    suspend fun deleteBackpack()

    suspend fun callSignIn(signIn: SignInRequest): SignInResponse

    suspend fun callSignUp(signUp: SignUpRequest): SignInResponse

    suspend fun callRefreshToken(refreshToken: RefreshTokenRequest): SignInResponse

    suspend fun callFetchPlayerInfo(): PlayerInfoResponse

    suspend fun callChangeImageProfile(imageFile: MultipartBody.Part): BaseResponse

    suspend fun callPlayerState(state: String): BaseResponse

    suspend fun callChangePassword(changePassword: ChangePasswordRequest): BaseResponse

    suspend fun callChangeProfile(changeProfile: ChangeProfileRequest): BaseResponse

    suspend fun callChangeLatLng(changeLatLngRequest: ChangeLatLngRequest): BaseResponse

    suspend fun callFetchRankPlayers(search: String, limit: Int): RankPlayersResponse

    suspend fun callLogActiveOn(): BaseResponse

    suspend fun callLogActiveOff(): BaseResponse

    suspend fun callFetchMission(): MissionResponse

    suspend fun callMissionMain(missionRequest: MissionRequest): BaseResponse

    suspend fun callChangeCurrentMode(mode: String): BaseResponse

    suspend fun callFetchItemCollection(): BackpackResponse

    suspend fun callSingleItemCollection(singleId: Int?): BaseResponse

    suspend fun callMultiItemCollection(multiItemCollectionRequest: MultiItemCollectionRequest): BaseResponse

    suspend fun callCreateRoom(createRoomRequest: CreateRoomRequest): BaseResponse

    suspend fun callCurrentRoomNo(): CurrentRoomNoResponse

    suspend fun callJoinRoomInfo(joinRoomInfoRequest: JoinRoomInfoRequest): BaseResponse

    suspend fun callLeaveRoomInfo(): BaseResponse

    suspend fun callChangeTeam(team: String): BaseResponse

    suspend fun callChangeStatusRoomInfo(): BaseResponse

    suspend fun callRoomInfoTegMulti(): BaseResponse

    suspend fun callChangeStatusUnready(): BaseResponse

    suspend fun callFetchMultiPlayer(): FetchMultiPlayerResponse

    suspend fun incomingSinglePeopleAll(socket: SinglePeopleAllSocket)

    suspend fun incomingSingleItem(socket: SingleItemSocket)

    suspend fun incomingSingleSuccessAnnouncement(socket: SingleSuccessAnnouncementSocket)

    suspend fun incomingPlaygroundSinglePlayer(socket: PlaygroundSinglePlayerSocket)

    suspend fun incomingRoomPeopleAll(socket: RoomPeopleAllSocket)

    suspend fun incomingPlaygroundRoom(socket: PlaygroundRoomSocket)

    suspend fun incomingRoomInfoTitle(socket: RoomInfoTitleSocket)

    suspend fun incomingRoomInfoPlayers(socket: RoomInfoPlayersSocket)

    suspend fun incomingRoomInfoTegMulti(socket: RoomInfoTegMultiSocket)

    suspend fun outgoingSingleItem()

    suspend fun outgoingSingleSuccessAnnouncement()

    suspend fun outgoingPlaygroundRoom()

    suspend fun outgoingRoomInfoPlayers()

    suspend fun outgoingRoomInfoTegMulti()

}
