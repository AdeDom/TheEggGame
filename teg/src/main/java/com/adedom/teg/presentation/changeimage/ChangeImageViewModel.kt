package com.adedom.teg.presentation.changeimage

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.response.BaseResponse
import com.adedom.teg.presentation.usercase.ChangeImageUseCase
import com.adedom.teg.util.convertMultipartBodyPart
import kotlinx.coroutines.launch

class ChangeImageViewModel(
    private val context: Context,
    private val useCase: ChangeImageUseCase,
    private val repository: DefaultTegRepository,
) : BaseViewModel<ChangeImageState>(ChangeImageState()) {

    private val _changeImageProfileEvent = MutableLiveData<BaseResponse>()
    val changeImageProfileEvent: LiveData<BaseResponse>
        get() = _changeImageProfileEvent

    val fetchPlayerInfo: LiveData<PlayerInfoEntity>
        get() = repository.getDbPlayerInfoLiveData()

    fun setStateImage(imageUri: String) {
        setState { copy(imageUri = imageUri, isImageUri = true) }
    }

    fun callChangeImageProfile() {
        launch {
            setState { copy(loading = true) }

            val fileUri = Uri.parse(state.value?.imageUri)
            val request = context.convertMultipartBodyPart(fileUri, "imageFile")
            when (val resource = useCase.callChangeImageProfile(request)) {
                is Resource.Success -> _changeImageProfileEvent.value = resource.data
                is Resource.Error -> setError(resource)
            }

            setState { copy(loading = false) }
        }
    }

}
