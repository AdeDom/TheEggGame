package com.adedom.teg.data.network.source

import androidx.lifecycle.LiveData
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.models.request.*
import com.adedom.teg.models.response.*
import okhttp3.MultipartBody

interface TegDataSource {

    suspend fun savePlayerInfo(playerInfo: PlayerInfoEntity)

    suspend fun getDbPlayerInfo(): PlayerInfoEntity?

    fun getDbPlayerInfoLiveData(): LiveData<PlayerInfoEntity>

    suspend fun deletePlayerInfo()

    suspend fun callSignIn(signIn: SignInRequest): SignInResponse

    suspend fun callSignUp(signUp: SignUpRequest): SignInResponse

    suspend fun callRefreshToken(refreshToken: RefreshTokenRequest): SignInResponse

    suspend fun callFetchPlayerInfo(): PlayerInfoResponse

    suspend fun callChangeImageProfile(imageFile: MultipartBody.Part): BaseResponse

    suspend fun callPlayerState(state: String): BaseResponse

    suspend fun callChangePassword(changePassword: ChangePasswordRequest): BaseResponse

    suspend fun callChangeProfile(changeProfile: ChangeProfileRequest): BaseResponse

    suspend fun callFetchRankPlayers(search: String, limit: Int): RankPlayersResponse

    suspend fun callLogActiveOn(): BaseResponse

    suspend fun callLogActiveOff(): BaseResponse

    suspend fun callFetchItemCollection(): BackpackResponse

    suspend fun callItemCollection(itemCollectionRequest: ItemCollectionRequest): BaseResponse

}
