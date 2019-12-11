package com.adedom.theegggame.ui.dialogs.rank

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.adedom.theegggame.R
import com.adedom.theegggame.data.networks.PlayerApi
import com.adedom.theegggame.data.repositories.PlayerRepository
import com.adedom.utility.dialog
import com.adedom.utility.recyclerVertical
import com.adedom.utility.textChanged

class RankDialog : DialogFragment() {

    private lateinit var mViewModel: RankDialogViewModel
    private lateinit var mAdapter: RankAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mEdtSearch: EditText
    private lateinit var mBtnRank10: Button
    private lateinit var mBtnRank50: Button
    private lateinit var mBtnRank100: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        val factory = RankDialogFactory(PlayerRepository(PlayerApi()))
        mViewModel = ViewModelProviders.of(this, factory).get(RankDialogViewModel::class.java)

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_rank, null)

        init(view)

        fetchPlayer()

        return AlertDialog.Builder(activity!!).dialog(view, R.drawable.ic_rank, R.string.rank)
    }

    private fun init(view: View) {
        mEdtSearch = view.findViewById(R.id.mEdtSearch) as EditText
        mBtnRank10 = view.findViewById(R.id.mBtnRank10) as Button
        mBtnRank50 = view.findViewById(R.id.mBtnRank50) as Button
        mBtnRank100 = view.findViewById(R.id.mBtnRank100) as Button
        mRecyclerView = view.findViewById(R.id.mRecyclerView) as RecyclerView

        mAdapter = RankAdapter()

        mRecyclerView.recyclerVertical { it.adapter = mAdapter }

        mEdtSearch.textChanged { fetchPlayer(it) }

        mBtnRank10.setOnClickListener { fetchPlayer(limit = "10") }
        mBtnRank50.setOnClickListener { fetchPlayer(limit = "50") }
        mBtnRank100.setOnClickListener { fetchPlayer(limit = "100") }
    }

    private fun fetchPlayer(search: String = "", limit: String = "") {
        mViewModel.getPlayerRank(search, limit).observe(this, Observer {
            mAdapter.setList(it)
        })
    }
}
