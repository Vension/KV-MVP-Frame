package com.vension.mvp.db.realm;

import com.vension.mvp.db.realm.bean.RealmLikeBean;

import java.util.List;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/10 11:36
 * 描  述：
 * ========================================================
 */

public class DataManager implements DBHelper {

    DBHelper mDbHelper;

    public DataManager(DBHelper dbHelper) {
        mDbHelper = dbHelper;
    }


    @Override
    public void insertNewsId(int id) {
        mDbHelper.insertNewsId(id);
    }

    @Override
    public boolean queryNewsId(int id) {
        return mDbHelper.queryNewsId(id);
    }

    @Override
    public void insertLikeBean(RealmLikeBean bean) {
        mDbHelper.insertLikeBean(bean);
    }

    @Override
    public void deleteLikeBean(String id) {
        mDbHelper.deleteLikeBean(id);
    }

    @Override
    public boolean queryLikeId(String id) {
        return mDbHelper.queryLikeId(id);
    }

    @Override
    public List<RealmLikeBean> getLikeList() {
        return mDbHelper.getLikeList();
    }

    @Override
    public void changeLikeTime(String id, long time, boolean isPlus) {
        mDbHelper.changeLikeTime(id, time, isPlus);
    }


}
