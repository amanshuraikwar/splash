package com.sonu.app.splash.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.sonu.app.splash.R;
import com.sonu.app.splash.ui.messagedialog.MessageDialog;
import com.sonu.app.splash.ui.messagedialog.MessageDialogConfig;

/**
 * Created by amanshuraikwar on 18/01/18.
 */

public class PermissionsHelper {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 101;

    public static boolean checkStoragePermission(final Activity activity) {

        final MessageDialog messageDialog = new MessageDialog(activity);

        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                MessageDialogConfig messageDialogConfig = new MessageDialogConfig();
                messageDialogConfig
                        .color(activity.getColor(com.sonu.app.splash.R.color.darkOrange));
                messageDialogConfig.actionText("GIVE PERMISSIONS");
                messageDialogConfig.iconDrawable(R.drawable.ic_error_grey_56dp);
                messageDialogConfig
                        .title(activity.getString(R.string.write_external_storage_permission_title));
                messageDialogConfig
                        .message(activity.getString(R.string.write_external_storage_permission_message));
                messageDialogConfig
                        .actionOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                ActivityCompat.requestPermissions(activity,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                                messageDialog.dismiss();
                            }
                        });
                messageDialog.setConfig(messageDialogConfig);
                messageDialog.show();

                return false;
            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                return false;
            }
        }

        return true;
    }
}
