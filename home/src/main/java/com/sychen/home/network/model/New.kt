package com.sychen.home.network.model


import com.google.gson.annotations.SerializedName

data class New(
        @SerializedName("code")
        val code: Int = 0, // 200
        @SerializedName("data")
        val `data`: List<Data> = listOf(),
        @SerializedName("message")
        val message: String = "" // success
) {
        data class Data(
                @SerializedName("author")
                val author: String = "", // test-author
                @SerializedName("content")
                val content: String = "", // test-content
                @SerializedName("date")
                val date: String = "", // date-test
                @SerializedName("id")
                val id: Int = 0, // 141
                @SerializedName("newDescription")
                val newDescription: String = "", // test-description
                @SerializedName("newTitle")
                val newTitle: String = "", // test-title
                @SerializedName("newTitleImgUrl")
                val newTitleImgUrl: String = "", // test-titleImgUrl
                @SerializedName("sourceName")
                val sourceName: String = "", // test-sourceName
                @SerializedName("webUrl")
                val webUrl: String = "" // test-webUrl
        )
}