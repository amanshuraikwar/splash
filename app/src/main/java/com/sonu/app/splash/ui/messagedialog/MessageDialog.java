package com.sonu.app.splash.ui.messagedialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sonu.app.splash.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by amanshuraikwar on 09/01/18.
 */

public class MessageDialog extends AlertDialog {

    @BindView(R.id.parentLl)
    View parent;

    @BindView(R.id.iconIv)
    ImageView icon;

    @BindView(R.id.titleTv)
    TextView title;

    @BindView(R.id.messageTv)
    TextView message;

    @BindView(R.id.actionBtn)
    Button action;

    public View.OnClickListener actionBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dismiss();
        }
    };

    private MessageDialogConfig messageDialogConfig;

    public MessageDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_message);

        ButterKnife.bind(this);

        action.setOnClickListener(actionBtnOnClickListener);

        if (messageDialogConfig != null) {
            messageDialogConfig.apply(getContext(), this);
        }
    }

    public void setConfig(MessageDialogConfig config) {
        this.messageDialogConfig = config;
    }

    @Override
    public void show() {

        super.show();
    }
}
