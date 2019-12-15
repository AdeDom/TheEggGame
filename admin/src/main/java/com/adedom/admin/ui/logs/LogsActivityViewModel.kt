package com.adedom.admin.ui.logs

import com.adedom.admin.util.BaseViewModel

class LogsActivityViewModel : BaseViewModel() {

    fun getLogs() = repository.getLogs()
}