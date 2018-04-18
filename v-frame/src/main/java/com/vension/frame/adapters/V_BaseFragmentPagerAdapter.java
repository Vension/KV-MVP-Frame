package com.vension.frame.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/8 14:08
 * 描  述：FragmentPagerAdapter的公共类
 * ========================================================
 */

public class V_BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();     //fragment列表
    private List<String> titles = new ArrayList<>();          //fragment列表

    public V_BaseFragmentPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    public V_BaseFragmentPagerAdapter(FragmentManager fm, List<Fragment> mFragment) {
        super(fm);
        this.fragments = mFragment;
    }

    /**
     * 接收首页传递的标题
     */
    public V_BaseFragmentPagerAdapter(FragmentManager fm, List<Fragment> mFragment, List<String> mTitleList) {
        super(fm);
        this.fragments = mFragment;
        this.titles = mTitleList;
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment) fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null && titles.size() > 0){
            return titles.get(position % titles.size());
        }
        return null;
    }

    public void addFragmentList(List<Fragment> fragment) {
        this.fragments.clear();
        this.fragments = null;
        this.fragments = fragment;
        notifyDataSetChanged();
    }

    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }
    public void addTitle(String str) {titles.add(str);}

}
