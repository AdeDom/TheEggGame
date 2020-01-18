package com.adedom.theegggame.util

//abstract class BaseActivity<VM : ViewModel> : AppCompatActivity() {
//
//    lateinit var viewModel: VM
//    private lateinit var mHandler: Handler
//
//    companion object {
//        lateinit var sActivity: Activity
//        lateinit var sContext: Context
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        sActivity = this@BaseActivity
//        sContext = baseContext
//
//        mHandler = Handler()
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        if (item!!.itemId == android.R.id.home) onBackPressed()
//
//        return super.onOptionsItemSelected(item)
//    }
//
//    override fun onResume() {
//        super.onResume()
//
//        mRunnable.run()
//    }
//
//    override fun onPause() {
//        super.onPause()
//
//        mHandler.removeCallbacks(mRunnable)
//    }
//
//    open fun onActivityRunning() {}
//
//    override fun onBackPressed() {
//        mHandler.removeCallbacks(mRunnable)
//
//        super.onBackPressed()
//    }
//
//    private val mRunnable = object : Runnable {
//        override fun run() {
//            onActivityRunning()
//            mHandler.postDelayed(this, 5000)
//        }
//    }
//
//}
