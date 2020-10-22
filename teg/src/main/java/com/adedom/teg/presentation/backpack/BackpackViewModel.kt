package com.adedom.teg.presentation.backpack

import androidx.lifecycle.LiveData
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.data.db.entities.BackpackEntity
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.presentation.usercase.BackpackUseCase
import kotlinx.coroutines.launch

class BackpackViewModel(
    private val useCase: BackpackUseCase,
    private val repository: DefaultTegRepository,
) : BaseViewModel<BackpackState>(BackpackState()) {

    val getDbBackpackLiveData: LiveData<BackpackEntity>
        get() = repository.getDbBackpackLiveData()

    fun callFetchItemCollection() {
        launch {
            if (repository.getDbBackpack() == null) {
                setState { copy(loading = true) }

                when (val resource = useCase.callFetchItemCollection()) {
                    is Resource.Error -> setError(resource)
                }

                setState { copy(loading = false) }
            }
        }
    }

}
