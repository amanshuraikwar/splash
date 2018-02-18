package com.sonu.app.splash.ui.userdescription;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.sonu.app.splash.R;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.cache.ContentCache;
import com.sonu.app.splash.model.unsplash.User;
import com.sonu.app.splash.ui.architecture.BaseActivity;
import com.sonu.app.splash.ui.content.usercollections.UserCollectionsFragment;
import com.sonu.app.splash.ui.content.userphotos.UserPhotosFragment;
import com.sonu.app.splash.ui.home.ViewPagerAdapter;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.list.ListItemTypeFactory;
import com.sonu.app.splash.ui.list.ContentListAdapter;
import com.sonu.app.splash.ui.messagedialog.MessageDialog;
import com.sonu.app.splash.ui.messagedialog.MessageDialogConfig;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.photo.PhotoListItem;
import com.sonu.app.splash.ui.photo.PhotoOnClickListener;
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionActivity;
import com.sonu.app.splash.ui.search.allsearch.AllSearchActivity;
import com.sonu.app.splash.ui.widget.NestedScrollAppBarLayout;
import com.sonu.app.splash.ui.widget.SwipeBackCoordinatorLayout;
import com.sonu.app.splash.util.ConnectionUtil;
import com.sonu.app.splash.util.LogUtils;
import com.sonu.app.splash.util.NumberUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by amanshuraikwar on 16/01/18.
 */

