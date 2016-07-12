package com.chiclam.material.widget;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chiclam.material.BaseFragment;
import com.chiclam.material.R;

/**
 * Created by chiclaim on 2016/07/11
 */
public class SwitchSnackFBARippleShadowFragment extends BaseFragment {


    SwitchCompat switchCompat;
    ViewGroup container;
    ViewGroup coordinatorLayout;

    @Override
    public void initViews(View view) {
        showTitleBack(view, "SwitchSnackFBARippleShadow");
        //switchCompat(view);
        container = (ViewGroup) view.findViewById(R.id.container);
        coordinatorLayout = (ViewGroup) view.findViewById(R.id.coordinator_layout);

        view.findViewById(R.id.btn_show_snack).setOnClickListener(this);
        view.findViewById(R.id.btn_show_snack_with_action).setOnClickListener(this);
        view.findViewById(R.id.btn_show_snack_in_coordinator).setOnClickListener(this);
    }

    private void switchCompat(View view) {
        switchCompat = (SwitchCompat) view.findViewById(R.id.switch_compat);

        //SwitchCompat被竖线隔开
        //switchCompat.setSplitTrack(false);

        //SwitchCompat右边会出现错误提示
        //switchCompat.setError("error");

        //是否显示文字[默认为 开启/关闭](当然也可以自定义文字)
        //switchCompat.setShowText(true);
        //自定义文字
        //switchCompat.setTextOff("Off");
        //switchCompat.setTextOn("On");

        //设置左边文字和右边按钮的距离
        //switchCompat.setSwitchPadding(20);

        //设置关闭和开启
        //switchCompat.setChecked(true/false);

        //监听switchCompat开启和关闭变化
        //switchCompat.setOnCheckedChangeListener();

        //设置Track图标
        //switchCompat.setTrackResource(R.mipmap.ic_back_gray);

        //switchCompat设置指示图标[但是开启和关闭都是一个图标,可以在setOnCheckedChangeListener里动态设置]
        //switchCompat.setThumbResource(R.mipmap.ic_back_gray);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_switch_snack_fab_ripple_shadow_layout;
    }


    @Override
    public void onClick(final View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_show_snack:
                Snackbar.make(container, "This is Snackbar", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.btn_show_snack_with_action:
                Snackbar.make(container, "Snackbar with action", Snackbar.LENGTH_SHORT)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(v.getContext(), "Snackbar Action pressed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                break;
            case R.id.btn_show_snack_in_coordinator:
                Snackbar.make(coordinatorLayout, "This is Snackbar", Snackbar.LENGTH_SHORT).show();
                break;
        }
    }
}
