package com.adedom.admin.util

import androidx.lifecycle.ViewModel
import com.adedom.admin.data.networks.BaseApi
import com.adedom.admin.data.repositories.BaseRepository

open class BaseViewModel : ViewModel() {
    val repository by lazy { BaseRepository(BaseApi.invoke()) }
}