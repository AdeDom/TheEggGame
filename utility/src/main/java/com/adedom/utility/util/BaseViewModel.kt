package com.adedom.utility.util

import androidx.lifecycle.ViewModel

open class BaseViewModel<Repository : Any>(val repository: Repository) : ViewModel()
