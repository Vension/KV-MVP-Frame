package com.vension.mvp.beans.gank

/**
 * Created by caik on 2017/6/1.
 */
data class GankItemBean(val _id:String, val createdAt:String, val desc:String,
                        var images:List<String>, val publishedAt:String, val source:String, val type:String, val url:String,
                        val used:Boolean, val who:String, var height: Int)