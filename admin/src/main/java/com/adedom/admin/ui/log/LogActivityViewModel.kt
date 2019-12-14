package com.adedom.admin.ui.log

import androidx.lifecycle.ViewModel
import com.adedom.admin.data.repositories.BaseRepository

class LogActivityViewModel(private val repository: BaseRepository) : ViewModel() {

    fun getLogs() = repository.getLogs()
}