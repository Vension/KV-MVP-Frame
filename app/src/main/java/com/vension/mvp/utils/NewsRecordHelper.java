package com.vension.mvp.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vension.frame.utils.VEmptyUtil;
import com.vension.mvp.beans.headline.NewsBean;
import com.vension.mvp.beans.headline.NewsRecordBean;
import org.litepal.crud.DataSupport;
import java.util.List;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/20 11:12
 * 描  述：用于获取新闻记录
 * ========================================================
 */

public class NewsRecordHelper {

    private static Gson mGson = new Gson();

    /**
     * 获取数据库保存的某个频道的最后一条记录
     *
     * @param channelCode 频道
     * @return
     */
    public static NewsRecordBean getLastNewsRecord(String channelCode) {
        return DataSupport.where("channelCode=?", channelCode)
                .findLast(NewsRecordBean.class);
    }

    /**
     * 获取某个频道上一组新闻记录
     *
     * @param channelCode 频道
     * @param page        页码
     * @return
     */
    public static NewsRecordBean getPreNewsRecord(String channelCode, int page) {
        List<NewsRecordBean> newsRecords =
                selectNewsRecords(channelCode, page - 1);

        if (VEmptyUtil.isEmpty(newsRecords)) {
            return null;
        }

        return newsRecords.get(0);
    }


    /**
     * 保存新闻记录
     *
     * @param channelCode
     * @param json
     */
    public static void save(String channelCode, String json) {
        int page = 1;
        NewsRecordBean lastNewsRecord = getLastNewsRecord(channelCode);
        if (lastNewsRecord != null) {
            //如果有记录
            page = lastNewsRecord.getPage() + 1;//页码为最后一条的页码加1
        }
        //保存新的记录
        NewsRecordBean newsRecord = new NewsRecordBean(channelCode, page, json, System.currentTimeMillis());
        newsRecord.saveOrUpdate("channelCode = ? and page = ?", channelCode, String.valueOf(page));
    }


    /**
     * 根据频道码和页码查询新闻记录
     * @param channelCode
     * @param page
     * @return
     */
    private static List<NewsRecordBean> selectNewsRecords(String channelCode, int page) {
        return DataSupport
                .where("channelCode = ? and page = ?", channelCode, String.valueOf(page))
                .find(NewsRecordBean.class);
    }

    /**
     * 将json转换成新闻集合
     *
     * @param json
     * @return
     */
    public static List<NewsBean> convertToNewsList(String json) {
        return mGson.fromJson(json, new TypeToken<List<NewsBean>>() {
        }.getType());
    }
}
