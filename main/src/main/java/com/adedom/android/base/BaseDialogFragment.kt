package com.adedom.android.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.adedom.android.R
import com.adedom.android.util.toast
import com.adedom.teg.domain.Resource
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

abstract class BaseDialogFragment(
    @LayoutRes private val layout: Int
) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout, container, false)
    }

    protected inline fun <reified T> LiveData<T>.observe(crossinline onNext: (T) -> Unit) {
        observe(this@BaseDialogFragment, { onNext(it) })
    }

    protected inline fun <reified T> Flow<T>.observe(crossinline onNext: (T) -> Unit) {
        this
            .catch { e ->
                context.toast(e.message, Toast.LENGTH_LONG)
            }
            .asLiveData()
            .observe(this@BaseDialogFragment, { onNext(it) })
    }

    protected fun LiveData<Resource.Error>.observeError() {
        observe(this@BaseDialogFragment, {
            if (it.tokenExpire) {
                findNavController().navigate(R.id.action_global_splashScreenFragment)
            } else {
                it.throwable.printStackTrace()
                context.toast(it.throwable.message, Toast.LENGTH_LONG)
            }
        })
    }

}
