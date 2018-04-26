package com.vension.mvp.beans.headline;

import java.util.List;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/19 10:40
 * 描  述：图片实体类
 * ========================================================
 */

public class ImageBean {
    /**
     * url : http://p3.pstatp.com/list/300x196/2c23000095ae9f56b15f.webp
     * width : 700
     * url_list : [{"url":"http://p3.pstatp.com/list/300x196/2c23000095ae9f56b15f.webp"},{"url":"http://pb9.pstatp.com/list/300x196/2c23000095ae9f56b15f.webp"},{"url":"http://pb1.pstatp.com/list/300x196/2c23000095ae9f56b15f.webp"}]
     * uri : list/2c23000095ae9f56b15f
     * height : 393
     */

    public String url;
    public int width;
    public String uri;
    public int height;
    public List<UrlListBeanX> url_list;

    public static class UrlListBeanX {
        /**
         * url : http://p3.pstatp.com/list/300x196/2c23000095ae9f56b15f.webp
         */

        public String url;
    }
}
