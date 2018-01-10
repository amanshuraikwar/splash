package com.sonu.app.splash.ui.loading;

import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.list.ListItemTypeFactory;

/**
 * Created by amanshuraikwar on 23/12/17.
 */

public class LoadingListItem extends ListItem<LoadingOnClickListener> {

    public enum STATE {LOADING, ERROR, NORMAL}

    private STATE state;
    private String infoText, actionText;

    public LoadingListItem(String infoText, String actionText) {
        this.infoText = infoText;
        this.actionText = actionText;
        state = STATE.NORMAL;
    }

    public LoadingListItem(String infoText, String actionText, STATE state) {
        this.infoText = infoText;
        this.actionText = actionText;
        this.state = state;
    }

    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public String getInfoText() {
        return infoText;
    }

    public void setInfoText(String infoText) {
        this.infoText = infoText;
    }

    public String getActionText() {
        return actionText;
    }

    public void setActionText(String actionText) {
        this.actionText = actionText;
    }

    @Override
    public int type(ListItemTypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
