package com.sonu.app.splash.ui.filter;

import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.list.ListItemTypeFactory;
import com.sonu.app.splash.ui.list.SimpleListItemOnClickListener;

/**
 * Created by amanshuraikwar on 27/01/18.
 */

public class FilterListItem extends ListItem<SimpleListItemOnClickListener> {

    private String text;
    private int id;
    private int iconDrawableId;
    private int iconColor;

    public FilterListItem(String text, int id, int iconDrawableId) {
        this.text = text;
        this.id = id;
        this.iconDrawableId = iconDrawableId;
    }

    public FilterListItem(String text, int id, int iconDrawableId, int iconColor) {
        this.text = text;
        this.id = id;
        this.iconDrawableId = iconDrawableId;
        this.iconColor = iconColor;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }

    public int getIconColor() {
        return iconColor;
    }

    public int getIconDrawableId() {
        return iconDrawableId;
    }

    @Override
    public int type(ListItemTypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
