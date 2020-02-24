package com.adedom.theegggame.ui.main

import com.adedom.library.util.BaseDialogFragment
import com.adedom.theegggame.R

class AboutDialog : BaseDialogFragment<MainActivityViewModel>(
    { R.layout.dialog_about },
    { R.drawable.ic_h2p },
    { R.string.about }
)