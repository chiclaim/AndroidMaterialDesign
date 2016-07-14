package com.chiclam.material.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.TextView;

import com.chiclam.material.BaseFragment;
import com.chiclam.material.R;

/**
 * Created by chiclaim on 2016/07/14
 */
public class PaletteFragment extends BaseFragment {

    TextView tvVibrant, tvVibrantLight, tvVibrantDark, tvMuted, tvMutedLight, tvMutedDark;
    TextView tvMutedSwatch, tvLightMutedSwatch, tvDarkMutedSwatch, tvVibrantSwatch,
            tvLightVibrantSwatch, tvDarkVibrantSwatch;

    @Override
    public void initViews(View view) {
        showTitleBack(view, "Palette");
        tvVibrant = (TextView) view.findViewById(R.id.tv_vibrant);
        tvVibrantLight = (TextView) view.findViewById(R.id.tv_vibrant_light);
        tvVibrantDark = (TextView) view.findViewById(R.id.tv_vibrant_dark);
        tvMuted = (TextView) view.findViewById(R.id.tv_muted);
        tvMutedLight = (TextView) view.findViewById(R.id.tv_muted_light);
        tvMutedDark = (TextView) view.findViewById(R.id.tv_muted_dark);


        tvMutedSwatch = (TextView) view.findViewById(R.id.tv_muted_swatch);
        tvLightMutedSwatch = (TextView) view.findViewById(R.id.tv_light_muted_swatch);
        tvDarkMutedSwatch = (TextView) view.findViewById(R.id.tv_dark_muted_swatch);

        tvVibrantSwatch = (TextView) view.findViewById(R.id.tv_vibrant_swatch);
        tvLightVibrantSwatch = (TextView) view.findViewById(R.id.tv_light_vibrant_swatch);
        tvDarkVibrantSwatch = (TextView) view.findViewById(R.id.tv_dark_vibrant_swatch);

        Palette.PaletteAsyncListener paletteListener = new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {

                int defaultColor = 0x000000;
                int vibrant = palette.getVibrantColor(defaultColor);
                int vibrantLight = palette.getLightVibrantColor(defaultColor);
                int vibrantDark = palette.getDarkVibrantColor(defaultColor);
                int muted = palette.getMutedColor(defaultColor);
                int mutedLight = palette.getLightMutedColor(defaultColor);
                int mutedDark = palette.getDarkMutedColor(defaultColor);

                tvVibrant.setBackgroundColor(vibrant);
                tvVibrantLight.setBackgroundColor(vibrantLight);
                tvVibrantDark.setBackgroundColor(vibrantDark);
                tvMuted.setBackgroundColor(muted);
                tvMutedLight.setBackgroundColor(mutedLight);
                tvMutedDark.setBackgroundColor(mutedDark);

                setSwatchColor(palette.getMutedSwatch(), tvMutedSwatch);
                setSwatchColor(palette.getLightMutedSwatch(), tvLightMutedSwatch);
                setSwatchColor(palette.getDarkMutedSwatch(), tvDarkMutedSwatch);

                setSwatchColor(palette.getVibrantSwatch(), tvVibrantSwatch);
                setSwatchColor(palette.getLightVibrantSwatch(), tvLightVibrantSwatch);
                setSwatchColor(palette.getDarkVibrantSwatch(), tvDarkVibrantSwatch);

            }
        };

        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.titanic);
        if (myBitmap != null && !myBitmap.isRecycled()) {
            Palette.from(myBitmap).generate(paletteListener);
        }
    }

    public void setSwatchColor(Palette.Swatch swatch, TextView textView) {
        if (swatch == null) {
            return;
        }
        int mutedRgb = swatch.getRgb();
        textView.setBackgroundColor(mutedRgb);
        textView.setTextColor(swatch.getTitleTextColor());
        textView.append(" Population:" + swatch.getPopulation());
        swatch.getBodyTextColor();//ignore
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_palette_layout;
    }


}
