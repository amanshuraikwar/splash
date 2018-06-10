package com.sonu.app.splash.ui.photo;

import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.list.ListItemTypeFactory;

/**
 * Created by amanshuraikwar on 20/12/17.
 */

public class PhotoListItem extends ListItem<PhotoOnClickListener> {

    private Photo photo;

    public PhotoListItem(Photo photo) {
        this.photo = photo;
    }

    public Photo getPhoto() {
        return photo;
    }

    @Override
    public int type(ListItemTypeFactory typeFactory) {
        return PhotoListItem.class.hashCode();
    }
}
