package com.adedom.admin.ui.logs

import com.adedom.admin.util.BaseViewModel
import com.adedom.admin.util.DATE_BEGIN
import com.adedom.admin.util.TIME_BEGIN
import com.adedom.library.util.KEY_DATE
import com.adedom.library.util.KEY_TIME
import com.adedom.library.util.getDateTime

class LogsActivityViewModel : BaseViewModel() {

    fun getLogs(dateBegin: String, timeBegin: String, dateEnd: String, timeEnd: String) =
        repository.getLogs(dateBegin, timeBegin, dateEnd, timeEnd)

    companion object{
        var dateBegin = DATE_BEGIN
        var timeBegin = TIME_BEGIN
        var dateEnd = getDateTime(KEY_DATE)
        var timeEnd = getDateTime(KEY_TIME)
    }
}
