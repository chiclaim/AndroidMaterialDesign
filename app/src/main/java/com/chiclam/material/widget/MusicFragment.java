package com.chiclam.material.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.chiclam.material.BaseFragment;
import com.chiclam.material.R;

/**
 * Created by chiclaim on 2016/07/12
 */
public class MusicFragment extends BaseFragment {
    boolean isPlay;
    ImageView ivPlay;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void initViews(View view) {
        showHome(view);
        ivPlay = (ImageView) view.findViewById(R.id.iv_play);
        ivPlay.setImageResource(R.mipmap.ic_play_circle_filled_black);
        isPlay = true;
        ivPlay.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_music_layout;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.music_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return super.onOptionsItemSelected(menuItem);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_play:
                if (isPlay) {
                    ivPlay.setImageResource(R.mipmap.ic_pause_circle_filled_black);
                    isPlay = false;

                } else {
                    ivPlay.setImageResource(R.mipmap.ic_play_circle_filled_black);
                    isPlay = true;
                }
                break;
        }
    }
}
