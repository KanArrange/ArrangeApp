package bilibili.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.arrange.R;

import bilibili.fragment.HomeAttentionFragment;
import bilibili.fragment.HomeBangumiFragment;
import bilibili.fragment.HomeDiscoverFragment;
import bilibili.fragment.HomeLiveFragment;
import bilibili.fragment.HomeRecommendedFragment;
import bilibili.fragment.HomeRegionFragment;


/**
 * Created by kan212 on 17/9/19.
 */

public class HomePagerAdapter extends FragmentStatePagerAdapter {

    private String[] TITLES;
    private Fragment[] fragments;

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public HomePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        TITLES = context.getResources().getStringArray(R.array.sections);
        fragments = new Fragment[TITLES.length];
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments[position] == null) {
            switch (position) {
                case 0:
                    fragments[position] = HomeLiveFragment.newInstance();
                    break;
                case 1:
                    fragments[position] = HomeRecommendedFragment.newInstance();
                    break;
                case 2:
                    fragments[position] = HomeBangumiFragment.newInstance();
                    break;
                case 3:
                    fragments[position] = HomeRegionFragment.newInstance();
                    break;
                case 4:
                    fragments[position] = HomeAttentionFragment.newInstance();
                    break;
                case 5:
                    fragments[position] = HomeDiscoverFragment.newInstance();
                    break;
                default:
                    break;
            }
        }
        return fragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }
}
