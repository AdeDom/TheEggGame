package com.adedom.teg.presentation.usercase

import androidx.lifecycle.LiveData
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.domain.Resource
import com.adedom.teg.models.response.BaseResponse
import okhttp3.MultipartBody

interface ChangeImageUseCase {

    fun fetchPlayerInfo(): LiveData<PlayerInfoEntity>

    suspend fun callChangeImageProfile(imageFile: MultipartBody.Part): Resource<BaseResponse>

}
