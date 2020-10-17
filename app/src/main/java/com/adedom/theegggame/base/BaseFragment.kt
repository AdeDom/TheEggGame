package com.adedom.theegggame.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.adedom.teg.domain.Resource
import com.adedom.theegggame.R
import com.adedom.theegggame.util.extension.toast

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
