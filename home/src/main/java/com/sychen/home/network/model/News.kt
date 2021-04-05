package com.sychen.home.network.model


import com.google.gson.annotations.SerializedName

data class News(
        @SerializedName("articles")
        val articles: List<Article> = listOf(),
        @SerializedName("status")
        val status: String = "", // ok
        @SerializedName("totalResults")
        val totalResults: Int = 0 // 20
) {
        data class Article(
                @SerializedName("author")
                val author: String = "", // Grace Segers
                @SerializedName("content")
                val content: String = "", // President Joe Biden called a sweeping elections law signed by Georgia Governor Brian Kemp this week an "atrocity," and said the Justice Department is "taking a look" at the measure.The new law incl… [+3720 chars]
                @SerializedName("description")
                val description: String = "", // Biden called a new law signed by Georgia Governor Brian Kemp this week was "an atrocity."
                @SerializedName("publishedAt")
                val publishedAt: String = "", // 2021-03-28T11:29:56Z
                @SerializedName("source")
                val source: Source = Source(),
                @SerializedName("title")
                val title: String = "", // Biden says Justice Department is "taking a look" at Georgia voting law - CBS News
                @SerializedName("url")
                val url: String = "", // https://www.cbsnews.com/news/georgia-voting-laws-biden-justice-department/
                @SerializedName("urlToImage")
                val urlToImage: String = "" // https://cbsnews1.cbsistatic.com/hub/i/r/2021/03/27/ac7acb01-5e7e-4fe2-aac0-fee72d402db9/thumbnail/1200x630/ff2e15cac594edc074a68967a55760c9/cbsn-fusion-biden-tackles-immigration-crisis-new-georgia-voting-law-thumbnail-678821-640x360.jpg
        ) {
                data class Source(
                        @SerializedName("id")
                        val id: String = "", // cbs-news
                        @SerializedName("name")
                        val name: String = "" // CBS News
                )
        }
}