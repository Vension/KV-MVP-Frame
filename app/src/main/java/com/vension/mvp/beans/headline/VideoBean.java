package com.vension.mvp.beans.headline;

import java.util.List;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/19 10:40
 * 描  述：视频实体类
 * ========================================================
 */

public class VideoBean {
    /**
     * group_flags : 32832
     * video_type : 0
     * video_preloading_flag : 1
     * video_url : []
     * direct_play : 1
     * detail_video_large_image : {"url":"http://p1.pstatp.com/video1609/2130000392cc3ddb8076","width":580,"url_list":[{"url":"http://p1.pstatp.com/video1609/2130000392cc3ddb8076"},{"url":"http://pb3.pstatp.com/video1609/2130000392cc3ddb8076"},{"url":"http://pb9.pstatp.com/video1609/2130000392cc3ddb8076"}],"uri":"video1609/2130000392cc3ddb8076","height":326}
     * show_pgc_subscribe : 1
     * video_third_monitor_url :
     * video_id : eb0eab0d76274b13a3fd0649ba1d0f74
     * video_watching_count : 0
     * video_watch_count : 657298
     */

    public int group_flags;
    public int video_type;
    public int video_preloading_flag;
    public int direct_play;
    public ImageBean detail_video_large_image;
    public int show_pgc_subscribe;
    public String video_third_monitor_url;
    public String video_id;
    public int video_watching_count;
    public int video_watch_count;
    public List<?> video_url;
    //自己新增的字段，记录视频播放的进度，用于同步视频列表也和详情页的进度
    public int progress;
}
