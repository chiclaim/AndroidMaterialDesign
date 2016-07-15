package com.chiclam.material;

import android.content.Intent;
import android.view.View;

import com.chiclam.material.widget.UserProfileActivity;
import com.chiclam.material.widget.WidgetsFragment;

/**
 * Created by chiclaim on 2016/07/09
 */
public class MainFragment extends BaseFragment {


    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_layout;
    }

    @Override
    public void initViews(View view) {
        view.findViewById(R.id.btn_material_widget).setOnClickListener(this);
        view.findViewById(R.id.btn_coordinator_layout).setOnClickListener(this);
        view.findViewById(R.id.btn_coordinator_twitter).setOnClickListener(this);
        view.findViewById(R.id.btn_motion).setOnClickListener(this);
    }

    //btn_measure_spec


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_material_widget:
                addFragment(new WidgetsFragment());
                break;
            case R.id.btn_coordinator_layout:
                //addFragment(new MyTextViewFragment());
                break;
            case R.id.btn_coordinator_twitter:
                startActivity(new Intent(getActivity(), UserProfileActivity.class));
                break;
            case R.id.btn_motion:
                break;

        }

    }


}