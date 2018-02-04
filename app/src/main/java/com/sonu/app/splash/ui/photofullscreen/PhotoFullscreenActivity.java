package com.sonu.app.splash.ui.photofullscreen;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.github.chrisbanes.photoview.PhotoView;
import com.sonu.app.splash.R;
import com.sonu.app.splash.ui.photo.Photo;
import com.sonu.app.splash.util.LogUtils;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by amanshuraikwar on 26/01/18.
 */

public class PhotoFullscreenActivity extends AppCompatActivity {

    public static final String KEY_PHOTO = "photo";
    private static final String TAG = LogUtils.getLogTag(PhotoFullscreenActivity.class);

    @BindView(R.id.photoView)
    PhotoView photoView;

    @BindView(R.id.loadingTv)
    TextView loadingTv;

    @OnClick(R.id.closeBtn)
    void onClick() {
        finishAfterTransition();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_fullscreen);

        ButterKnife.bind(this);

        final Photo photo = getIntent().getParcelableExtra(KEY_PHOTO);

        Glide.with(PhotoFullscreenActivity.this)
                .load(photo.getUrlSmall())
                .into(photoView);

        Glide.with(PhotoFullscreenActivity.this)
                .load(photo.getUrlFull())
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
