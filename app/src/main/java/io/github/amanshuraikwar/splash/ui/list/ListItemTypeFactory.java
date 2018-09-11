package io.github.amanshuraikwar.splash.ui.list;

import android.view.View;

import io.github.amanshuraikwar.multiitemlistadapter.BaseTypeFactory;
import io.github.amanshuraikwar.multiitemlistadapter.ViewHolder;
import io.github.amanshuraikwar.splash.ui.list.networkstate.NetworkStateListItem;
import io.github.amanshuraikwar.splash.ui.list.networkstate.NetworkStateViewHolder;
import io.github.amanshuraikwar.splash.ui.list.photo.PhotoListItem;
import io.github.amanshuraikwar.splash.ui.list.photo.PhotoViewHolder;

/**
 * List item type factory responsible for getting layout id and ViewHolder instance.
 *
 * @author Amanshu Raikwar
 * Created by amanshuraikwar on 20/12/17.
 */

public class ListItemTypeFactory extends BaseTypeFactory {

    public final static int NETWORK_STATE_LIST_ITEM_TYPE = 1;

    public int type(PhotoListItem item) {
        return 0;
    }

    public int type(NetworkStateListItem item) {
        return 1;
    }

    /**
     * To get layout file id (R.layout.*) for a corresponding list item/view type.
     *
     * @param viewType list item/view type.
     * @return layout file id corresponding to list item/view type.
     */
    public int getLayout(int viewType) {
        switch (viewType) {
            case 0:
                return PhotoViewHolder.Companion.getLAYOUT();
            case NETWORK_STATE_LIST_ITEM_TYPE:
                return NetworkStateViewHolder.Companion.getLAYOUT();
            default:
                return super.getLayout(viewType);
        }
    }

    /**
     * To get ViewHolder instance for corresponding list item/view type.
     *
     * @param parent parent view instance to instantiate corresponding ViewHolder instance.
     * @param type list item/view type of which the ViewHolder instance is needed.
     * @return ViewHolder instance for the given list item/view type.
     */
    public ViewHolder createViewHolder(View parent, int type) {

        ViewHolder viewHolder = null;

        switch (type) {
            case 0:
                viewHolder = new PhotoViewHolder(parent);
                break;
            case NETWORK_STATE_LIST_ITEM_TYPE:
                viewHolder = new NetworkStateViewHolder(parent);
                break;
            default:
                viewHolder = super.createViewHolder(parent, type);
        }

        return viewHolder;
    }
}
