package com.adedom.admin.ui.logs

import com.adedom.admin.util.BaseViewModel

class LogsActivityViewModel : BaseViewModel() {

    fun getLogs(dateBegin: String, timeBegin: String, dateEnd: String, timeEnd: String) =
        repository.getLogs(dateBegin, timeBegin, dateEnd, timeEnd)
}