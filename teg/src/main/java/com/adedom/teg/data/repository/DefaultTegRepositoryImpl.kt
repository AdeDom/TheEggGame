package com.adedom.teg.data.repository

import androidx.lifecycle.LiveData
import com.adedom.teg.base.BaseRepository
import com.adedom.teg.data.db.entities.BackpackEntity
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.data.network.source.TegDataSource
import com.adedom.teg.data.network.websocket.*
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.TegLatLng
import com.adedom.teg.models.request.*
import com.adedom.teg.models.response.*
import okhttp3.MultipartBody

class DefaultTegRepositoryImpl(
    private val dataSource: TegDataSource
) : BaseRepository(), DefaultTegRepository {

    override suspend fun savePlayerInfo(playerInfo: PlayerInfoEntity) {
        return dataSource.savePlayerInfo(playerInfo)
    }

    override suspend fun getDbPlayerInfo(): PlayerInfoEntity? {
        return dataSource.getDbPlayerInfo()
    }

    override fun getDbPlayerInfoLiveData(): LiveData<PlayerInfoEntity> {
        return dataSource.getDbPlayerInfoLiveData()
    }

    override suspend fun deletePlayerInfo() {
        return dataSource.deletePlayerInfo()
    }

    override suspend fun saveBackpack(backpack: BackpackEntity) {
        return dataSource.saveBackpack(backpack)
    }

    override suspend fun getDbBackpack(): BackpackEntity? {
        return dataSource.getDbBackpack()
    }

    override fun getDbBackpackLiveData(): LiveData<BackpackEntity> {
        return dataSource.getDbBackpackLiveData()
    }

    override suspend fun deleteBackpack() {
        return dataSource.deleteBackpack()
    }

    override suspend fun callSignIn(signIn: SignInRequest): Resource<SignInResponse> {
        return safeApiCall { dataSource.callSignIn(signIn) }
    }

    override suspend fun callSignUp(signUp: SignUpRequest): Resource<SignInResponse> {
        return safeApiCall { dataSource.callSignUp(signUp) }
    }

    override suspend fun callRefreshToken(refreshToken: RefreshTokenRequest): Resource<SignInResponse> {
        return safeApiCall { dataSource.callRefreshToken(refreshToken) }
    }

    override suspend fun callFetchPlayerInfo(): Resource<PlayerInfoResponse> {
        return safeApiCall { dataSource.callFetchPlayerInfo() }
    }

    override suspend fun callChangeImageProfile(imageFile: MultipartBody.Part): Resource<BaseResponse> {
        return safeApiCall { dataSource.callChangeImageProfile(imageFile) }
    }

    override suspend fun callPlayerState(state: String): Resource<BaseResponse> {
        return safeApiCall { dataSource.callPlayerState(state) }
    }

    override suspend fun callChangePassword(changePassword: ChangePasswordRequest): Resource<BaseResponse> {
        return safeApiCall { dataSource.callChangePassword(changePassword) }
    }

    override suspend fun callChangeProfile(changeProfile: ChangeProfileRequest): Resource<BaseResponse> {
        return safeApiCall { dataSource.callChangeProfile(changeProfile) }
    }

    override suspend fun callChangeLatLng(changeLatLngRequest: ChangeLatLngRequest): Resource<BaseResponse> {
        return safeApiCall { dataSource.callChangeLatLng(changeLatLngRequest) }
    }

    override suspend fun callFetchRankPlayers(
        search: String,
        limit: Int
    ): Resource<RankPlayersResponse> {
        return safeApiCall { dataSource.callFetchRankPlayers(search, limit) }
    }

    override suspend fun callLogActiveOn(): Resource<BaseResponse> {
        return safeApiCall { dataSource.callLogActiveOn() }
    }

    override suspend fun callLogActiveOff(): Resource<BaseResponse> {
        return safeApiCall { dataSource.callLogActiveOff() }
    }

    override suspend fun callFetchMission(): Resource<MissionResponse> {
        return safeApiCall { dataSource.callFetchMission() }
    }

    override suspend fun callMissionMain(missionRequest: MissionRequest): Resource<BaseResponse> {
        return safeApiCall { dataSource.callMissionMain(missionRequest) }
    }

    override suspend fun callChangeCurrentMode(mode: String): Resource<BaseResponse> {
        return safeApiCall { dataSource.callChangeCurrentMode(mode) }
    }

    override suspend fun callFetchItemCollection(): Resource<BackpackResponse> {
        return safeApiCall { dataSource.callFetchItemCollection() }
    }

    override suspend fun callSingleItemCollection(singleId: Int?): Resource<BaseResponse> {
        return safeApiCall { dataSource.callSingleItemCollection(singleId) }
    }

    override suspend fun callMultiItemCollection(multiItemCollectionRequest: MultiItemCollectionRequest): Resource<BaseResponse> {
        return safeApiCall { dataSource.callMultiItemCollection(multiItemCollectionRequest) }
    }

    override suspend fun callCreateRoom(createRoomRequest: CreateRoomRequest): Resource<BaseResponse> {
        return safeApiCall { dataSource.callCreateRoom(createRoomRequest) }
    }

    override suspend fun callCurrentRoomNo(): Resource<CurrentRoomNoResponse> {
        return safeApiCall { dataSource.callCurrentRoomNo() }
    }

    override suspend fun callJoinRoomInfo(joinRoomInfoRequest: JoinRoomInfoRequest): Resource<BaseResponse> {
        return safeApiCall { dataSource.callJoinRoomInfo(joinRoomInfoRequest) }
    }

    override suspend fun callLeaveRoomInfo(): Resource<BaseResponse> {
        return safeApiCall { dataSource.callLeaveRoomInfo() }
    }

    override suspend fun callChangeTeam(team: String): Resource<BaseResponse> {
        return safeApiCall { dataSource.callChangeTeam(team) }
    }

    override suspend fun callChangeStatusRoomInfo(): Resource<BaseResponse> {
        return safeApiCall { dataSource.callChangeStatusRoomInfo() }
    }

    override suspend fun callRoomInfoTegMulti(): Resource<BaseResponse> {
        return safeApiCall { dataSource.callRoomInfoTegMulti() }
    }

    override suspend fun callChangeStatusUnready(): Resource<BaseResponse> {
        return safeApiCall { dataSource.callChangeStatusUnready() }
    }

    override suspend fun callFetchMultiPlayer(): Resource<FetchMultiPlayerResponse> {
        return safeApiCall { dataSource.callFetchMultiPlayer() }
    }

    override suspend fun callFetchMultiScore(): Resource<ScoreResponse> {
        return safeApiCall { dataSource.callFetchMultiScore() }
    }

    override suspend fun callAddMultiScore(): Resource<BaseResponse> {
        return safeApiCall { dataSource.callAddMultiScore() }
    }

    override suspend fun callFetchMultiItem(): Resource<MultiItemResponse> {
        return safeApiCall { dataSource.callFetchMultiItem() }
    }

    override suspend fun callAddMultiItem(): Resource<BaseResponse> {
        return safeApiCall { dataSource.callAddMultiItem() }
    }

    override suspend fun incomingSinglePeopleAll(socket: SinglePeopleAllSocket) {
        return dataSource.incomingSinglePeopleAll(socket)
    }

    override suspend fun incomingSingleItem(socket: SingleItemSocket) {
        return dataSource.incomingSingleItem(socket)
    }

    override suspend fun incomingSingleSuccessAnnouncement(socket: SingleSuccessAnnouncementSocket) {
        return dataSource.incomingSingleSuccessAnnouncement(socket)
    }

    override suspend fun incomingPlaygroundSinglePlayer(socket: PlaygroundSinglePlayerSocket) {
        return dataSource.incomingPlaygroundSinglePlayer(socket)
    }

    override suspend fun incomingRoomPeopleAll(socket: RoomPeopleAllSocket) {
        return dataSource.incomingRoomPeopleAll(socket)
    }

    override suspend fun incomingPlaygroundRoom(socket: PlaygroundRoomSocket) {
        return dataSource.incomingPlaygroundRoom(socket)
    }

    override suspend fun incomingRoomInfoTitle(socket: RoomInfoTitleSocket) {
        return dataSource.incomingRoomInfoTitle(socket)
    }

    override suspend fun incomingRoomInfoPlayers(socket: RoomInfoPlayersSocket) {
        return dataSource.incomingRoomInfoPlayers(socket)
    }

    override suspend fun incomingRoomInfoTegMulti(socket: RoomInfoTegMultiSocket) {
        return dataSource.incomingRoomInfoTegMulti(socket)
    }

    override suspend fun incomingMultiPlayerItems(socket: MultiPlayerItemsSocket) {
        return dataSource.incomingMultiPlayerItems(socket)
    }

    override suspend fun outgoingSingleItem() {
        return dataSource.outgoingSingleItem()
    }

    override suspend fun outgoingSingleSuccessAnnouncement() {
        return dataSource.outgoingSingleSuccessAnnouncement()
    }

    override suspend fun outgoingPlaygroundSinglePlayer(latLng: TegLatLng) {
        return dataSource.outgoingPlaygroundSinglePlayer(latLng)
    }

    override suspend fun outgoingPlaygroundRoom() {
        return dataSource.outgoingPlaygroundRoom()
    }

    override suspend fun outgoingRoomInfoPlayers() {
        return dataSource.outgoingRoomInfoPlayers()
    }

    override suspend fun outgoingRoomInfoTegMulti() {
        return dataSource.outgoingRoomInfoTegMulti()
    }

    override suspend fun outgoingMultiPlayerItems() {
        return dataSource.outgoingMultiPlayerItems()
    }

}
