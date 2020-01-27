package com.adedom.theegggame

import androidx.lifecycle.Observer
import com.adedom.theegggame.ui.single.SingleActivityViewModel
import org.junit.Test

class SingleUnitTest {

    val single = SingleActivityViewModel()

    @Test
    fun testTitleItem() {
        val title = single.titleItem(4)

        println(title)
    }

    @Test
    fun testDetailItem() {
        val detail = single.detailItem(5, 0)

        println(detail)
    }

//    @Test
//    fun testKeepItemSingle() {
//        single.keepItemSingle("1", 2, 3, 4.5, 6.7)
//    }

}
