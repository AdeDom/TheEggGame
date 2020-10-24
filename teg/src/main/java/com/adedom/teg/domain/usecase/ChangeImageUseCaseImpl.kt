package com.adedom.teg.domain.usecase

import androidx.lifecycle.LiveData
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.response.BaseResponse
import com.adedom.teg.presentation.usercase.ChangeImageUseCase
import okhttp3.MultipartBody

class ChangeImageUseCaseImpl(
    private val repository: DefaultTegRepository,
) : ChangeImageUseCase {

    override fun fetchPlayerInfo(): LiveData<PlayerInfoEntity> {
        return repository.getDbPlayerInfoLiveData()
    }

    override suspend fun callChangeImageProfile(imageFile: MultipartBody.Part): Resource<BaseResponse> {
        val resource = repository.callChangeImageProfile(imageFile)

        when (resource) {
            is Resource.Success -> {
                if (resource.data.success) {
                    callFetchPlayerInfo()
                }
            }
        }

        return resource
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
                }
            }
            is Resource.Error -> callFetchPlayerInfo()
        }
    }

}
