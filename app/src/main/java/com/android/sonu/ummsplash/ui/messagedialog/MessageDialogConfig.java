package com.android.sonu.ummsplash.ui.messagedialog;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.android.sonu.ummsplash.R;

/**
 * Created by amanshuraikwar on 10/01/18.
 */

public class MessageDialogConfig {

    private int color = Color.parseColor("#B71C1C");
    private String actionText = "OK";
    private int iconDrawable = R.drawable.ic_error_grey_56dp;
    private boolean iconOnly = true;
    private boolean customOnActionBtnClick = false;
    private String title = "Unknown error!";
    private String message = "Please try again.";
    private View.OnClickListener actionBtnOnClickListener;

    public void color(int color) {
        this.color = color;
    }

    public void iconDrawable(int iconDrawable) {
        this.iconDrawable = iconDrawable;
    }

    public void actionText(@NonNull String actionText) {
        this.actionText = actionText;
    }

    public void title(@NonNull String title) {
        iconOnly = false;
        this.title = title;
    }

    public void message(@NonNull String message) {
        iconOnly = false;
        this.message = message;
    }

    public void iconOnly(boolean iconOnly) {
        this.iconOnly = iconOnly;
    }

    public void actionOnClickListener(@NonNull View.OnClickListener onClickListener) {
        customOnActionBtnClick = true;
        actionBtnOnClickListener = onClickListener;
    }

    void apply(Context context, MessageDialog messageDialog) {
        messageDialog.parent.setBackgroundColor(color);
        messageDialog.icon.setImageDrawable(ContextCompat.getDrawable(context, iconDrawable));
        messageDialog.action.setText(actionText);

        if (iconOnly) {
            messageDialog.title.setVisibility(View.GONE);
            messageDialog.message.setVisibility(View.GONE);
        } else {
            messageDialog.title.setVisibility(View.VISIBLE);
            messageDialog.message.setVisibility(View.VISIBLE);

            messageDialog.title.setText(title);
            messageDialog.message.setText(message);
        }

        if (customOnActionBtnClick) {
            messageDialog.action.setOnClickListener(actionBtnOnClickListener);
        } else {
            messageDialog.action.setOnClickListener(messageDialog.actionBtnOnClickListener);
        }
    }

}
