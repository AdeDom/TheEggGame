package com.adedom.teg.presentation.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.model.ValidateSignIn
import com.adedom.teg.models.request.SignInRequest
import com.adedom.teg.models.response.SignInResponse
import com.adedom.teg.presentation.usercase.SignInUseCase
import com.adedom.teg.sharedpreference.service.PreferenceConfig
import kotlinx.coroutines.launch

class SignInViewModel(
    private val useCase: SignInUseCase,
    private val preferenceConfig: PreferenceConfig,
) : BaseViewModel<SignInState>(SignInState()) {

    private val _signInEvent = MutableLiveData<ValidateSignIn>()
    val signInEvent: LiveData<ValidateSignIn>
        get() = _signInEvent

    private val _signIn = MutableLiveData<SignInResponse>()
    val signIn: LiveData<SignInResponse>
        get() = _signIn

    fun setStateUsername(username: String) {
        setState {
            copy(
                username = username,
                isValidUsername = useCase.isValidateUsername(username)
            )
        }
    }

    fun setStatePassword(password: String) {
        setState {
            copy(
                password = password,
                isValidPassword = useCase.isValidatePassword(password)
            )
        }
    }

    fun onSignIn() {
        val signIn = SignInRequest(
            username = state.value?.username,
            password = state.value?.password
        )
        _signInEvent.value = useCase.validateSignIn(signIn)
    }

    fun callSignIn() {
        launch {
            setState { copy(loading = true, isClickable = false) }
            val request = SignInRequest(
                username = state.value?.username,
                password = state.value?.password
            )
            when (val resource = useCase.callSignIn(request)) {
                is Resource.Success -> _signIn.value = resource.data
                is Resource.Error -> setError(resource)
            }
            setState { copy(loading = false, isClickable = true) }
        }
    }

    fun getConfigUsername(): String {
        return preferenceConfig.username
    }

}
