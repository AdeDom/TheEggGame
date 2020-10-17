package com.adedom.teg.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adedom.teg.domain.Resource
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<S : Any>(private val initialState: S) : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()
    private val exceptionHandler = CoroutineExceptionHandler { _, err ->
        setError(Resource.Error(err))
    }
    private val _state = MutableLiveData<S>().apply { value = initialState }
    private val _error = MutableLiveData<Resource.Error>()
    private val _attachFirstTime = MutableLiveData<Unit>().apply { value = Unit }

    val state: LiveData<S> = _state
    val error: LiveData<Resource.Error> = _error
    val attachFirstTime: LiveData<Unit> = _attachFirstTime

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main + exceptionHandler

    override fun onCleared() {
        coroutineContext.cancel()
        super.onCleared()
    }

    protected fun setState(reducer: S.() -> S) {
        _state.value = (_state.value ?: initialState).reducer()
    }

    protected fun setError(error: Resource.Error) {
        _error.value = error
    }

}
