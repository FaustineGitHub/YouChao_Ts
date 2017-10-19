package com.youchao.tingshuo.ui.adapter;

import android.support.v4.app.Fragment;

import com.youchao.tingshuo.ui.fragment.MyFragment;
import com.youchao.tingshuo.ui.fragment.PiPeiFragment;
import com.youchao.tingshuo.ui.fragment.CircleFragment;
import com.youchao.tingshuo.ui.widget.fragmentnavigator.FragmentNavigatorAdapter;


/**
 * Created by Dlt on 2017/3/18 12:53
 */
public class MainFragmentAdapter implements FragmentNavigatorAdapter {

    private static final String TABS[] = {"ShouYe", "PiPei", "Circle"};

    @Override
    public Fragment onCreateFragment(int position) {

        switch (position) {
            case 0:
                return CircleFragment.newInstance(position);
            case 1:
                return PiPeiFragment.newInstance(position);
            case 2:
                return MyFragment.newInstance(position);

        }
        return null;
    }

    @Override
    public String getTag(int position) {

        switch (position) {
            case 0:
                return CircleFragment.TAG;
            case 1:
                return PiPeiFragment.TAG;
            case 2:
                return MyFragment.TAG;

        }
        return null;
    }

    @Override
    public int getCount() {
        return TABS.length;
    }
}
