package com.youchao.tingshuo.ui.fragment.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.youchao.tingshuo.R;
import com.youchao.tingshuo.ui.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dell on 2017/9/26.
 * 个人介绍页面的资料fragment
 */

public class TabFragmentZiLiao extends BaseFragment {
    private static final String TAG = "TabFragmentZiLiao";
    @Bind(R.id.ziliao_nickname)
    TextView mZiliaoNickname;
    @Bind(R.id.ziliao_sex)
    TextView mZiliaoSex;
    @Bind(R.id.ziliao_birthday)
    TextView mZiliaoBirthday;
    @Bind(R.id.ziliao_address)
    TextView mZiliaoAddress;
    @Bind(R.id.ziliao_gexingqianming)
    TextView mZiliaoGexingqianming;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_introduce_ziliao, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
