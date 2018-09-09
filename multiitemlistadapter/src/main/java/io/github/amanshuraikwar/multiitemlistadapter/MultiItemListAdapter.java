package io.github.amanshuraikwar.multiitemlistadapter;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MultiItemListAdapter<TypeFactory extends BaseTypeFactory> extends PagedListAdapter<ListItem, ViewHolder> {

    private static DiffUtil.ItemCallback<ListItem> LIST_ITEM_COMPARATOR = new DiffUtil.ItemCallback<ListItem>() {

        @Override
        public boolean areItemsTheSame(@NonNull ListItem listItem1,
                                       @NonNull ListItem listItem2) {
            return (listItem1.concreteClass() + listItem1.id())
                    .equals(listItem2.concreteClass() + listItem2.id());
        }

        @Override
        public boolean areContentsTheSame(@NonNull ListItem listItem1,
                                          @NonNull ListItem listItem2) {
            return listItem1 == listItem2;
        }
    };

    protected FragmentActivity host;
    private TypeFactory typeFactory;

    protected MultiItemListAdapter(@NonNull FragmentActivity host,
                                   @NonNull TypeFactory typeFactory) {
        super(LIST_ITEM_COMPARATOR);
        this.host = host;
        this.typeFactory = typeFactory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View contactView =
                LayoutInflater
                        .from(host)
                        .inflate(typeFactory.getLayout(viewType), parent, false);

        return typeFactory.createViewHolder(contactView, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position), host);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).type(typeFactory);
    }
}
