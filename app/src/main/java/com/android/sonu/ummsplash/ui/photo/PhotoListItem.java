package com.android.sonu.ummsplash.ui.photo;

import com.android.sonu.ummsplash.ui.list.ListItem;
import com.android.sonu.ummsplash.ui.list.ListItemTypeFactory;

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
        return typeFactory.type(this);
    }
}
