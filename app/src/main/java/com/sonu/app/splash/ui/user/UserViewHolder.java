package com.sonu.app.splash.ui.user;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.sonu.app.splash.R;
import com.sonu.app.splash.ui.list.ViewHolder;
import com.sonu.app.splash.util.LogUtils;

/**
 * Created by amanshuraikwar on 20/12/17.
 */

public class UserViewHolder extends ViewHolder<UserListItem> {

    private static final String TAG = LogUtils.getLogTag(UserViewHolder.class);

    @LayoutRes
    public static final int LAYOUT = R.layout.item_user;
    ImageView artistPicIv;
    View parent;
    TextView artistNameTv;
    TextView artistUsernameTv;

    public UserViewHolder(View itemView) {
        super(itemView);
        artistPicIv = itemView.findViewById(R.id.artistPicIv);
        parent = itemView.findViewById(R.id.parent);
        artistNameTv = itemView.findViewById(R.id.artistNameTv);
        artistUsernameTv = itemView.findViewById(R.id.artistUsernameTv);
    }

    @Override
    public void bind(final UserListItem listItem, FragmentActivity parentActivity) {

        Log.i(TAG, "bind:userDescription="+listItem.getUser());

        Glide.with(parentActivity)
                .load(listItem.getUser().getProfileImage().getLarge())
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(new RequestOptions().centerCrop().circleCrop())
                .into(artistPicIv);

        artistNameTv.setText(listItem.getUser().getName());
        artistUsernameTv.setText(String.format("@%s", listItem.getUser().getUsername()));

        // unique transition name
        artistPicIv.setTransitionName(listItem.getUser().getId());

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItem.getOnClickListener().onClick(listItem.getUser(), artistPicIv);
            }
        });
    }
}
