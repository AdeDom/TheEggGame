package com.adedom.android.presentation.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.adedom.android.R
import com.adedom.android.base.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_about.*

class AboutDialog : BaseDialogFragment(R.layout.dialog_about) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btHide.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}
