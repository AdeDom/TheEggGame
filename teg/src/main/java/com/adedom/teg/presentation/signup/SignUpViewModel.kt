package com.adedom.teg.presentation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adedom.teg.presentation.usercase.SignUpUseCase
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.domain.Resource
import com.adedom.teg.models.request.SignUpRequest
import com.adedom.teg.models.response.SignInResponse
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val useCase: SignUpUseCase
) : BaseViewModel<SignUpState>(SignUpState()) {

    private val _signUpEvent = MutableLiveData<SignInResponse>()
    val signUpEvent: LiveData<SignInResponse>
        get() = _signUpEvent

    fun setStateUsername(username: String) {
        setState { copy(username = username) }
    }

    fun setStatePassword(password: String) {
        setState { copy(password = password) }
    }

    fun setStateName(name: String) {
        setState { copy(name = name) }
    }

    fun setStateGender(gender: String) {
        setState { copy(gender = gender) }
    }

    fun setStateBirthDate(birthDate: String) {
        setState { copy(birthDate = birthDate) }
    }

    fun callSignUp() {
        launch {
            setState { copy(loading = true) }
            val request = SignUpRequest(
                username = state.value?.username,
                password = state.value?.password,
                name = state.value?.name,
                gender = state.value?.gender,
                birthDate = state.value?.birthDate,
            )
            when (val resource = useCase.callSignUp(request)) {
                is Resource.Success -> _signUpEvent.value = resource.data
                is Resource.Error -> setError(resource)
            }
            setState { copy(loading = false) }
        }
    }

}
