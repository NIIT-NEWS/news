package com.sychen.home.network.model


import com.google.gson.annotations.SerializedName

class Location : ArrayList<Location.LocationItem>(){
        data class LocationItem(
                @SerializedName("lat")
                val lat: String = "", // 100
                @SerializedName("lon")
                val lon: String = "", // 30
                @SerializedName("time")
                val time: String = "", // 2021-3-30
                @SerializedName("uid")
                val uid: Int = 0 // 1
        )
}