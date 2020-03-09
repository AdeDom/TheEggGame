package com.adedom.admin.ui.itemcollection

import com.adedom.admin.util.BaseViewModel
import com.adedom.admin.util.KEY_STRING

class ItemCollectionActivityViewModel : BaseViewModel() {

    fun getItemCollection(name: String, itemId: Int) = repository.getItemCollection(name, itemId)

    companion object{
        var name:String = KEY_STRING
        var itemId:Int = 0
    }
}
