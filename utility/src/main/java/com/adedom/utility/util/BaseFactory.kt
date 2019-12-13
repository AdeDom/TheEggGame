package com.adedom.utility.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class BaseFactory : ViewModelProvider.NewInstanceFactory() {
    var viewModel: (() -> ViewModel)? = null
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModel?.invoke() as T
    }
}

