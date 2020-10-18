package com.adedom.android.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.adedom.android.R
import com.adedom.android.util.toast
import com.adedom.teg.domain.Resource

abstract class BaseFragment(@LayoutRes private val layout: Int) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout, container, false)
    }

    protected inline fun <reified T> LiveData<T>.observe(crossinline onNext: (T) -> Unit) {
        observe(this@BaseFragment, { onNext(it) })
    }

    protected fun LiveData<Resource.Error>.observeError() {
        observe(this@BaseFragment, {
            if (it.tokenExpire) {
                findNavController().navigate(R.id.action_global_splashScreenFragment)
            } else {
                it.throwable.printStackTrace()
                context?.toast(it.throwable.message)
            }
        })
    }

}
