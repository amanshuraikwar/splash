package com.sonu.app.splash.ui.photo;

import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.list.ListItemTypeFactory;

/**
 * Created by amanshuraikwar on 20/12/17.
 */

public class PhotoHorizontalListItem extends ListItem<PhotoOnClickListener> {

    private Photo photo;

    public PhotoHorizontalListItem(Photo photo) {
        this.photo = photo;
    }

    public Photo getPhoto() {
        return photo;
    }

    @Override
    public int type(ListItemTypeFactory typeFactory) {
        return PhotoHorizontalListItem.class.hashCode();
    }
}
