package com.robinhood.spark;

public abstract class SparkAdapter {

    public abstract int getCount();

    public abstract Object getItem(int index);

    public float getX(int index) {
        return index;
    }

    public abstract float getY(int index);
}
