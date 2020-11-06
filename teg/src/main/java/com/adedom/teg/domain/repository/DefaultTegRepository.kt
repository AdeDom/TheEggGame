package com.adedom.teg.domain.repository

import androidx.lifecycle.LiveData
import com.adedom.teg.data.db.entities.BackpackEntity
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.data.network.websocket.RoomPeopleAllSocket
import com.adedom.teg.domain.Resource
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

    suspend fun callFetchItemCollection(): Resource<BackpackResponse>

    suspend fun callItemCollection(itemCollectionRequest: ItemCollectionRequest): Resource<BaseResponse>

    suspend fun callMultiItemCollection(multiItemCollectionRequest: MultiItemCollectionRequest): Resource<BaseResponse>

    suspend fun callFetchRooms(): Resource<RoomsResponse>

    suspend fun incomingRoomPeopleAll(socket: RoomPeopleAllSocket)

}
