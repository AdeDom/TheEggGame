package com.adedom.theegggame

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import com.adedom.theegggame.dialog.InsertPlayerDialog
import com.adedom.theegggame.model.PlayerItem
import com.adedom.theegggame.utility.*
import com.luseen.simplepermission.permissions.Permission
import com.luseen.simplepermission.permissions.PermissionActivity
import kotlinx.android.synthetic.main.activity_login.*
import java.sql.ResultSet

class LoginActivity : PermissionActivity() { // 14/7/62

    val TAG = "LoginActivity"
    private val mPlayerItem = arrayListOf<PlayerItem>()
    private var mIsRememberPassword = false

    companion object {
        const val MY_LOGIN = "login"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        checkConn()
        requestPermission()
        locationSetting()

        setWidgets()
        setEvents()
    }

    private fun checkConn() {
        if (MyConnect.conn() == null) {
            MyToast.showLong(baseContext, "NULL")
        } else {
            MyToast.showLong(baseContext, "OK")
        }
    }

    private fun requestPermission() {
        requestPermission(Permission.FINE_LOCATION) { permissionGranted, isPermissionDeniedForever ->
            if (!permissionGranted) {
                MyToast.showLong(baseContext, "Please grant permission")
                finish()
            }
        }
    }

    private fun locationSetting() {
        //todo check Log1
        val contentResolver = baseContext.contentResolver
        val gpsStatus = Settings.Secure.isLocationProviderEnabled(
            contentResolver,
            LocationManager.GPS_PROVIDER
        )
        if (!gpsStatus) {
            startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1234)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //todo check Log2
        val contentResolver = baseContext.contentResolver
        val gpsStatus = Settings.Secure.isLocationProviderEnabled(
            contentResolver,
            LocationManager.GPS_PROVIDER
        )
        if (!gpsStatus && requestCode === 1234) {
            MyToast.showLong(baseContext, "Please grant location")
            finish()
        }
    }

    private fun setWidgets() {
        val preferences = getSharedPreferences(MY_LOGIN, Context.MODE_PRIVATE)
        val user = preferences.getString("user", "")
        mEdtUser.setText(user)

        val password = preferences.getString("password", "")
        if (password!!.isNotEmpty()) {
            mIsRememberPassword = true
            mEdtPassword.setText(password)
            login()
        }
    }

    private fun setEvents() {
        mBtnReg.setOnClickListener {
            InsertPlayerDialog().show(supportFragmentManager, null)
        }

        mBtnLogin.setOnClickListener {
            login()
        }

        mTvForgotPassword.setOnClickListener {
            //todo forgot password
        }
    }

    private fun login() {
        // TODO: 20/05/2562 login one user only

        val sql = "SELECT * FROM tbl_player\n" +
                "WHERE user = '${mEdtUser.text.toString().trim()}' AND " +
                "password = '${mEdtPassword.text.toString().trim()}'"
        MyConnect.executeQuery(sql, object : MyResultSet {
            override fun onResponse(rs: ResultSet) {
                if (rs.next()) {
                    val item = PlayerItem(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getString(7)
                    )
                    mPlayerItem.add(item)

                    if (!mIsRememberPassword) {
                        dialogRememberPassword()
                    } else {
                        loginFinish()
                    }
                } else {
                    MyToast.showLong(baseContext, "Username & Password ไม่ถูกต้อง")
                }
            }
        })
    }

    private fun dialogRememberPassword() {
        val editor = getSharedPreferences(MY_LOGIN, Context.MODE_PRIVATE).edit()

        val builder = AlertDialog.Builder(this@LoginActivity)
        builder.setTitle("Remember password")
            .setMessage("Do you want remember password?")
            .setPositiveButton(R.string.yes) { dialog, which ->
                editor.putString("password", mEdtPassword.text.toString().trim()).commit()
                loginFinish()
            }.setNegativeButton(R.string.no) { dialog, which ->
                editor.putString("password", "").commit()
                loginFinish()
            }.show()
    }

    private fun loginFinish() {
        val editor = getSharedPreferences(MY_LOGIN, Context.MODE_PRIVATE).edit()
        editor.putString("user", mEdtUser.text.toString().trim()).commit()

        finish()
        MyToast.showShort(baseContext, "Welcome to Five Star Game.")
        for (item in mPlayerItem) {
            MyIntent().getIntent(baseContext, MainActivity::class.java, item)
        }
    }

    override fun onResume() {
        super.onResume()
        MyMediaPlayer.getMusic(baseContext, R.raw.music_main)
    }

    override fun onPause() {
        super.onPause()
        MyMediaPlayer.mMusic!!.stop()
    }
}
