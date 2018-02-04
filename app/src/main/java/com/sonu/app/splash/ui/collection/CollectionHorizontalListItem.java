package com.sonu.app.splash.ui.collection;

import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.list.ListItemTypeFactory;

/**
 * Created by amanshuraikwar on 28/01/18.
 */

public class CollectionHorizontalListItem extends ListItem<CollectionOnClickListener> {

    private Collection collection;

    public CollectionHorizontalListItem(Collection collection) {
        this.collection = collection;
    }

    public Collection getCollection() {
        return collection;
    }

    @Override
    public int type(ListItemTypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
