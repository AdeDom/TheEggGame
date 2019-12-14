package com.adedom.utility.util

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel

abstract class BaseActivity<VM : ViewModel> : AppCompatActivity() {
    val TAG = "MyTag"
    lateinit var viewModel: VM
    val factory = BaseFactory()
}