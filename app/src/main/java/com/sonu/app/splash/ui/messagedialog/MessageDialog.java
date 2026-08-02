package com.sonu.app.splash.ui.messagedialog;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sonu.app.splash.R;

/**
 * Created by amanshuraikwar on 09/01/18.
 */

public class MessageDialog extends AlertDialog {
    View parent;
    ImageView icon;
    TextView title;
    TextView message;
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

        parent = findViewById(R.id.parentLl);

        icon = findViewById(R.id.iconIv);

        title = findViewById(R.id.titleTv);

        message = findViewById(R.id.messageTv);

        action = findViewById(R.id.actionBtn);
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
