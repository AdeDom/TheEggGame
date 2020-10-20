package com.adedom.teg.presentation.changeprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.model.ChangeProfileModel
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.response.BaseResponse
import com.adedom.teg.presentation.usercase.ChangeProfileUseCase
import kotlinx.coroutines.launch
import java.util.*

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

    fun setStateBirthDateString(birthDateString: String) {
        setState { copy(birthDateString = birthDateString) }
    }

    fun setStateBirthDateCalendar(birthDateCalendar: Calendar) {
        setState {
            copy(
                birthDateCalendar = birthDateCalendar,
                birthDateString = useCase.getStringBirthDate(birthDateCalendar)
            )
        }
    }

    fun getIntCalendarYear(date: String): Int {
        return useCase.getIntCalendarYear(date)
    }

    fun getIntCalendarMonth(date: String): Int {
        return useCase.getIntCalendarMonth(date)
    }

    fun getIntCalendarDayOfMonth(date: String): Int {
        return useCase.getIntCalendarDayOfMonth(date)
    }

    fun callChangeProfile() {
        launch {
            setState { copy(loading = true) }

            val request = ChangeProfileModel(
                name = state.value?.name,
                gender = state.value?.gender,
                birthDateString = state.value?.birthDateString,
                birthDateCalendar = state.value?.birthDateCalendar,
            )

            when (val resource = useCase.callChangeProfile(request)) {
                is Resource.Success -> _changeProfileEvent.value = resource.data
                is Resource.Error -> setError(resource)
            }

            setState { copy(loading = false) }
        }
    }

}
