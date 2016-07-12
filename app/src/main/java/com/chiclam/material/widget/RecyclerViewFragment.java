package com.chiclam.material.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chiclam.material.BaseFragment;
import com.chiclam.material.R;
import com.chiclam.material.adapter.RecyclerAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by chiclaim on 2016/07/12
 */
public class RecyclerViewFragment extends BaseFragment {

    private List<String> list = Arrays.asList("GO", "C#", "VB", "C++", "Java", "Swift");

    @Override
    public void initViews(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new RecyclerAdapter(list));

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recycler_view_layout;
    }
}
