package com.adedom.teg.presentation.imageprofile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.domain.Resource
import com.adedom.teg.models.response.BaseResponse
import com.adedom.teg.presentation.usercase.ChangeImageUseCase
import com.adedom.teg.util.convertMultipartBodyPart
import kotlinx.coroutines.launch

class ImageProfileViewModel(
    private val context: Context,
    private val useCase: ChangeImageUseCase,
) : BaseViewModel<ImageProfileState>(ImageProfileState()) {

    private val _uploadImageProfileEvent = MutableLiveData<BaseResponse>()
    val uploadImageProfileEvent: LiveData<BaseResponse>
        get() = _uploadImageProfileEvent

    fun setStateImage(imageUri: String) {
        setState { copy(imageUri = imageUri, isImageUri = true) }
    }

    fun callUploadImageProfile() {
        launch {
            setState { copy(loading = true) }

            val fileUri = Uri.parse(state.value?.imageUri)
            val request = context.convertMultipartBodyPart(fileUri, "imageFile")
            when (val resource = useCase.callChangeImageProfile(request)) {
                is Resource.Success -> _uploadImageProfileEvent.value = resource.data
                is Resource.Error -> setError(resource)
            }

            setState { copy(loading = false) }
        }
    }

}
