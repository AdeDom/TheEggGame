package com.adedom.admin.ui.itemcollection

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.adedom.admin.R
import com.adedom.admin.data.models.Egg
import com.adedom.admin.util.*
import com.adedom.library.extension.dialogFragment
import com.adedom.library.extension.getContent
import com.adedom.library.extension.setSpinner

class ItemCollectionDialog : BaseDialogFragment({ R.layout.dialog_item_collection }) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        init(v)
        return AlertDialog.Builder(activity!!)
            .dialogFragment(v, R.drawable.ic_egg, R.string.item_collection)
    }

    private fun init(view: View) {
        val etName = v.findViewById(R.id.etName) as EditText
        val spinner = v.findViewById(R.id.spinner) as Spinner
        val ivDateBegin = v.findViewById(R.id.ivDateBegin) as ImageView
        val ivTimeBegin = v.findViewById(R.id.ivTimeBegin) as ImageView
        val ivDateEnd = v.findViewById(R.id.ivDateEnd) as ImageView
        val ivTimeEnd = v.findViewById(R.id.ivTimeEnd) as ImageView
        val tvDateBegin = v.findViewById(R.id.tvDateBegin) as TextView
        val tvTimeBegin = v.findViewById(R.id.tvTimeBegin) as TextView
        val tvDateEnd = v.findViewById(R.id.tvDateEnd) as TextView
        val tvTimeEnd = v.findViewById(R.id.tvTimeEnd) as TextView
        val btSearch = v.findViewById(R.id.btSearch) as Button

        etName.setText(ItemCollectionActivityViewModel.name)

        spinner.setSpinner<Egg>({
            val adapter = EggAdapter(BaseActivity.sContext, dataEgg())
            it.adapter = adapter
        }, {
            ItemCollectionActivityViewModel.itemId = it.itemId
        })
        spinner.setSelection(ItemCollectionActivityViewModel.itemId.minus(1))

        btSearch.setOnClickListener {
            ItemCollectionActivityViewModel.name = etName.getContent()
            listener.onAttach()
            dialog!!.dismiss()
        }

    }

    private fun dataEgg(): ArrayList<Egg> {
        val items = ArrayList<Egg>()
        items.add(Egg(1, KEY_EGG, R.drawable.ic_egg))
        items.add(Egg(2, KEY_EGG_I, R.drawable.ic_egg_i))
        items.add(Egg(3, KEY_EGG_II, R.drawable.ic_egg_ii))
        items.add(Egg(4, KEY_EGG_III, R.drawable.ic_egg_iii))
        return items
    }
}

