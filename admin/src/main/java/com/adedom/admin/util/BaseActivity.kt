package com.adedom.admin.util

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.adedom.utility.*

abstract class BaseActivity<VM : ViewModel> : AppCompatActivity(), OnAttachListener {

    val TAG = "BaseActivity"
    lateinit var viewModel: VM

    init {
        name = ""
        spinnerIndexStart = 0
        spinnerIndexEnd = 98
        isCheckOnline = true
        isCheckOffline = true
        isCheckMale = true
        isCheckFemale = true
        dateBegin = DATE_BEGIN
        timeBegin = TIME_BEGIN
        dateEnd = DATE_NOW
        timeEnd = TIME_NOW
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}