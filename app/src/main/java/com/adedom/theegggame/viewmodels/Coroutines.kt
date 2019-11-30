package com.adedom.theegggame.viewmodels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

fun <T : Any> ioThenMain(work: suspend () -> T?, callback: (T?) -> Unit) =
    CoroutineScope(Dispatchers.Main).launch {
        callback(CoroutineScope(Dispatchers.IO).async rt@{
            return@rt work()
        }.await())
    }
