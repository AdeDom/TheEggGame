package com.adedom.utility.extension

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adedom.utility.util.ItemDecoration

fun RecyclerView.recyclerVertical(rv: (RecyclerView) -> Unit) {
    this.apply {
        layoutManager = LinearLayoutManager(context)
        setHasFixedSize(true)
        rv.invoke(this)
    }
}

fun RecyclerView.recyclerGrid(rv: (RecyclerView) -> Unit) {
    this.apply {
        layoutManager = GridLayoutManager(context, 2)
        addItemDecoration(
            ItemDecoration(
                2,
                ItemDecoration.dpToPx(10, resources),
                true
            )
        )
        rv.invoke(this)
    }
}