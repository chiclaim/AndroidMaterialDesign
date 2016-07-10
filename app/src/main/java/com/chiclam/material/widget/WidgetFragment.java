package com.chiclam.material.widget;

import android.view.View;

import com.chiclam.material.BaseFragment;
import com.chiclam.material.R;

/**
 * Created by chiclaim on 2016/07/10
 */
public class WidgetFragment extends BaseFragment {
    @Override
    public void initViews(View view) {
        showTitleBack(view, "MaterialWidgets");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_material_widgets_layout;
    }
}
