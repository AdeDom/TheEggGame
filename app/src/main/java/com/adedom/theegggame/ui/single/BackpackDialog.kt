package com.adedom.theegggame.ui.single

import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.library.util.BaseDialogFragment
import com.adedom.theegggame.R
import com.adedom.theegggame.ui.main.MainActivity

class BackpackDialog : BaseDialogFragment<SingleActivityViewModel>(
    { R.layout.dialog_backpack },
    { R.drawable.ic_backpack_black },
    { R.string.backpack }
) {

    override fun initDialog(view: View) {
        super.initDialog(view)
        viewModel = ViewModelProviders.of(this).get(SingleActivityViewModel::class.java)

        val egg = view.findViewById(R.id.mTvEgg) as TextView
        val eggI = view.findViewById(R.id.mTvEggI) as TextView
        val eggII = view.findViewById(R.id.mTvEggII) as TextView
        val eggIII = view.findViewById(R.id.mTvEggIII) as TextView

        viewModel.fetchBackpack(MainActivity.sPlayer.playerId).observe(this, Observer {
            egg.text = it.egg.toString()
            eggI.text = it.eggI.toString()
            eggII.text = it.eggII.toString()
            eggIII.text = it.eggIII.toString()
        })
    }

}
