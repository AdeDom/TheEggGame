package com.adedom.admin.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adedom.admin.R
import com.adedom.admin.ui.itemcollection.ItemCollectionActivity
import com.adedom.admin.ui.logs.LogsActivity
import com.adedom.admin.ui.player.PlayerActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        mBtPlayer.setOnClickListener {
            startActivity(Intent(baseContext, PlayerActivity::class.java))
        }

        mBtItemCollection.setOnClickListener {
            startActivity(Intent(baseContext, ItemCollectionActivity::class.java))
        }

        mBtLog.setOnClickListener {
            startActivity(Intent(baseContext, LogsActivity::class.java))
        }
    }
}