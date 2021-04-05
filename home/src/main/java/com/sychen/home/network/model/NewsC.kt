package com.sychen.home.network.model


import com.google.gson.annotations.SerializedName

data class NewsC(
        @SerializedName("code")
        val code: Int = 0, // 200
        @SerializedName("data")
        val `data`: List<Data> = listOf()
) {
        data class Data(
                @SerializedName("creator")
                val creator: String = "", // 北京日报报业集团
                @SerializedName("date")
                val date: String = "", // 2021-03-16T16:00:00.000+00:00
                @SerializedName("id")
                val id: Int = 0, // 1
                @SerializedName("newInfo")
                val newInfo: String = "", // 根据近期国际市场油价变化情况，按照现行成品油价格形成机制，自2021年3月17日24时起，国内汽、柴油价格（标准品，下同）每吨分别提高235元和230元。调整后，各省（区、市）和中心城市汽、柴油最高零售价格见附表。相关价格联动及补贴政策按现行规定执行。中石油、中石化、中海油三大公司要组织好成品油生产和调运，确保市场稳定供应，严格执行国家价格政策。各地相关部门要加大市场监督检查力度，严厉查处不执行国家价格政策的行为，维护正常市场秩序。消费者可通过12315平台举报价格违法行为。
                @SerializedName("newTitle")
                val newTitle: String = "", // 油价九连涨！今天24时起，成品油每吨涨235元
                @SerializedName("newTitleImgUrl")
                val newTitleImgUrl: String = "" // http://pics2.baidu.com/feed/8c1001e93901213fdf24b463475b02d92e2e95ba.jpeg?token=67ac7544a49395385652826aea705a4b
        )
}