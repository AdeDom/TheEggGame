package com.adedom.admin.util

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.adedom.admin.R
import com.adedom.utility.*
import kotlinx.android.synthetic.main.activity_base.*

abstract class BaseActivity<VM : ViewModel> : AppCompatActivity(), OnAttachListener {

    val TAG = "BaseActivity"
    lateinit var viewModel: VM

    init {
        search = ""
        spinnerLevel = 0
        isCheckOnline = true
        isCheckOffline = true
        dateBegin = DATE_BEGIN
        timeBegin = TIME_BEGIN
        dateEnd = DATE_NOW
        timeEnd = TIME_NOW
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

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
}