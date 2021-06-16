package com.sychen.home.network.model


import com.google.gson.annotations.SerializedName

data class NiitNews(
        @SerializedName("endRow")
        val endRow: Int = 0, // 10
        @SerializedName("hasNextPage")
        val hasNextPage: Boolean = false, // false
        @SerializedName("hasPreviousPage")
        val hasPreviousPage: Boolean = false, // false
        @SerializedName("isFirstPage")
        val isFirstPage: Boolean = false, // true
        @SerializedName("isLastPage")
        val isLastPage: Boolean = false, // true
        @SerializedName("list")
        val list: List<News> = listOf(),
        @SerializedName("navigateFirstPage")
        val navigateFirstPage: Int = 0, // 1
        @SerializedName("navigateLastPage")
        val navigateLastPage: Int = 0, // 1
        @SerializedName("navigatePages")
        val navigatePages: Int = 0, // 8
        @SerializedName("navigatepageNums")
        val navigatepageNums: List<Int> = listOf(),
        @SerializedName("nextPage")
        val nextPage: Int = 0, // 0
        @SerializedName("pageNum")
        val pageNum: Int = 0, // 1
        @SerializedName("pageSize")
        val pageSize: Int = 0, // 10
        @SerializedName("pages")
        val pages: Int = 0, // 1
        @SerializedName("prePage")
        val prePage: Int = 0, // 0
        @SerializedName("size")
        val size: Int = 0, // 10
        @SerializedName("startRow")
        val startRow: Int = 0, // 1
        @SerializedName("total")
        val total: Int = 0 // -1
) {
        data class News(
                @SerializedName("author")
                val author: String = "", // 电气工程学院
                @SerializedName("content")
                val content: String = "", //   为落实江苏省高校协同创新计划实施的相关要求，做好江苏高校协同创新第三建设期（2021-2024年）发展规划编制工作，6月10日下午，江苏风力发电工程技术中心第三建设期发展规划编制研讨及论证会在我校顺利召开。会议邀请了来自东南大学、河海大学、南京邮电大学、南京信息工程大学、江苏省可再生能源行业协会、金风科技有限公司、南京水利科学研究院、中国电力科学研究院、华能江苏清洁能源分公司、中国船级社质量认证公司的11位专家，校党委常委、副校长孙雁飞，国有资产管理处、实验室建设与管理处、科技处、发展合作处、电气工程学院相关负责人及骨干教师参加了此次发展规划编制研讨及论证会。会议由专家组组长、河海大学力学与材料学院蔡新院长主持。  会上，孙雁飞副校长介绍了学校的办学历史、办学特点和取得的成绩，并对各位专家的到来表示诚挚的欢迎和衷心的感谢。电气工程学院副院长刘颖汇报了江苏风力发电工程技术中心第三建设期发展规划编制的工作情况，重点介绍了协同创新中心的体制机制改革与创新、组织结构建设、运行模式，以及三期建设过程中面临的挑战。与会的各位领导专家对江苏风力发电工程技术中心第三建设期发展规划进行了认真的讨论，重点对中心经过两个周期的建设，面对新的发展格局和新的发展理念所承担的任务和具体任务指标进行了详细的分析，并对规划中的管理体制、科研方向和协同单位的协同机制给出了宝贵的建议。最后，形成并通过了江苏风力发电工程技术中心第三建设期发展规划论证意见。  江苏风力发电工程技术中心由南京工业职业技术大学牵头，已顺利完成两期建设。此次发展规划编制研讨及论证会将有效促进风力发电工程技术中心进一步发展，为将中心建成集原始创新、技术应用、产业转化和人才培养为一体的江苏一流、国内领先的工程技术中心奠定良好基础。	 	（电气工程学院 供稿）
                @SerializedName("date")
                val date: String = "", // 2021-06-11
                @SerializedName("favorCount")
                val favorCount: Int = 0, // 0
                @SerializedName("id")
                val id: Int = 0, // 144
                @SerializedName("newTitle")
                val newTitle: String = "", // 江苏风力发电工程技术中心第三建设期发展规划编制研讨及论证会在我校顺利召开
                @SerializedName("newTitleImgUrl")
                val newTitleImgUrl: String = "", // http://news.niit.edu.cn/_upload/article/images/37/7c/50b1ccc642fd81593d5acd711e66/4bf499af-2a64-4e60-9260-aeb10146330f.jpg
                @SerializedName("sourceName")
                val sourceName: String = "", // 南京工业职业技术大学
                @SerializedName("type")
                val type: Int = 0, // 0
                @SerializedName("webUrl")
                val webUrl: String = "" // http://news.niit.edu.cn/a2/43/c3999a41539/page.htm
        )
}