package com.sonu.app.splash.ui.photoinfodialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sonu.app.splash.R;
import com.sonu.app.splash.ui.messagedialog.MessageDialogConfig;
import com.sonu.app.splash.ui.photodescription.PhotoDescription;
import com.sonu.app.splash.util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by amanshuraikwar on 09/01/18.
 */

public class PhotoInfoDialog extends AlertDialog {

    private static final String TAG = LogUtils.getLogTag(PhotoInfoDialog.class);

    @BindView(R.id.exifExposureTimeBtn)
    Button exifExposureTimeBtn;

    @BindView(R.id.exifFocalLengthBtn)
    Button exifFocalLengthBtn;

    @BindView(R.id.exifIsoBtn)
    Button exifIsoBtn;

    @BindView(R.id.exifMakeBtn)
    Button exifMakeBtn;

    @BindView(R.id.exifModelBtn)
    Button exifModelBtn;

    @BindView(R.id.exifApertureBtn)
    Button exifApertureBtn;

    @BindView(R.id.photoResolutionBtn)
    Button photoResolutionBtn;

    @OnClick(R.id.actionBtn)
    public void onClick() {
        dismiss();
    }

    private PhotoDescription photoDescription;

    public PhotoInfoDialog(@NonNull Context context,
                           PhotoDescription photoDescription) {
        super(context);
        this.photoDescription = photoDescription;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_photo_info);

        ButterKnife.bind(this);

        Log.i(TAG, "onCreate:photoDescription"+photoDescription);

        if (!photoDescription.getExifMake().equals("")) {
            exifMakeBtn.setText(photoDescription.getExifMake());
        }

        if (!photoDescription.getExifModel().equals("")) {
            exifModelBtn.setText(photoDescription.getExifModel());
        }

        if (!photoDescription.getExifExposureTime().equals("")) {
            exifExposureTimeBtn.setText(String.format("%ss", photoDescription.getExifExposureTime()));
        }

        if (!photoDescription.getExifAperture().equals("")) {
            exifApertureBtn.setText(String.format("f/%s", photoDescription.getExifAperture()));
        }

        if (photoDescription.getExifIso() != 0) {
            exifIsoBtn.setText(String.format("%d", photoDescription.getExifIso()));
        }

        if (!photoDescription.getExifFocalLength().equals("")) {
            exifFocalLengthBtn.setText(String.format("%smm", photoDescription.getExifFocalLength()));
        }

        photoResolutionBtn.setText(String.format("%d x %d", photoDescription.getWidth(), photoDescription.getHeight()));

    }
}
