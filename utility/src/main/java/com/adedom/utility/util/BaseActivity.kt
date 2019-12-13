package com.adedom.utility.util

import androidx.appcompat.app.AppCompatActivity
import com.adedom.utility.util.BaseFactory

abstract class BaseActivity : AppCompatActivity() {
    val TAG = "MyTag"
    val factory = BaseFactory()
}