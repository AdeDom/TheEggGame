package com.adedom.theegggame.ui.main

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.adedom.theegggame.R
import com.adedom.theegggame.util.BaseDialogFragment
import com.adedom.utility.extension.dialog
import com.adedom.utility.extension.recyclerVertical
import com.adedom.utility.extension.textChanged

class RankDialog : BaseDialogFragment<MainActivityViewModel>({R.layout.dialog_rank}) {

    private lateinit var mAdapter: RankAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mEdtSearch: EditText
    private lateinit var mBtnRank10: Button
    private lateinit var mBtnRank50: Button
    private lateinit var mBtnRank100: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        init(bView)

        fetchPlayer()

        return AlertDialog.Builder(activity!!).dialog(bView, R.drawable.ic_rank, R.string.rank)
    }

    private fun init(view: View) {
        mEdtSearch = view.findViewById(R.id.mEtSearch) as EditText
        mBtnRank10 = view.findViewById(R.id.mBtRank10) as Button
        mBtnRank50 = view.findViewById(R.id.mBtRank50) as Button
        mBtnRank100 = view.findViewById(R.id.mBtRank100) as Button
        mRecyclerView = view.findViewById(R.id.mRecyclerView) as RecyclerView

        mAdapter = RankAdapter()

        mRecyclerView.recyclerVertical { it.adapter = mAdapter }

        mEdtSearch.textChanged { fetchPlayer(it) }

        mBtnRank10.setOnClickListener { fetchPlayer(limit = "10") }
        mBtnRank50.setOnClickListener { fetchPlayer(limit = "50") }
        mBtnRank100.setOnClickListener { fetchPlayer(limit = "100") }
    }

    private fun fetchPlayer(search: String = "", limit: String = "") {
        viewModel.getPlayerRank(search, limit).observe(this, Observer {
            mAdapter.setList(it)
        })
    }
}
