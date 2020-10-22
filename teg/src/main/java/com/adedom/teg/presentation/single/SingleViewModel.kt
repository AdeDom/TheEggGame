package com.adedom.teg.presentation.single

import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import kotlinx.coroutines.launch

class SingleViewModel(
    private val repository: DefaultTegRepository,
) : BaseViewModel<SingleState>(SingleState()) {

    fun callFetchItemCollection() {
        launch {
            setState { copy(loading = true) }

            when (val resource = repository.callFetchItemCollection()) {
                is Resource.Success -> setState { copy(backpack = resource.data.backpack) }
                is Resource.Error -> setError(resource)
            }

            setState { copy(loading = false) }
        }
    }

}
