package com.adedom.teg.data.repository

import androidx.lifecycle.LiveData
import com.adedom.teg.base.BaseRepository
import com.adedom.teg.data.db.entities.BackpackEntity
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.data.network.source.TegDataSource
import com.adedom.teg.data.network.websocket.PlaygroundRoomSocket
import com.adedom.teg.data.network.websocket.RoomPeopleAllSocket
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.request.*
import com.adedom.teg.models.response.*
import com.adedom.teg.models.websocket.CreateRoomIncoming
import okhttp3.MultipartBody

class DefaultTegAuthRepositoryImpl(
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

    override suspend fun callFetchItemCollection(): Resource<BackpackResponse> {
        return safeApiCall { dataSource.callFetchItemCollection() }
    }

    override suspend fun callItemCollection(itemCollectionRequest: ItemCollectionRequest): Resource<BaseResponse> {
        return safeApiCall { dataSource.callItemCollection(itemCollectionRequest) }
    }

    override suspend fun callMultiItemCollection(multiItemCollectionRequest: MultiItemCollectionRequest): Resource<BaseResponse> {
        return safeApiCall { dataSource.callMultiItemCollection(multiItemCollectionRequest) }
    }

    override suspend fun incomingRoomPeopleAll(socket: RoomPeopleAllSocket) {
        return dataSource.incomingRoomPeopleAll(socket)
    }

    override suspend fun incomingPlaygroundRoom(socket: PlaygroundRoomSocket) {
        return dataSource.incomingPlaygroundRoom(socket)
    }

    override suspend fun outgoingCreateRoom(socket: CreateRoomIncoming) {
        return dataSource.outgoingCreateRoom(socket)
    }

}
