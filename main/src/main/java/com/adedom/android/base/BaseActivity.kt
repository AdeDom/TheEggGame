package com.adedom.android.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.adedom.android.util.toast
import com.adedom.teg.domain.Resource

abstract class BaseActivity : AppCompatActivity() {

    protected inline fun <reified T> LiveData<T>.observe(crossinline onNext: (T) -> Unit) {
        observe(this@BaseActivity, { onNext(it) })
    }

    protected fun LiveData<Resource.Error>.observeError() {
        observe(this@BaseActivity, {
            it.throwable.printStackTrace()
            toast(it.throwable.message)
        })
    }

}
