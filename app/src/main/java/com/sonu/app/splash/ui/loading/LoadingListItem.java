package com.sonu.app.splash.ui.loading;

import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.list.ListItemTypeFactory;

/**
 * Created by amanshuraikwar on 23/12/17.
 */

public class LoadingListItem extends ListItem<LoadingOnClickListener> {

    public enum STATE {LOADING, ERROR, NORMAL}

    private STATE state;

    public LoadingListItem() {
        state = STATE.NORMAL;
    }

    public LoadingListItem(STATE state) {
        this.state = state;
    }

    public synchronized STATE getState() {
        return state;
    }

    public synchronized void setState(STATE state) {
        this.state = state;
    }

    @Override
    public int type(ListItemTypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
