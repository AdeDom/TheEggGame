package com.adedom.teg.domain.usecase

import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.model.ChangeProfileModel
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.request.ChangeProfileRequest
import com.adedom.teg.models.response.BaseResponse
import com.adedom.teg.presentation.usercase.ChangeProfileUseCase
import java.util.*

class ChangeProfileUseCaseImpl(
    private val repository: DefaultTegRepository,
) : ChangeProfileUseCase {

    override suspend fun callChangeProfile(changeProfile: ChangeProfileModel): Resource<BaseResponse> {
        val request = ChangeProfileRequest(
            name = changeProfile.name,
            gender = changeProfile.gender,
            birthDate = getBirthDate(changeProfile)
        )
        val resource = repository.callChangeProfile(request)

        when (resource) {
            is Resource.Success -> {
                val result = resource.data
                if (result.success) {
                    callFetchPlayerInfo()
                }
            }
        }

        return resource
    }

    private fun getBirthDate(changeProfile: ChangeProfileModel): String? {
        return if (changeProfile.birthDateCalendar == null) {
            changeProfile.birthDateString
        } else {
            getStringBirthDate(changeProfile.birthDateCalendar)
        }
    }

    private suspend fun callFetchPlayerInfo() {
        when (val resource = repository.callFetchPlayerInfo()) {
            is Resource.Success -> {
                if (resource.data.success) {
                    val result = resource.data.playerInfo

                    val playerInfoEntity = PlayerInfoEntity(
                        playerId = result?.playerId.orEmpty(),
                        username = result?.username,
                        name = result?.name,
                        image = result?.image,
                        level = result?.level,
                        state = result?.state,
                        gender = result?.gender,
                        birthDate = result?.birthDate,
                    )

                    repository.savePlayerInfo(playerInfoEntity)
                } else {
                    callFetchPlayerInfo()
                }
            }
            is Resource.Error -> callFetchPlayerInfo()
        }
    }

    override fun getStringBirthDate(birthDate: Calendar?): String {
        val year = birthDate?.get(Calendar.YEAR)
        val month = birthDate?.get(Calendar.MONTH)
        val dayOfMonth = birthDate?.get(Calendar.DAY_OF_MONTH)
        return "$dayOfMonth/${month?.plus(1)}/$year"
    }

}
