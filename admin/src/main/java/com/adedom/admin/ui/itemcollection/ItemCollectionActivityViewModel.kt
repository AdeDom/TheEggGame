package com.adedom.admin.ui.itemcollection

import com.adedom.admin.util.BaseViewModel

class ItemCollectionActivityViewModel : BaseViewModel() {

    fun getItemCollection(name: String, itemId: Int) = repository.getItemCollection(name, itemId)

    companion object{
        var name:String = ""
        var itemId:Int = 0
    }
}
