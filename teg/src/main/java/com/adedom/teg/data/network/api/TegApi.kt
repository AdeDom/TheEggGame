package com.adedom.teg.data.network.api

import com.adedom.teg.models.request.*
import com.adedom.teg.models.response.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface TegApi {

    @POST("api/auth/sign-in")
    suspend fun callSignIn(@Body signIn: SignInRequest): SignInResponse

    @POST("api/auth/sign-up")
    suspend fun callSignUp(@Body signUp: SignUpRequest): SignInResponse

    @POST("api/auth/refresh-token")
    suspend fun callRefreshToken(@Body refreshToken: RefreshTokenRequest): SignInResponse

    @GET("api/account/player-info")
    suspend fun callFetchPlayerInfo(): PlayerInfoResponse

    @Multipart
    @PUT("api/account/image-profile")
    suspend fun callChangeImageProfile(@Part imageFile: MultipartBody.Part): BaseResponse

    @PATCH("api/account/state/{state}")
    suspend fun callPlayerState(@Path("state") state: String): BaseResponse

    @PUT("api/account/change-password")
    suspend fun callChangePassword(@Body changePassword: ChangePasswordRequest): BaseResponse

    @PUT("api/account/change-profile")
    suspend fun callChangeProfile(@Body changeProfile: ChangeProfileRequest): BaseResponse

    @PUT("api/account/change-lat-lng")
    suspend fun callChangeLatLng(@Body changeLatLngRequest: ChangeLatLngRequest): BaseResponse

    @GET("api/application/rank/rank")
    suspend fun callFetchRankPlayers(
        @Query("search") search: String,
        @Query("limit") limit: Int,
    ): RankPlayersResponse

    @POST("api/application/log-active")
    suspend fun callLogActiveOn(): BaseResponse

    @PATCH("api/application/log-active")
    suspend fun callLogActiveOff(): BaseResponse

    @GET("api/application/fetch-mission")
    suspend fun callFetchMission(): MissionResponse

    @POST("api/application/mission")
    suspend fun callMissionMain(@Body missionRequest: MissionRequest): BaseResponse

    @GET("api/single/item-collection")
    suspend fun callFetchItemCollection(): BackpackResponse

    @POST("api/single/item-collection")
    suspend fun callItemCollection(@Body itemCollectionRequest: ItemCollectionRequest): BaseResponse

    @POST("api/multi/item-collection")
    suspend fun callMultiItemCollection(@Body multiItemCollectionRequest: MultiItemCollectionRequest): BaseResponse

    @POST("api/multi/create-room")
    suspend fun callCreateRoom(@Body createRoomRequest: CreateRoomRequest): BaseResponse

    @POST("api/multi/join-room-info")
    suspend fun callJoinRoomInfo(@Body joinRoomInfoRequest: JoinRoomInfoRequest): BaseResponse

}
