package com.adedom.android.presentation.changeprofile

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.hideSoftKeyboard
import com.adedom.android.util.setVisibility
import com.adedom.android.util.snackbar
import com.adedom.teg.presentation.changeprofile.ChangeProfileViewModel
import com.adedom.teg.util.TegConstant
import kotlinx.android.synthetic.main.fragment_change_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class ChangeProfileFragment : BaseFragment(R.layout.fragment_change_profile) {

    private val viewModel by viewModel<ChangeProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe { state ->
            progressBar.setVisibility(state.loading)

            tvBirthDate.text = state.birthDateString
        }

        viewModel.playerInfo.observe(viewLifecycleOwner, { playerInfo ->
            if (playerInfo == null) return@observe

            etName.setText(playerInfo.name)
            when (playerInfo.gender) {
                TegConstant.GENDER_MALE -> rbMale.isChecked = true
                TegConstant.GENDER_FEMALE -> rbFemale.isChecked = true
            }
            tvBirthDate.text = playerInfo.birthDate

            playerInfo.gender?.let { viewModel.setStateGender(it) }
            playerInfo.birthDate?.let { viewModel.setStateBirthDateString(it) }
        })

        viewModel.changeProfileEvent.observe { response ->
            rootLayout.snackbar(response.message)
            if (response.success) {
                findNavController().popBackStack()
            }
        }

        etName.addTextChangedListener { viewModel.setStateName(it.toString()) }
        rbMale.setOnClickListener { viewModel.setStateGender(TegConstant.GENDER_MALE) }
        rbFemale.setOnClickListener { viewModel.setStateGender(TegConstant.GENDER_FEMALE) }

        // event
        ivBirthDate.setOnClickListener {
            context?.let {
                val birthDate = tvBirthDate.text.toString()
                val calendar = Calendar.getInstance()

                val dpd = DatePickerDialog(
                    it,
                    { _, year, month, dayOfMonth ->
                        calendar.set(year, month, dayOfMonth)
                        viewModel.setStateBirthDateCalendar(calendar)
                    },
                    viewModel.getIntCalendarYear(birthDate),
                    viewModel.getIntCalendarMonth(birthDate),
                    viewModel.getIntCalendarDayOfMonth(birthDate),
                )

                dpd.show()
            }
        }

        btBack.setOnClickListener {
            findNavController().popBackStack()
        }

        btChangeProfile.setOnClickListener {
            viewModel.callChangeProfile()
        }

        rootLayout.setOnClickListener { activity?.hideSoftKeyboard() }
    }

}
