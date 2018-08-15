package com.verbosetech.yoohoo.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.verbosetech.yoohoo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mayank on 10/5/17.
 */

public class ImageViewerActivity extends AppCompatActivity {

    private static final String IMAGE_URL = ImageViewerActivity.class.getPackage().getName() + ".image_url";

    public static Intent newInstance(Context context, String imageUrl) {
        Intent intent = new Intent(context, ImageViewerActivity.class);
        intent.putExtra(IMAGE_URL, imageUrl);
        return intent;
    }

    @BindView(R.id.photo_view)
    PhotoView photoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        ButterKnife.bind(this);

        String imageUrl = getIntent().getStringExtra(IMAGE_URL);
        if (!TextUtils.isEmpty(imageUrl)) Glide.with(this).load(imageUrl).into(photoView);
    }
}
