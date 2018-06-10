package com.sonu.app.splash.ui.user;

import com.sonu.app.splash.model.unsplash.User;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.list.ListItemTypeFactory;

/**
 * Created by amanshuraikwar on 20/12/17.
 */

public class UserHorizontalListItem extends ListItem<UserOnClickListener> {

    private User user;

    public UserHorizontalListItem(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public int type(ListItemTypeFactory typeFactory) {
        return UserHorizontalListItem.class.hashCode();
    }
}
