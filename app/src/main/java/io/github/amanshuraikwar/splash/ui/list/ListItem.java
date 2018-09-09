package io.github.amanshuraikwar.splash.ui.list;

/**
 * List item used throughout the app representing a list item of a RecyclerView.
 *
 * @author Amanshu Raikwar
 * Created by amanshuraikwar on 20/12/17.
 */

public abstract class ListItem<OnClickListener> {

    private OnClickListener onClickListener;

    /**
     * To get a unique id for a specific list item  type
     * @return a string which is unique for a specific list item type
     */
    public abstract String id();

    /**
     * To return the concrete child class
     * @return child class
     */
    public abstract String concreteClass();

    public abstract int type(ListItemTypeFactory typeFactory);

    public OnClickListener getOnClickListener(){
        return this.onClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }
}
