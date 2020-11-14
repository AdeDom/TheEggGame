package com.adedom.teg.presentation.usercase

import com.adedom.teg.domain.Resource
import com.adedom.teg.models.response.BaseResponse
import okhttp3.MultipartBody

interface ChangeImageUseCase {

    suspend fun callChangeImageProfile(imageFile: MultipartBody.Part): Resource<BaseResponse>

}
