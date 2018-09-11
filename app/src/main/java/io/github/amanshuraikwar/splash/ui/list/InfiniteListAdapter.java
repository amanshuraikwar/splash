package io.github.amanshuraikwar.splash.ui.list;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.ViewGroup;

import javax.security.auth.callback.Callback;

import io.github.amanshuraikwar.multiitemlistadapter.MultiItemListAdapter;
import io.github.amanshuraikwar.multiitemlistadapter.ViewHolder;
import io.github.amanshuraikwar.splash.data.jetpack.NetworkState;
import io.github.amanshuraikwar.splash.ui.list.networkstate.NetworkStateListItem;
import io.github.amanshuraikwar.splash.util.Util;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

import static io.github.amanshuraikwar.splash.ui.list.ListItemTypeFactory.NETWORK_STATE_LIST_ITEM_TYPE;

public class InfiniteListAdapter extends MultiItemListAdapter {

    private final String TAG = Util.getTag(this);

    private NetworkState networkState;
    private Function0<Unit> retryCallback;

    public InfiniteListAdapter(@NonNull FragmentActivity host,
                               @NonNull ListItemTypeFactory typeFactory,
                               @NonNull Function0<Unit> retryCallback) {
        super(host, typeFactory);
        this.retryCallback = retryCallback;
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            Log.d(TAG, "getItemViewType: network state list item");
            return NETWORK_STATE_LIST_ITEM_TYPE;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            Log.d(TAG, "onBindViewHolder: network state list item");
            holder.bind(new NetworkStateListItem(networkState, retryCallback), host);
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + (hasExtraRow() ? 1 : 0);
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean hadExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean hasExtraRow = hasExtraRow();
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount());
            } else {
                notifyItemInserted(super.getItemCount());
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    boolean hasExtraRow() {
        return networkState != null && networkState != NetworkState.Companion.getLOADED();
    }
}