public class UserDescriptionActivity
        extends BaseActivity<UserDescriptionContract.Presenter>
        implements UserDescriptionContract.View {

    public static final String KEY_USER = "User";

    private static final String TAG = LogUtils.getLogTag(UserDescriptionActivity.class);

    @BindView(R.id.eddfl)
    SwipeBackCoordinatorLayout eddfl;

    @BindView(R.id.userPicIv)
    ImageView userPicIv;

    @BindView(R.id.artistNameTv)
    TextView artistNameTv;

    @BindView(R.id.artistUsernameTv)
    TextView artistUsernameTv;

    @BindView(R.id.artistBioTv)
    TextView artistBioTv;

    @BindView(R.id.artistLocationFl)
    View artistLocationFl;

    @BindView(R.id.artistLocationTv)
    TextView artistLocationTv;

    @BindView(R.id.artistPhotosCountBtn)
    Button artistPhotosCountBtn;

    @BindView(R.id.artistFollowersCountBtn)
    Button artistFollowersCountBtn;

    @BindView(R.id.artistLikesCountBtn)
    Button artistLikesCountBtn;

    @BindView(R.id.tagsParentLl)
    LinearLayout tagsParentLl;

    @BindView(R.id.artistPortfolioLinkIb)
    ImageButton artistPortfolioLinkIb;

    @BindView(R.id.userDataLl)
    LinearLayout userDataLl;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.appBar)
    NestedScrollAppBarLayout appBar;

    @BindView(R.id.userInfoLoadingWrapperFl)
    View userInfoLoadingWrapperFl;

    @BindView(R.id.userInfoPb)
    ProgressBar userInfoPb;

    @BindView(R.id.userInfoRetryBtn)
    Button userInfoRetryBtn;

    @BindView(R.id.addToFavFab)
    FloatingActionButton addToFavFab;

    @Inject
    UserPhotosFragment userPhotosFragment;

    @Inject
    UserCollectionsFragment userCollectionsFragment;

    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_description);
        ButterKnife.bind(this);

        User user = getIntent().getParcelableExtra(KEY_USER);

        updateUi(user);

        eddfl.setOnSwipeListener(new SwipeBackCoordinatorLayout.OnSwipeListener() {
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
        });

        userInfoRetryBtn.setOnClickListener(view -> getPresenter().getData());

        addToFavFab.setOnClickListener(view -> getPresenter().onAddToFavClick());
    }

    private void updateUi(User user) {

        artistNameTv.setText(user.getName().toLowerCase());

        artistUsernameTv.setText(
                String.format("@%s", user.getUsername()));

        Glide.with(this)
                .load(user.getProfileImage().getLarge())
                .apply(new RequestOptions().centerCrop().circleCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(userPicIv);

        if (viewPagerAdapter == null) {

            Bundle bundle = new Bundle();
            bundle.putString(UserPhotosFragment.KEY_USERNAME, getCurArtistUsername());
            bundle.putString(UserCollectionsFragment.KEY_USERNAME, getCurArtistUsername());
            userPhotosFragment.setArguments(bundle);
            userCollectionsFragment.setArguments(bundle);

            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPagerAdapter.addFragment(userPhotosFragment,
                    String.format(
                            "%s photos",
                            String.valueOf(NumberUtils.format(user.getTotalPhotos()))));
            viewPagerAdapter.addFragment(userCollectionsFragment,
                    String.format(
                            "%s collections",
                            String.valueOf(NumberUtils.format(user.getTotalCollections()))));
        }

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void hideLoading() {
        userInfoLoadingWrapperFl.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        userInfoLoadingWrapperFl.setVisibility(View.VISIBLE);
        userInfoPb.setVisibility(View.VISIBLE);
        userInfoRetryBtn.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        userInfoLoadingWrapperFl.setVisibility(View.VISIBLE);
        userInfoPb.setVisibility(View.GONE);
        userInfoRetryBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayUserDescription(final User user) {

        artistPhotosCountBtn.setText(
                String.format(
                        "%s photos",
                        String.valueOf(NumberUtils.format(user.getTotalPhotos()))));

        artistFollowersCountBtn.setText(
                String.format(
                        "%s followers",
                        String.valueOf(NumberUtils.format(user.getFollowersCount()))));

        artistLikesCountBtn.setText(
                String.format(
                        "%s likes",
                        String.valueOf(NumberUtils.format(user.getTotalLikes()))));

        if (user.getBio() != null) {

            artistBioTv.setVisibility(View.VISIBLE);
            artistBioTv.setText(user.getBio().trim());
        }

        if (user.getLocation() != null) {

            artistLocationFl.setVisibility(View.VISIBLE);
            artistLocationTv.setText(user.getLocation());
        }

        for (String tag : user.getUserTags().getCustom()) {
            tagsParentLl.addView(getTagView(tag));
        }

        for (String tag : user.getUserTags().getAggregated()) {
            tagsParentLl.addView(getTagView(tag));
        }

        artistPortfolioLinkIb.setOnClickListener(view -> {

            if (user.getPortfolioUrl() != null) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(user.getPortfolioUrl()));
                startActivity(intent);
            } else {
                Toast.makeText(UserDescriptionActivity.this, "No portfolio url", Toast.LENGTH_SHORT).show();
            }
        });
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

    private CardView getTagView(String tag) {
        CardView parent =
                (CardView) getLayoutInflater().inflate(
                        R.layout.item_artist_tag, tagsParentLl, false);
        TextView tagTv = (TextView) parent.getChildAt(0);
        tagTv.setText(tag);
        parent.setOnClickListener(tagOnClickListener);
        return parent;
    }

    private View.OnClickListener tagOnClickListener =
            view -> {

                Intent i = new Intent(UserDescriptionActivity.this,
                        AllSearchActivity.class);
                i.putExtra(
                        AllSearchActivity.KEY_QUERY,
                        ((TextView)((CardView) view).getChildAt(0)).getText());
                startActivity(i);
            };

    @Override
    public String getCurArtistUsername() {

            return ((User)
                    getIntent()
                            .getParcelableExtra(
                                    UserDescriptionActivity.KEY_USER)).getUsername();
    }

    @Override
    public String getCurArtistId() {
        return ((User)
                getIntent()
                        .getParcelableExtra(
                                UserDescriptionActivity.KEY_USER)).getId();
    }

    @Override
    public User getCurUser() {
        return getIntent().getParcelableExtra(UserDescriptionActivity.KEY_USER);
    }
}
