package com.adedom.theegggame.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adedom.theegggame.R
import com.adedom.theegggame.ui.adapters.RankAdapter
import com.adedom.theegggame.ui.viewmodels.RankDialogViewModel

class RankDialog : DialogFragment() { // 2/12/19

    val TAG = "RankDialog"
    private lateinit var mViewModel: RankDialogViewModel
    private lateinit var mAdapter: RankAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mEdtSearch: EditText
    private lateinit var mBtnRank10: Button
    private lateinit var mBtnRank50: Button
    private lateinit var mBtnRank100: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        mViewModel = ViewModelProviders.of(this).get(RankDialogViewModel::class.java)

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_rank, null)

        val builder = AlertDialog.Builder(activity!!)
            .setView(view)

        init(view)

        freshPlayer()

        return builder.create()
    }

    private fun init(view: View) {
        mEdtSearch = view.findViewById(R.id.mEdtSearch) as EditText
        mBtnRank10 = view.findViewById(R.id.mBtnRank10) as Button
        mBtnRank50 = view.findViewById(R.id.mBtnRank50) as Button
        mBtnRank100 = view.findViewById(R.id.mBtnRank100) as Button
        mRecyclerView = view.findViewById(R.id.mRecyclerView) as RecyclerView

        mAdapter = RankAdapter()

        mRecyclerView.also {
            it.layoutManager = LinearLayoutManager(context)
            it.setHasFixedSize(true)
            it.adapter = mAdapter
        }

        mEdtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                freshPlayer(text.toString())
            }
        })

        mBtnRank10.setOnClickListener { freshPlayer(limit = "10") }
        mBtnRank50.setOnClickListener { freshPlayer(limit = "50") }
        mBtnRank100.setOnClickListener { freshPlayer(limit = "100") }
    }

    private fun freshPlayer(search: String = "", limit: String = "") {
        mViewModel.repository.getPlayerRank(search, limit)
        mViewModel.getPlayerRank().observe(this, Observer {
            mAdapter.setList(it)
        })
    }
}
