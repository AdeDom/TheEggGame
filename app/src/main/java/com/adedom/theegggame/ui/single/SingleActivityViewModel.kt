package com.adedom.theegggame.ui.single

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.theegggame.data.repositories.DatabaseRepository

class SingleActivityViewModel : ViewModel() { // 2/12/19

    val repository by lazy { DatabaseRepository() }

    fun insertItem(): LiveData<JsonResponse> = repository.result

}
