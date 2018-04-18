package com.vension.mvp.beans.eyes

import java.io.Serializable

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/12 17:22
 * 描  述：分类 Bean
 * ========================================================
 */

data class CategoryBean(val id: Long, val name: String, val description: String, val bgPicture: String, val bgColor: String, val headerImage: String) : Serializable
