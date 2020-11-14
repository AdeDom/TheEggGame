package com.adedom.android.presentation.auth

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.adedom.android.R
import com.adedom.android.base.BaseActivity
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : BaseActivity() {

    private val appBarConfiguration by lazy { AppBarConfiguration(setOf()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        setSupportActionBar(toolbar)
        setupActionBarWithNavController(fragment.findNavController(), appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return fragment.findNavController().navigateUp(appBarConfiguration) ||
                super.onSupportNavigateUp()
    }

}
