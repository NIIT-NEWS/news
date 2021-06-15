package com.sychen.search.network.model


import com.google.gson.annotations.SerializedName

        data class News(
                @SerializedName("author")
                val author: String = "", // 发布者：系统管理员
                @SerializedName("content")
                val content: String = "", //   为认真贯彻落实习近平总书记在党史学习教育动员大会上的重要讲话精神，把学习党史同总结经验、观照现实、推动工作结合起来，同解决实际问题结合起来，帮助大学生在进入工作岗位之前学习了解相关法律知识，为更好地进入社会、走入职场打好基础。5月19日下午，由南京市总工会主办，市科教卫体工会联合会协办、南京工业职业技术大学和江苏天哲律师事务所承办的“与法治同行 为劳动护航——工会公益法律服务、律师巡回课堂进校园”首场活动在我校学术交流中心报告厅举行。市总工会法律工作部部长帅小平、一级主任科员陈建新，市科教卫体工会联合会一级主任科员刘璐到会指导，市总工会法律援助团李京农律师作专题报告，学校党委常委、副校长朱建国出席并主持会议，招就处、工会负责人和150余名学生参会。  今年是“八五”普法开局之年，南京市总工会始终坚持以构建和谐劳动关系为重点，把党史学习教育的成效转化为服务职工群众的动力，落实“我为群众办实事”实践活动。本次活动是工会法律服务触角的延伸，进一步拓展了工会服务对象，增强了法治宣传工作的实效性，让法治走进校园、走到师生身边，与法治同行，为劳动护航。会上，帅小平围绕工会是什么、开展律师巡回课堂进校园的目的、遇到劳动争议怎么办三个问题进行阐述和宣讲，并向到场的大学生赠送《职工维权知识100问》宣传手册。刘璐阐述了市科教卫体工会联合会正在开展的“劳模工匠”“法律服务、工会知识”进校园活动的目的和意义。南京市劳模、市首届维护职工权益“十佳律师”、江苏天哲律师事务所李京农律师对即将走进职场的大学生就《工会法》《劳动法》《劳动合同法》等相关法律法规，结合典型案例，进行了深入浅出的讲解，并不时开展互动交流，令大家受益匪浅。  朱建国代表学校对市总工会、市科教卫体工会、江苏天哲律师事务所送来的“工会公益法律服务、律师巡回课堂进校园”活动表示衷心感谢，并希望同学们认真学习相关法律法规，为将来更好地融入社会、走进职场打好坚实基础。	 	 	 	 	 	 	（招生就业处 工会 供稿）
                @SerializedName("date")
                val date: String = "", // 发布时间：2021-05-19
                @SerializedName("favorCount")
                val favorCount: Int = 0, // 0
                @SerializedName("id")
                val id: Int = 0, // 11
                @SerializedName("newTitle")
                val newTitle: String = "", // “与法治同行 为劳动护航——南京市总工会公益法律服务、律师巡回课堂进校园”首场活动在我校举行
                @SerializedName("newTitleImgUrl")
                val newTitleImgUrl: String = "", // http://www.niit.edu.cn//_upload/article/images/cc/22/d3ff74654004a968b6665d282754/92efdf95-e133-4137-986f-406effc0d8b5.jpg
                @SerializedName("sourceName")
                val sourceName: String = "", // 南京工业职业技术大学
                @SerializedName("webUrl")
                val webUrl: String = "" // http://www.niit.edu.cn//9f/39/c4062a40761/page.htm
        )