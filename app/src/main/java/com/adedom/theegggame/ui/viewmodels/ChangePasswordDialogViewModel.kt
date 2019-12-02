package com.adedom.theegggame.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.theegggame.data.repositories.PlayerRepository

class ChangePasswordDialogViewModel : ViewModel() { // 2/12/19

    val response = PlayerRepository()

    fun result(): LiveData<JsonResponse> = response.result

}
