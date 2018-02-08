package com.sonu.app.splash.ui.photo;

import com.sonu.app.splash.model.unsplash.Photo;

/**
 * Created by amanshuraikwar on 07/02/18.
 */

public class PhotoLight {

    public String url;

    public int width, height;

    public String color;

    public PhotoLight(Photo photo) {
        url = photo.getPhotoUrls().getSmall();
        width = photo.getWidth();
        height = photo.getHeight();
        color = photo.getColor();
    }
}
