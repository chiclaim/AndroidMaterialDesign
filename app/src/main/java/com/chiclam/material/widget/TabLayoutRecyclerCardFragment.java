package com.chiclam.material.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.chiclam.material.BaseFragment;
import com.chiclam.material.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by chiclaim on 2016/07/12
 */
public class TabLayoutRecyclerCardFragment extends BaseFragment {

    //TabLayout的tabContentStart属性只针对scrollable有效
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void initViews(View view) {
        showTitleBack(view, "TabLayoutRecyclerCard");
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);

        viewPager.setAdapter(new ViewPagerAdapter(
                Arrays.asList("Tab1", "Tab2", "Tab3", "Tab4", "Tab5", "Tab6"),
                Arrays.asList(new RecyclerViewFragment(), new RecyclerViewFragment(),
                        new RecyclerViewFragment(), new RecyclerViewFragment(),
                        new RecyclerViewFragment(), new RecyclerViewFragment()
                )));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tab_layout_recycler_card;
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private List<String> list;
        private List<? extends Fragment> fs;

        public ViewPagerAdapter(List<String> list, List<? extends Fragment> fs) {
            super(getChildFragmentManager());
            this.list = list;
            this.fs = fs;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fs.get(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tab_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.fixed:
                tabLayout.setTabMode(TabLayout.MODE_FIXED);
                break;
            case R.id.scroll:
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
