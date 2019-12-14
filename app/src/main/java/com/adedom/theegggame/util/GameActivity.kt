package com.adedom.theegggame.util

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.adedom.utility.*
import com.adedom.utility.data.*
import com.adedom.utility.util.Setting
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

abstract class GameActivity : AppCompatActivity() {

    val TAG = "GameActivity"
    private lateinit var mViewModel: GameActivityViewModel
    var playerId: String? = null
    private val mHandlerFetch = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = GameActivityFactory(GameRepository(GameApi()))
        mViewModel = ViewModelProviders.of(this, factory).get(GameActivityViewModel::class.java)

        sActivity = this@GameActivity
        sContext = baseContext

        Setting(sActivity, sContext)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        Setting.locationListener(this, true)

        // todo music

        playerId = this.getPrefLogin(PLAYER_ID)
        mViewModel.setState(playerId!!, ONLINE).observe(this, Observer {
            if (it.result == FAILED) baseContext.failed()
        })

        mRunnableFetch.run()
    }

    override fun onPause() {
        super.onPause()
        Setting.locationListener(this, false)

        // music

        mHandlerFetch.removeCallbacks(mRunnableFetch)

        mViewModel.setState(playerId!!, OFFLINE).observe(this, Observer {
            if (it.result == FAILED) baseContext.failed()
        })

        mViewModel.updateLogs(randomKey, getDateTime(DATE), getDateTime(TIME))
            .observe(this, Observer {
                if (it.result == FAILED) baseContext.failed()
            })
    }

    open fun gameLoop() {}

    override fun onBackPressed() {
        mHandlerFetch.removeCallbacks(mRunnableFetch)
        super.onBackPressed()
    }

    private val mRunnableFetch = object : Runnable {
        override fun run() {
            gameLoop()
            mHandlerFetch.postDelayed(this, 5000)
        }
    }

    companion object {
        lateinit var sActivity: Activity
        lateinit var sContext: Context
    }
}

class GameActivityViewModel(private val repository: GameRepository) : ViewModel() {
    fun setState(playerId: String, state: String) = repository.setState(playerId, state)

    fun updateLogs(randomKey: String, dateOut: String, timeOut: String) =
        repository.updateLogs(randomKey, dateOut, timeOut)
}

@Suppress("UNCHECKED_CAST")
class GameActivityFactory(private val repository: GameRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GameActivityViewModel(repository) as T
    }
}

class GameRepository(private val api: GameApi) : ApiRequest() {
    fun setState(playerId: String, state: String) = apiRequest { api.setState(playerId, state) }

    fun updateLogs(randomKey: String, dateOut: String, timeOut: String) =
        apiRequest { api.updateLogs(randomKey, dateOut, timeOut) }
}

interface GameApi {
    @FormUrlEncoded
    @POST("set-state.php")
    fun setState(
        @Field(VALUES1) playerId: String,
        @Field(VALUES2) state: String
    ): Call<GameEntity>

    @FormUrlEncoded
    @POST("update-logs.php")
    fun updateLogs(
        @Field(VALUES1) randomKey: String,
        @Field(VALUES2) dateOut: String,
        @Field(VALUES3) timeOut: String
    ): Call<GameEntity>

    companion object {
        operator fun invoke(): GameApi {
            return RetrofitClient.instance()
                .create(GameApi::class.java)
        }
    }
}

data class GameEntity(@SerializedName(RESULT) val result: String? = null)
