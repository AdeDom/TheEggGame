package com.adedom.theegggame.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.theegggame.data.repositories.RetrofitRepository

class ChangePasswordDialogViewModel(private val repository: RetrofitRepository) :
    ViewModel() { // 2/12/19

    fun changePassword(
        playerId: String,
        oldPassword: String,
        newPassword: String
    ): LiveData<JsonResponse> {
        return repository.changePassword(playerId, oldPassword, newPassword)
    }

}
