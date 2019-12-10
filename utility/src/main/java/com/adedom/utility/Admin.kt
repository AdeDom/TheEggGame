package com.adedom.utility

import android.content.Context
import com.baoyz.swipemenulistview.SwipeMenu
import com.baoyz.swipemenulistview.SwipeMenuCreator
import com.baoyz.swipemenulistview.SwipeMenuItem
import com.baoyz.swipemenulistview.SwipeMenuListView

fun createItem(context: Context, menu: SwipeMenu, bgColor: Int, icon: Int) {
    val menuItem = SwipeMenuItem(context)
    menuItem.setBackground(bgColor)
    menuItem.width = 200
    menuItem.setIcon(icon)
    menu.addMenuItem(menuItem)
}

fun createSwipeMenu(context: Context, swipeMenuListView: SwipeMenuListView) {
    val creator = SwipeMenuCreator { menu ->
        createItem(context, menu, android.R.color.holo_orange_light, R.drawable.ic_edit_white)
        createItem(context, menu, android.R.color.holo_red_light, R.drawable.ic_delete_white)
    }
    swipeMenuListView.setMenuCreator(creator)
}