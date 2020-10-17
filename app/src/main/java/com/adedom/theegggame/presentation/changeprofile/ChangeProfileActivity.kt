package com.adedom.theegggame.presentation.changeprofile

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.adedom.teg.presentation.changeprofile.ChangeProfileViewModel
import com.adedom.theegggame.R
import com.adedom.theegggame.base.BaseActivity
import com.adedom.theegggame.util.extension.toast
import kotlinx.android.synthetic.main.activity_change_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangeProfileActivity : BaseActivity() {

    val viewModel by viewModel<ChangeProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_profile)

        viewModel.state.observe { state ->
            progressBar.visibility = if (state.loading) View.VISIBLE else View.INVISIBLE
        }

        viewModel.playerInfo.observe(this, { playerInfo ->
            if (playerInfo == null) return@observe

            etName.setText(playerInfo.name)
            etGender.setText(playerInfo.gender)
            etBirthDate.setText(playerInfo.birthDate)
        })

        viewModel.changeProfileEvent.observe { response ->
            toast(response.message)
            if (response.success) finish()
        }

        etName.addTextChangedListener { viewModel.setStateName(it.toString()) }
        etGender.addTextChangedListener { viewModel.setStateGender(it.toString()) }
        etBirthDate.addTextChangedListener { viewModel.setStateBirthDate(it.toString()) }

        btChangeProfile.setOnClickListener {
            viewModel.callChangeProfile()
        }

    }

}
