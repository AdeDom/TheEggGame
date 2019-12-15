package com.adedom.admin.ui.itemcollection

import com.adedom.admin.util.BaseViewModel

class ItemCollectionActivityViewModel : BaseViewModel() {

    fun getItemCollection() = repository.getItemCollection()
}