package com.chiclam.material.widget;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v8.renderscript.RenderScript;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chiclam.material.R;
import com.chiclam.material.utils.RenderScriptGaussianBlur;

import java.util.Arrays;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity implements
        AppBarLayout.OnOffsetChangedListener, ViewPager.OnPageChangeListener {


    private List<String> tabs = Arrays.asList("推文", "媒体", "喜欢");
    private List<? extends RecyclerViewFragment> fragments;

    private RenderScript rs;


    private int coverHeight;
    private int toolBarHeight;
    private int statusBarHeight;
    private Bitmap blurBitmap;
    private Animation animFromBottom;

    AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView tvLeft, tvName;
    private ImageView ivAvatar, ivCover, ivBlur;
    View viewBlurMask;

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private int getToolbarHeight() {
        TypedArray styledAttributes = getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});
        int height = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return height;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_layout);
        statusBarHeight = getStatusBarHeight();
        toolBarHeight = getToolbarHeight();
        coverHeight = getResources().getDimensionPixelSize(R.dimen.artist_profile_avatar_height);

        initViews();

    }

    protected void initViews() {
        tvLeft = (TextView) findViewById(R.id.tv_title);
        tvName = (TextView) findViewById(R.id.tv_name);
        ivAvatar = (ImageView) findViewById(R.id.iv_avatar);
        ivCover = (ImageView) findViewById(R.id.iv_avatar_cover);
        ivBlur = (ImageView) findViewById(R.id.iv_title_blur);
        viewBlurMask = findViewById(R.id.view_blur_mask);

        animFromBottom = AnimationUtils.loadAnimation(this, R.anim.slide_from_bottom);
        appBarLayout = (AppBarLayout) findViewById(R.id.main_appbar);
        appBarLayout.addOnOffsetChangedListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tl_tab_layout);
        initTabTitles();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                generateToolbarBlurImg();
            }
        }, 1000);


    }

    private void initTabTitles() {
        fragments = Arrays.asList(new RecyclerViewFragment(), new RecyclerViewFragment(),
                new RecyclerViewFragment());
        //pagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return tabs.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabs.get(position);
            }
        };

        viewPager.addOnPageChangeListener(this);

        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);


        //viewPager.setCurrentItem(1);
    }


    public RenderScript getRs() {
        if (rs == null) {
            rs = RenderScript.create(this);
        }
        return rs;
    }


    private static SpannableStringBuilder getSsb(String prefix, String suffix, int prefixColor, int suffixColor) {
        if (TextUtils.isEmpty(prefix)) {
            prefix = "0";
        }
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        SpannableString redSpannable = new SpannableString(prefix + suffix);
        int prefixLen = prefix.length();
        redSpannable.setSpan(new ForegroundColorSpan(prefixColor), 0, prefixLen, 0);
        redSpannable.setSpan(new ForegroundColorSpan(suffixColor), prefixLen, suffix.length() + prefixLen, 0);
        ssb.append(redSpannable);
        return ssb;
    }

    int currentOffset = 0;
    int avatarMaxSize;
    float avatarMinSize;

    //10   6
    //100  4

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//        android.util.Log.d("ArtistDetail", verticalOffset + "," + statusBarHeight + ","
//                + toolBarHeight + "," + appBarLayout.getHeight() + "," + coverHeight + ","
//                + appBarLayout.getTotalScrollRange());
        int abOffset = Math.abs(verticalOffset);


        if (tvLeft.getVisibility() == View.GONE) {
            if ((abOffset + statusBarHeight + toolBarHeight) >= (tvName.getTop() + tvName.getHeight())) {
                tvLeft.startAnimation(animFromBottom);
                tvLeft.setVisibility(View.VISIBLE);
            }
        } else {
            if ((abOffset + statusBarHeight + toolBarHeight) < (tvName.getTop() + tvName.getHeight())) {
                tvLeft.setVisibility(View.GONE);
            }
        }

        if (avatarMaxSize == 0) {
            avatarMaxSize = ivAvatar.getWidth();
            avatarMinSize = avatarMaxSize * 0.63f;//1-(3f/8f)
        }

        float alpha = abOffset / (float) appBarLayout.getTotalScrollRange();//796
        if (abOffset > currentOffset) {//上拉
            ViewGroup.LayoutParams params = ivAvatar.getLayoutParams();
            if (params.width > avatarMinSize) {
                params.height -= 1;
                params.width -= 1;
                ivAvatar.setLayoutParams(params);
            }
            setImageBlurByScroll(abOffset, alpha);
        } else if (abOffset == currentOffset) {//静止

        } else {//下拉
            if (checkLessThanCover(abOffset)) {
                ViewGroup.LayoutParams params = ivAvatar.getLayoutParams();
                if (params.width < avatarMaxSize) {
                    float rate = (appBarLayout.getTotalScrollRange() - abOffset) /
                            (float) appBarLayout.getTotalScrollRange();
                    params.height = (int) (avatarMaxSize * rate);
                    params.width = (int) (avatarMaxSize * rate);
                    ivAvatar.setLayoutParams(params);
                }
            }
            setImageBlurByScroll(abOffset, alpha);
        }
        currentOffset = abOffset;
    }


    boolean alreadySetBlur;

    private void setImageBlur() {
        if (!alreadySetBlur) {
            ivBlur.setImageBitmap(blurBitmap);
            ivBlur.setAlpha(1f);
            alreadySetBlur = true;
            viewBlurMask.setAlpha(0.2f);
        }
    }

    private void removeImageBlur(float alpha) {
        ivBlur.setImageBitmap(null);
        ivBlur.setAlpha(alpha);
        alreadySetBlur = false;
        viewBlurMask.setAlpha(0.0f);
    }

    private void setImageBlurByScroll(int offset, float alpha) {
        if (checkBeyond(offset)) {
            setImageBlur();
        } else if (checkOverlapWithCover(offset)) {
            setImageBlur();
        } else {
            removeImageBlur(alpha);
        }
    }


    private boolean checkBeyond(int offset) {
        return offset + statusBarHeight + toolBarHeight > coverHeight;
    }

    private boolean checkOverlapWithCover(int offset) {
        return offset + statusBarHeight + toolBarHeight == coverHeight;
    }

    private boolean checkLessThanCover(int offset) {
        return offset + statusBarHeight + toolBarHeight <= coverHeight;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        appBarLayout.removeOnOffsetChangedListener(this);
        viewPager.removeOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void generateToolbarBlurImg() {
        //final BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inSampleSize = 8 * 8;// * 8;
        //Bitmap source = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        ivCover.setDrawingCacheEnabled(true);
        Bitmap source = ivCover.getDrawingCache();
        int width = getResources().getDisplayMetrics().widthPixels;
        int y = coverHeight - toolBarHeight;
        //blurTemplate = Bitmap.createScaledBitmap(source, width, height, false);
        Bitmap blurTemplate = Bitmap.createBitmap(source, 0, y, width, toolBarHeight);
        source.recycle();
        blurBitmap = blur(getRs(), blurTemplate, 16);
    }

    public static Bitmap blur(RenderScript rs, Bitmap bitmap, int radius) {
        return new RenderScriptGaussianBlur(rs).blur(radius, bitmap);
    }
}
