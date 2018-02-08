package com.sonu.app.splash.util;

import com.sonu.app.splash.model.unsplash.Photo;

/**
 * Created by amanshuraikwar on 07/02/18.
 */

public class PhotoUtils {

    private static final String FILE_EXTENSION = ".jpg";

    public static String getDownloadFileName(Photo photo) {
        return getDownloadFileName(photo.getId());
    }

    public static String getDownloadFileName(String photoId) {
        return photoId+FILE_EXTENSION;
    }
}
