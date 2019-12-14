package com.adedom.utility

import android.content.Context
import android.location.Geocoder

//region SwipeMenuListView
//fun createItem(context: Context, menu: SwipeMenu, bgColor: Int, icon: Int) {
//    val menuItem = SwipeMenuItem(context)
//    menuItem.setBackground(bgColor)
//    menuItem.width = 200
//    menuItem.setIcon(icon)
//    menu.addMenuItem(menuItem)
//}
//
//fun createSwipeMenu(context: Context, swipeMenuListView: SwipeMenuListView) {
//    val creator = SwipeMenuCreator { menu ->
//        createItem(context, menu, android.R.color.holo_orange_light, R.drawable.ic_edit_white)
//        createItem(context, menu, android.R.color.holo_red_light, R.drawable.ic_delete_white)
//    }
//    swipeMenuListView.setMenuCreator(creator)
//}

//mSwipeMenuListView.also {
//    it.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT)
//
//    it.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
//        // select
//        mSwipeMenuListView.smoothOpenMenu(i)
//    }
//    it.setOnMenuItemClickListener { position, menu, index ->
//        var t = ""
//        when (index) {
//            0 -> t = "Edit" // edit
//            1 -> t = "Delete" // delete
//        }
//        Toast.makeText(baseContext, t, Toast.LENGTH_SHORT).show()
//        true
//    }
//}
//createSwipeMenu(baseContext, mSwipeMenuListView)

//mSwipeMenuListView.adapter = PlayerAdapter(baseContext, it as ArrayList<Player>)

//<!--        <com.baoyz.swipemenulistview.SwipeMenuListView-->
//<!--                android:id="@+id/mSwipeMenuListView"-->
//<!--                android:layout_width="match_parent"-->
//<!--                android:layout_height="match_parent"-->
//<!--                android:clipToPadding="false"-->
//<!--                android:paddingBottom="100dp"-->
//<!--                tools:listitem="@layout/item_player" />-->

//class PlayerAdapter(
//    private val mContext: Context,
//    private val players: ArrayList<Player>
//) : ArrayAdapter<Player>(mContext, 0, players) {
//
//    override fun getView(
//        position: Int,
//        convertView: View?,
//        parent: ViewGroup
//    ): View {
//        var view = convertView
//        val holder: ViewHolder
//        if (view == null) {
//            val inflater = LayoutInflater.from(mContext)
//            view = inflater.inflate(R.layout.item_player, parent, false)
//            holder = ViewHolder(view)
//            view.tag = holder
//        } else {
//            holder = view.tag as ViewHolder
//        }
//
//        val player = players[position]
//        holder.tvName.text = player.name
//        holder.tvPlayerId.text = player.playerId
//        if (player.image != EMPTY) holder.ivImage.loadProfile(player.image!!)
//
//        return view!!
//    }
//
//    inner class ViewHolder(view: View) {
//        val tvName = view.findViewById(R.id.mTvName) as TextView
//        val tvPlayerId = view.findViewById(R.id.mTvPlayerId) as TextView
//        val ivImage = view.findViewById(R.id.mIvImage) as ImageView
//    }
//
//}

//endregion

fun Context.getLocality(latitude: Double, longitude: Double): String {
    val list = Geocoder(this).getFromLocation(latitude, longitude, 1)
    return if (list[0].locality != null) {
        list[0].locality
    } else {
        "unknown"
    }
}
