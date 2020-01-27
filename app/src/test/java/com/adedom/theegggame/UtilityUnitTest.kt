package com.adedom.theegggame

import com.adedom.library.util.KEY_EMPTY
import com.adedom.theegggame.util.KEY_FEMALE
import com.adedom.theegggame.util.getLevel
import com.adedom.theegggame.util.rndLatLng
import com.adedom.theegggame.util.setImageProfile
import com.google.android.gms.maps.model.LatLng
import org.junit.Test

class UtilityUnitTest {

    @Test
    fun testRndLatlng() {
        val (lat, lng) = rndLatLng(LatLng(13.6021314, 100.6978833))
        println(">>$lat , $lng")
    }

    @Test
    fun testLevel() {
        val level = getLevel(25)

        println(level)
    }

    @Test
    fun testImageProfile(){
        setImageProfile(KEY_EMPTY, KEY_FEMALE,{
            print("1")
        },{
            print("2")
        },{
            print("3")
        })
    }

    @Test
    fun testImageViewProfile(){
//        setImageProfile(ImageView(context),"","")
    }

}

