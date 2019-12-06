package com.adedom.theegggame.util

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.adedom.theegggame.data.networks.RetrofitClient
import com.adedom.utility.PLAYER_ID
import com.adedom.utility.Setting
import com.adedom.utility.failed
import com.adedom.utility.getPrefLogin
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

abstract class BaseActivity : AppCompatActivity() {

    val TAG = "BaseActivity"
    private lateinit var mViewModel: BaseActivityViewModel
    private var mPlayerId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = BaseActivityFactory(BaseRepository(BaseApi()))
        mViewModel = ViewModelProviders.of(this, factory).get(BaseActivityViewModel::class.java)

        sActivity = this@BaseActivity
        sContext = baseContext

        Setting(sActivity, sContext)
    }

    override fun onResume() {
        super.onResume()
        Setting.locationListener(this, true)

        // todo music
        mPlayerId = this.getPrefLogin(PLAYER_ID)
        mViewModel.setState(mPlayerId!!, "online").observe(this, Observer {
            if (it.result == "failed") baseContext.failed()
        })
    }

    override fun onPause() {
        super.onPause()
        Setting.locationListener(this, false)

        // music

        mViewModel.setState(mPlayerId!!, "offline").observe(this, Observer {
            if (it.result == "failed") baseContext.failed()
        })
    }

    companion object {
        lateinit var sActivity: Activity
        lateinit var sContext: Context
    }
}

class BaseActivityViewModel(private val repository: BaseRepository) : ViewModel() {
    fun setState(playerId: String, state: String): LiveData<BaseEntity> {
        return repository.setState(playerId, state)
    }
}

@Suppress("UNCHECKED_CAST")
class BaseActivityFactory(private val repository: BaseRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BaseActivityViewModel(repository) as T
    }
}

class BaseRepository(private val api: BaseApi) {
    fun setState(playerId: String, state: String): LiveData<BaseEntity> {
        val liveData = MutableLiveData<BaseEntity>()
        api.setState(playerId, state)
            .enqueue(object : Callback<BaseEntity> {
                override fun onFailure(call: Call<BaseEntity>, t: Throwable) {}
                override fun onResponse(
                    call: Call<BaseEntity>,
                    response: Response<BaseEntity>
                ) {
                    if (!response.isSuccessful) return
                    liveData.value = response.body()
                }
            })
        return liveData
    }
}

interface BaseApi {
    @FormUrlEncoded
    @POST("set-state.php")
    fun setState(
        @Field("values1") playerId: String,
        @Field("values2") state: String
    ): Call<BaseEntity>

    companion object {
        operator fun invoke(): BaseApi {
            return RetrofitClient.instance()
                .create(BaseApi::class.java)
        }
    }
}

data class BaseEntity(@SerializedName("result") val result: String? = null)
