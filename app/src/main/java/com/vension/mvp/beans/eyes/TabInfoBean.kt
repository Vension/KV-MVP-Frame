package com.vension.mvp.beans.eyes

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/16 16:17
 * 描  述：热门的 tabInfo
 * ========================================================
 */

data class TabInfoBean(val tabInfo: TabInfo) {
    data class TabInfo(val tabList: ArrayList<Tab>)

    data class Tab(val id: Long, val name: String, val apiUrl: String)
}
