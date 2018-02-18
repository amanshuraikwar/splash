package com.sonu.app.splash.ui.header;

import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.list.ListItemTypeFactory;
import com.sonu.app.splash.ui.list.SimpleListItemOnClickListener;

/**
 * Created by amanshuraikwar on 27/01/18.
 */

public class HeaderListItem extends ListItem<SimpleListItemOnClickListener> {

    private String text;

    public HeaderListItem(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public int type(ListItemTypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
