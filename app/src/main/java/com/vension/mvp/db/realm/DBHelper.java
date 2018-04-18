package com.vension.mvp.db.realm;

import com.vension.mvp.db.realm.bean.RealmLikeBean;

import java.util.List;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/10 10:04
 * 描  述：
 * ========================================================
 */

public interface DBHelper {

    void insertNewsId(int id);

    /**
     * 查询 阅读记录
     * @param id
     * @return
     */
    boolean queryNewsId(int id);

    /**
     * 增加 收藏记录
     * @param bean
     */
    void insertLikeBean(RealmLikeBean bean);

    /**
     * 删除 收藏记录
     * @param id
     */
    void deleteLikeBean(String id);

    /**
     * 查询 收藏记录
     * @param id
     * @return
     */
    boolean queryLikeId(String id);

    List<RealmLikeBean> getLikeList();

    /**
     * 修改 收藏记录 时间戳以重新排序
     * @param id
     * @param time
     * @param isPlus
     */
    void changeLikeTime(String id, long time, boolean isPlus);

}
