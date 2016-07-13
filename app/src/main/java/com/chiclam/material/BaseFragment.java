package com.chiclam.material;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by chiclaim on 2016/07/09
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }


    public abstract void initViews(View view);

    public abstract int getLayoutId();


    public final void addFragment(Fragment fragment) {
        String tag = getActivity().getClass().toString();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .replace(android.R.id.content, fragment, tag)
                .commit();
    }

    protected void showTitleBack(View view, String title) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        if (toolbar != null) {
            //toolbar.setNavigationIcon(R.mipmap.ic_back_white);
            //or
            //change gray icon to white
            Drawable icon = getResources().getDrawable(R.mipmap.ic_back_gray);
            Drawable drawable = getTintedDrawable(icon, 0xffffffff);
            toolbar.setNavigationIcon(drawable);
            if (title != null) {
                toolbar.setTitle(title);
            }
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

    }

    protected void showHome(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        if (toolbar != null) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            getFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public Drawable getTintedDrawable(@NonNull Drawable inputDrawable, @ColorInt int color) {
        Drawable wrapDrawable = DrawableCompat.wrap(inputDrawable);
        DrawableCompat.setTint(wrapDrawable, color);
        DrawableCompat.setTintMode(wrapDrawable, PorterDuff.Mode.SRC_IN);
        return wrapDrawable;
    }

    protected void finish() {
        getFragmentManager().popBackStack();
    }

    @Override
    public void onClick(View v) {

    }
}