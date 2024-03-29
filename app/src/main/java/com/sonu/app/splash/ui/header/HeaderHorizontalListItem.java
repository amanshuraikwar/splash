package com.sonu.app.splash.ui.header;

import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.list.ListItemTypeFactory;
import com.sonu.app.splash.ui.list.SimpleListItemOnClickListener;

/**
 * Created by amanshuraikwar on 27/01/18.
 */

public class HeaderHorizontalListItem extends ListItem<SimpleListItemOnClickListener> {

    private String text;

    public HeaderHorizontalListItem(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public int type(ListItemTypeFactory typeFactory) {
        return HeaderHorizontalListItem.class.hashCode();
    }
}
