package com.adedom.theegggame

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import com.adedom.theegggame.dialog.AboutDialog
import com.adedom.theegggame.dialog.MissionDialog
import com.adedom.theegggame.dialog.RankDialog
import com.adedom.theegggame.dialog.SettingDialog
import com.adedom.theegggame.model.PlayerBean
import com.adedom.theegggame.multi.RoomActivity
import com.adedom.theegggame.single.SingleActivity
import com.adedom.theegggame.utility.MyGlide
import com.adedom.theegggame.utility.MyIntent
import com.adedom.theegggame.utility.MyMediaPlayer
import com.adedom.utility.MyLibrary
import com.adedom.utility.Pathiphon
import com.luseen.simplepermission.permissions.Permission
import com.luseen.simplepermission.permissions.PermissionActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : PermissionActivity() { // 03/11/19

    private lateinit var mPlayerId: String
    private var mCountExit = 0
    private lateinit var mLocationSwitchStateReceiver: BroadcastReceiver

    companion object {
        const val MY_LOGIN = "login"
        lateinit var activity: Activity
        lateinit var context: Context
        lateinit var mPlayerItem: PlayerBean
        val mTimeStamp = System.currentTimeMillis() / 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity = this@MainActivity
        context = baseContext
        if (checkLogin()) return

        requestPermission()
        locationListener()
        locationSetting()

        setEvents()
    }

    private fun checkLogin(): Boolean {
        mPlayerId = getSharedPreferences(MY_LOGIN, MODE_PRIVATE)
            .getString("player_id", "empty")!!
        if (mPlayerId == "empty") {
            startActivity(
                Intent(baseContext, LoginActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
            finishAffinity()
            return true
        } else {
            Pathiphon.call("sp_get_player_by_player_id")
                .parameter(mPlayerId)
                .commitQuery {
                    if (it.next()) {
                        val player = PlayerBean(
                            it.getString(1),
                            it.getString(2),
                            it.getString(3),
                            it.getString(4),
                            it.getInt(5),
                            it.getString(6)
                        )
                        mPlayerItem = player

                        MyLibrary.with(baseContext).showShort(R.string.welcome)
                        setWidgets()
                    } else {
                        getSharedPreferences(MY_LOGIN, Context.MODE_PRIVATE).edit()
                            .putString("player_id", "empty")
                            .apply()
                        startActivity(
                            Intent(baseContext, LoginActivity::class.java)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        )
                        finishAffinity()
                    }
                }
        }
        return false
    }

    private fun setWidgets() {
        if (mPlayerItem.image != "empty") {
            MyGlide.getGlide(baseContext, mImgProfile, mPlayerItem.image)
        }

        mTvName.text = mPlayerItem.name
        mTvLevel.text = "Level : ${mPlayerItem.level}"
    }

    private fun setEvents() {
        mBtnSingle.setOnClickListener {
            MyIntent().getIntent(baseContext, SingleActivity::class.java)
        }
        mBtnMulti.setOnClickListener { MyIntent().getIntent(baseContext, RoomActivity::class.java) }
        mImgMission.setOnClickListener { MissionDialog().show(supportFragmentManager, null) }
        mImgRank.setOnClickListener { RankDialog().show(supportFragmentManager, null) }
        mImgAbout.setOnClickListener { AboutDialog().show(supportFragmentManager, null) }
        mImgSetting.setOnClickListener { SettingDialog().show(supportFragmentManager, null) }
    }

    override fun onBackPressed() {
        if (mCountExit > 0) {
            finishAffinity()
        }
        mCountExit++
        Handler().postDelayed({ mCountExit = 0 }, 2000)
        MyLibrary.with(baseContext).showShort(R.string.on_back_pressed)
    }

    override fun onResume() {
        super.onResume()
        locationListener(true)
        MyMediaPlayer.getMusic(baseContext, R.raw.music_main)
    }

    override fun onPause() {
        super.onPause()
        locationListener(false)
        MyMediaPlayer.music!!.stop()
    }

    //region setApp
    private fun requestPermission() {
        requestPermission(Permission.FINE_LOCATION) { permissionGranted, isPermissionDeniedForever ->
            if (!permissionGranted) {
                MyLibrary.with(baseContext).showLong(R.string.grant_permission)
                finishAffinity()
            }
        }
    }

    private fun locationListener() {
        mLocationSwitchStateReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (LocationManager.PROVIDERS_CHANGED_ACTION == intent.action) {
                    val locationManager =
                        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                    val isGpsEnabled =
                        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) //NETWORK_PROVIDER

                    if (!isGpsEnabled) {
                        locationSetting()
                    }
                }
            }
        }
    }

    private fun locationListener(boolean: Boolean) {
        if (boolean) {
            val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
            filter.addAction(Intent.ACTION_PROVIDER_CHANGED)
            registerReceiver(mLocationSwitchStateReceiver, filter)
        } else {
            unregisterReceiver(mLocationSwitchStateReceiver)
        }
    }

    private fun locationSetting() {
        if (!locationSetting(baseContext)) {
            startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1234)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (!locationSetting(baseContext) && requestCode == 1234) {
            MyLibrary.with(baseContext).showLong(R.string.please_grant_location)
            finishAffinity()
        }
    }

    fun locationSetting(context: Context): Boolean {
        val contentResolver = context.contentResolver
        return Settings.Secure.isLocationProviderEnabled(
            contentResolver,
            LocationManager.GPS_PROVIDER
        )
    }
    //endregion
}
