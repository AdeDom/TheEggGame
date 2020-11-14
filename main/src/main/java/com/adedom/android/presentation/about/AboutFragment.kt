package com.adedom.android.presentation.about

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.adedom.android.R
import com.adedom.android.base.BaseFragment

class AboutFragment : BaseFragment(R.layout.fragment_about) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)?.supportActionBar?.show()
    }

}
