package com.sonu.app.splash.ui.photofullscreen;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.chrisbanes.photoview.PhotoView;
import com.sonu.app.splash.R;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.util.LogUtils;

/**
 * Created by amanshuraikwar on 26/01/18.
 */

public class PhotoFullscreenActivity extends AppCompatActivity {

    public static final String KEY_PHOTO = "photo";
    private static final String TAG = LogUtils.getLogTag(PhotoFullscreenActivity.class);
    PhotoView photoView;
    TextView loadingTv;

    void onClick() {
        finishAfterTransition();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_fullscreen);

        photoView = findViewById(R.id.photoView);

        loadingTv = findViewById(R.id.loadingTv);
        findViewById(R.id.closeBtn).setOnClickListener(v -> onClick());
        final Photo photo = getIntent().getParcelableExtra(KEY_PHOTO);

        Glide.with(PhotoFullscreenActivity.this)
                .load(photo.getPhotoUrls().getSmall())
                .into(photoView);

        Glide.with(PhotoFullscreenActivity.this)
                .load(photo.getPhotoUrls().getFull())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource,
                                                Transition<? super Drawable> transition) {
                        Log.d(TAG, "onResourceReady:called");

                        loadingTv.setText("");
                        Glide.with(PhotoFullscreenActivity.this)
                                .load(resource)
                                .into(photoView);
                    }
                });
    }
}
