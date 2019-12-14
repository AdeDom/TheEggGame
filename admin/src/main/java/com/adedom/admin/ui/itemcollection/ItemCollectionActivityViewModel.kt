package com.adedom.admin.ui.itemcollection

import androidx.lifecycle.ViewModel
import com.adedom.admin.data.repositories.BaseRepository

class ItemCollectionActivityViewModel(private val repository: BaseRepository) : ViewModel() {

    fun getItemCollection() = repository.getItemCollection()

}