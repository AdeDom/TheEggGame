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

    @PATCH("api/application/change-current-mode/{mode}")
    suspend fun callChangeCurrentMode(@Path("mode") mode: String): BaseResponse

    @GET("api/single/item-collection")
    suspend fun callFetchItemCollection(): BackpackResponse

    @PATCH("api/single/item-collection/{singleId}")
    suspend fun callSingleItemCollection(@Path("singleId") singleId: Int?): BaseResponse

    @POST("api/multi/item-collection")
    suspend fun callMultiItemCollection(@Body multiItemCollectionRequest: MultiItemCollectionRequest): BaseResponse

    @POST("api/multi/create-room")
    suspend fun callCreateRoom(@Body createRoomRequest: CreateRoomRequest): BaseResponse

    @POST("api/multi/join-room-info")
    suspend fun callJoinRoomInfo(@Body joinRoomInfoRequest: JoinRoomInfoRequest): BaseResponse

    @DELETE("api/multi/leave-room-info")
    suspend fun callLeaveRoomInfo(): BaseResponse

    @PATCH("api/multi/change-team/{team}")
    suspend fun callChangeTeam(@Path("team") team: String): BaseResponse

    @PATCH("api/multi/change-status-room-info")
    suspend fun callChangeStatusRoomInfo(): BaseResponse

    @PATCH("api/multi/room-info-teg-multi")
    suspend fun callRoomInfoTegMulti(): BaseResponse

    @PATCH("api/multi/change-status-off")
    suspend fun callChangeStatusUnready(): BaseResponse

    @GET("api/multi/fetch-multi-player")
    suspend fun callFetchMultiPlayer(): FetchMultiPlayerResponse

    @GET("api/multi/fetch-multi-score")
    suspend fun callFetchMultiScore(): ScoreResponse

    @POST("api/multi/add-multi-score")
    suspend fun callAddMultiScore(@Body addMultiScoreRequest: AddMultiScoreRequest): BaseResponse

    @GET("api/multi/fetch-multi-item")
    suspend fun callFetchMultiItem(): MultiItemResponse

    @POST("api/multi/add-multi-item")
    suspend fun callAddMultiItem(): BaseResponse

    @GET("api/multi/multi-player-end-teg")
    suspend fun callFetchMultiPlayerEndTeg(): MultiPlayerEndGameResponse

    @PATCH("api/multi/multi-player-end-game")
    suspend fun callMultiPlayerEndGame(): BaseResponse

}
