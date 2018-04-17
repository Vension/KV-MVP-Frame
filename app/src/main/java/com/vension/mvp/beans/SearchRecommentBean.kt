package com.vension.mvp.beans

import java.io.Serializable
/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/2 18:03
 * 描  述：搜索相关实体类
 * ========================================================
 */

/**
 * message : success
 * data : {"page_style":4,"page_font":1,"suggest_words":["郭文贵","尤果","佛","nba","面试技巧","人过五十","肝脏排毒","电动汽车","正阳门下","刘国梁马龙","疏通血管","派遣员工","人一辈子","美国航母","事业单位涨工资","7月1日","心血管疾病","牧羊曲郑绪岚","血管健康","笛子演奏"],"suggest_icon":0,"suggest_word_list":[{"type":"hist","word":"郭文贵"},{"type":"hist","word":"尤果"},{"type":"hist","word":"佛"},{"type":"hist","word":"nba"},{"type":"recom","word":"面试技巧"},{"type":"recom","word":"人过五十"},{"type":"recom","word":"肝脏排毒"},{"type":"recom","word":"电动汽车"},{"type":"recom","word":"正阳门下"},{"type":"recom","word":"刘国梁马龙"},{"type":"recom","word":"疏通血管"},{"type":"recom","word":"派遣员工"},{"type":"recom","word":"人一辈子"},{"type":"recom","word":"美国航母"},{"type":"recom","word":"事业单位涨工资"},{"type":"recom","word":"7月1日"},{"type":"recom","word":"心血管疾病"},{"type":"recom","word":"牧羊曲郑绪岚"},{"type":"recom","word":"血管健康"},{"type":"recom","word":"笛子演奏"}]}
 */

data class SearchRecommentBean(val message : String, val data: DataBean) : Serializable{

    /**
     * page_style : 4
     * page_font : 1
     * suggest_words : ["郭文贵","尤果","佛","nba","面试技巧","人过五十","肝脏排毒","电动汽车","正阳门下","刘国梁马龙","疏通血管","派遣员工","人一辈子","美国航母","事业单位涨工资","7月1日","心血管疾病","牧羊曲郑绪岚","血管健康","笛子演奏"]
     * suggest_icon : 0
     * suggest_word_list : [{"type":"hist","word":"郭文贵"},{"type":"hist","word":"尤果"},{"type":"hist","word":"佛"},{"type":"hist","word":"nba"},{"type":"recom","word":"面试技巧"},{"type":"recom","word":"人过五十"},{"type":"recom","word":"肝脏排毒"},{"type":"recom","word":"电动汽车"},{"type":"recom","word":"正阳门下"},{"type":"recom","word":"刘国梁马龙"},{"type":"recom","word":"疏通血管"},{"type":"recom","word":"派遣员工"},{"type":"recom","word":"人一辈子"},{"type":"recom","word":"美国航母"},{"type":"recom","word":"事业单位涨工资"},{"type":"recom","word":"7月1日"},{"type":"recom","word":"心血管疾病"},{"type":"recom","word":"牧羊曲郑绪岚"},{"type":"recom","word":"血管健康"},{"type":"recom","word":"笛子演奏"}]
     */
    data class DataBean(val page_style : Int,val page_font : Int,val suggest_words: List<String>,
                        val suggest_icon : Int,val suggest_word_list : List<SuggestWordListBean>) : Serializable

    /**
     * type : hist
     * word : 郭文贵
     */
    data class SuggestWordListBean(val type : String,val word : String) : Serializable
}
