package com.adedom.teg.presentation.changeprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adedom.teg.presentation.usercase.ChangeProfileUseCase
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.request.ChangeProfileRequest
import com.adedom.teg.models.response.BaseResponse
import kotlinx.coroutines.launch

class ChangeProfileViewModel(
    private val useCase: ChangeProfileUseCase,
    private val repository: DefaultTegRepository,
) : BaseViewModel<ChangeProfileState>(ChangeProfileState()) {

    private val _changeProfileEvent = MutableLiveData<BaseResponse>()
    val changeProfileEvent: LiveData<BaseResponse>
        get() = _changeProfileEvent

    val playerInfo: LiveData<PlayerInfoEntity>
        get() = repository.getDbPlayerInfoLiveData()

    fun setStateName(name: String) {
        setState { copy(name = name) }
    }

    fun setStateGender(gender: String) {
        setState { copy(gender = gender) }
    }

    fun setStateBirthDate(birthDate: String) {
        setState { copy(birthDate = birthDate) }
    }

    fun callChangeProfile() {
        launch {
            setState { copy(loading = true) }

            val request = ChangeProfileRequest(
                name = state.value?.name,
                gender = state.value?.gender,
                birthDate = state.value?.birthDate,
            )

            when (val resource = useCase.callChangeProfile(request)) {
                is Resource.Success -> _changeProfileEvent.value = resource.data
                is Resource.Error -> setError(resource)
            }

            setState { copy(loading = false) }
        }
    }

}
