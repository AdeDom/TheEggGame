package com.adedom.android.presentation.playerprofile

import android.os.Bundle
import android.view.View
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.setImageCircle
import com.adedom.teg.presentation.playerprofile.PlayerProfileViewModel
import kotlinx.android.synthetic.main.fragment_player_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerProfileFragment : BaseFragment(R.layout.fragment_player_profile) {

    private val viewModel by viewModel<PlayerProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDbPlayerInfoLiveData.observe(viewLifecycleOwner, { playerInfo ->
            if (playerInfo == null) return@observe

            tvName.text = getString(R.string.player_profile_name, playerInfo.name)
            tvGender.text = getString(R.string.player_profile_gender, playerInfo.gender)
            tvBirthDate.text = getString(R.string.player_profile_birth_date, playerInfo.birthDate)
            ivImageProfile.setImageCircle(playerInfo.image)
        })

        viewModel.error.observeError()
    }

}
