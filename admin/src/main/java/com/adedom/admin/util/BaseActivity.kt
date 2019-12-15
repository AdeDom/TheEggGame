package com.adedom.admin.util

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.adedom.admin.R
import kotlinx.android.synthetic.main.activity_base.*

abstract class BaseActivity<VM : ViewModel> : AppCompatActivity() {

    val TAG = "BaseActivity"
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        sActivity = this@BaseActivity
        sContext = baseContext

        mSwipeRefreshLayout.also {
            it.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_light
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        lateinit var sActivity: Activity
        lateinit var sContext: Context
    }
}