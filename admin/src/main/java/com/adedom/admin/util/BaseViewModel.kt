package com.adedom.admin.util

import androidx.lifecycle.ViewModel
import com.adedom.admin.data.network.BaseApi
import com.adedom.admin.data.repositories.BaseRepository

abstract class BaseViewModel : ViewModel() {
    val repository by lazy { BaseRepository(BaseApi.invoke()) }
}