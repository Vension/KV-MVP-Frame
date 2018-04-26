package com.vension.mvp.ui.adapters.headline;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.vension.mvp.beans.headline.ChannelBean;

import java.util.ArrayList;
import java.util.List;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/19 9:51
 * 描  述：频道的adapter
 * ========================================================
 */

public class ChannelPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;
    private List<ChannelBean> mChannels;

    public ChannelPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<ChannelBean> channelList) {
        super(fm);
        mFragments = fragmentList != null ? fragmentList : new ArrayList<>();
        mChannels = channelList != null ? channelList : new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mChannels.get(position).title;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
