package com.sonu.app.splash.ui.collectiondecription;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.sonu.app.splash.R;
import com.sonu.app.splash.ui.architecture.BaseActivity;
import com.sonu.app.splash.model.unsplash.Collection;
import com.sonu.app.splash.ui.content.collectionphotos.CollectionPhotosFragment;
import com.sonu.app.splash.ui.home.ViewPagerAdapter;
import com.sonu.app.splash.ui.search.allsearch.AllSearchActivity;
import com.sonu.app.splash.ui.userdescription.UserDescriptionActivity;
import com.sonu.app.splash.ui.widget.NestedScrollAppBarLayout;
import com.sonu.app.splash.ui.widget.SwipeBackCoordinatorLayout;
import com.sonu.app.splash.util.ColorHelper;
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by amanshuraikwar on 04/02/18.
 */

public class CollectionDescriptionActivity
        extends BaseActivity<CollectionDescriptionContract.Presenter>
        implements CollectionDescriptionContract.View {

    public static final String KEY_COLLECTION = "collection";

    private static final String TAG = LogUtils.getLogTag(CollectionDescriptionActivity.class);

    @BindView(R.id.parent)
    SwipeBackCoordinatorLayout parent;

    @BindView(R.id.appBar)
    NestedScrollAppBarLayout appBar;

    @BindView(R.id.coverPhotoIv)
    ImageView coverPhotoIv;

    @BindView(R.id.collectionTitleTv)
    TextView collectionTitleTv;

    @BindView(R.id.tagsParentLl)
    LinearLayout tagsParentLl;

    @BindView(R.id.userPicIv)
    ImageView userPicIv;

    @BindView(R.id.artistPicCv)
    CardView artistPicCv;

    @BindView(R.id.artistNameTv)
    TextView artistNameTv;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.addToFavFab)
    FloatingActionButton addToFavFab;

    private ViewPagerAdapter adapter;

    @Inject
    CollectionPhotosFragment collectionPhotosFragment;

    private SwipeBackCoordinatorLayout.OnSwipeListener onSwipeListener =
            new SwipeBackCoordinatorLayout.OnSwipeListener() {
                @Override
                public boolean canSwipeBack(int dir) {
                    return dir == SwipeBackCoordinatorLayout.UP_DIR || appBar.getY() >= 0;
                }

                @Override
                public void onSwipeProcess(float percent) {

                }

                @Override
                public void onSwipeFinish(int dir) {
                    finishAfterTransition();
                }
            };

    private View.OnClickListener tagOnClickListener =
            view ->
                    startAllSearchActivity(
                            ((TextView) ((CardView) view).getChildAt(0)).getText().toString());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_collection_description);

        ButterKnife.bind(this);

        updateUi(getIntent().getParcelableExtra(KEY_COLLECTION));

        parent.setOnSwipeListener(onSwipeListener);

        artistPicCv.setOnClickListener(
                view ->
                        startUserDescriptionActivity(
                                getIntent().getParcelableExtra(KEY_COLLECTION), userPicIv));

        addToFavFab.setOnClickListener(view -> getPresenter().onAddToFavClick());
    }

    private void updateUi(Collection collection) {

        collectionTitleTv.setText(collection.getTitle());

        if (collection.getCoverPhoto() !=  null) {

            Glide.with(this)
                    .load(collection.getCoverPhoto().getPhotoUrls().getRegular())
                    .apply(new RequestOptions().centerCrop())
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(coverPhotoIv);

            int color = Color.parseColor(collection.getCoverPhoto().getColor());

            getWindow().setStatusBarColor(color);

            addToFavFab.setBackgroundTintList(ColorStateList.valueOf(color));

            if (!ColorHelper.isDark(color)) {

                getWindow()
                        .getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

                addToFavFab.setImageTintList(
                        ColorStateList.valueOf(ContextCompat.getColor(this, R.color.activeIcon)));
            }
        }

        artistNameTv.setText(collection.getUser().getName().toLowerCase());

        Glide.with(this)
                .load(collection.getUser().getProfileImage().getLarge())
                .apply(new RequestOptions().centerCrop().circleCrop())
                .transition(new DrawableTransitionOptions().crossFade())
                .into(userPicIv);

        if (collection.getTags().length > 0) {
            for (String tag : collection.getTags()) {
                tagsParentLl.addView(getTagView(tag));
            }
        }

        if (adapter == null) {

            Bundle bundle = new Bundle();
            bundle.putString(CollectionPhotosFragment.KEY_COLLECTION_ID, getCollectionId());
            collectionPhotosFragment.setArguments(bundle);

            adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(collectionPhotosFragment, collection.getTotalPhotos()+" photos");
        }

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    private CardView getTagView(String tag) {
        CardView parent =
                (CardView) getLayoutInflater().inflate(
                        R.layout.item_artist_tag, tagsParentLl, false);
        TextView tagTv = (TextView) parent.getChildAt(0);
        tagTv.setText(tag);
        parent.setOnClickListener(tagOnClickListener);
        return parent;
    }

    @Override
    public String getCollectionId() {
        return String.valueOf(((Collection)getIntent().getParcelableExtra(KEY_COLLECTION)).getId());
    }

    @Override
    public Collection getCollection() {
        return getIntent().getParcelableExtra(KEY_COLLECTION);
    }

    @Override
    public void setFavActive() {

        addToFavFab
                .setImageDrawable(
                        ContextCompat.getDrawable(
                                this, R.drawable.bookmark_check_black_24dp));
    }

    @Override
    public void setFavInactive() {

        addToFavFab
                .setImageDrawable(
                        ContextCompat.getDrawable(
                                this, R.drawable.bookmark_plus_outline_black_24dp));
    }

    private void startUserDescriptionActivity(Collection collection, View transitionView) {

        Intent i = new Intent(this, UserDescriptionActivity.class);
        i.putExtra(UserDescriptionActivity.KEY_USER, collection.getUser());

        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(this,
                        transitionView,
                        getString(R.string.transition_artist_pic));

        startActivity(i, options.toBundle());
    }

    private void startAllSearchActivity(String query) {

        Intent i = new Intent(CollectionDescriptionActivity.this,
                AllSearchActivity.class);
        i.putExtra(AllSearchActivity.KEY_QUERY, query);
        startActivity(i);
    }
}
