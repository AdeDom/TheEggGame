package com.adedom.teg.presentation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.model.SignUpModel
import com.adedom.teg.models.response.SignInResponse
import com.adedom.teg.presentation.usercase.SignUpUseCase
import kotlinx.coroutines.launch
import java.util.*

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

    fun setStateRePassword(rePassword: String) {
        setState { copy(rePassword = rePassword) }
    }

    fun setStateName(name: String) {
        setState { copy(name = name) }
    }

    fun setStateGender(gender: String) {
        setState { copy(gender = gender) }
    }

    fun setStateBirthDate(birthDate: Calendar) {
        setState {
            copy(
                birthDateCalendar = birthDate,
                birthDateString = useCase.getStringBirthDate(birthDate)
            )
        }
    }

    fun callSignUp() {
        launch {
            setState { copy(loading = true, isClickable = false) }
            val request = SignUpModel(
                username = state.value?.username,
                password = state.value?.password,
                rePassword = state.value?.rePassword,
                name = state.value?.name,
                gender = state.value?.gender,
                birthDate = state.value?.birthDateCalendar,
            )
            when (val resource = useCase.callSignUp(request)) {
                is Resource.Success -> _signUpEvent.value = resource.data
                is Resource.Error -> setError(resource)
            }
            setState { copy(loading = false, isClickable = true) }
        }
    }

}
