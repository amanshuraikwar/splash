package com.sonu.app.splash.model.unsplash;

import java.util.LinkedHashMap;

/**
 * Created by amanshuraikwar on 13/02/18.
 */

public class StatsValues<ValueType> {

    private int total, change;
    private String resolution;
    private int quantity;
    private LinkedHashMap<String, ValueType> values;

    @SuppressWarnings("unchecked")
    private StatsValues(Builder builder) {
        total = builder.total;
        change = builder.change;
        resolution = builder.resolution;
        quantity = builder.quantity;
        values = builder.values;
    }

    public int getTotal() {
        return total;
    }

    public int getChange() {
        return change;
    }

    public String getResolution() {
        return resolution;
    }

    public int getQuantity() {
        return quantity;
    }

    public LinkedHashMap<String, ValueType> getValues() {
        return values;
    }


    public static final class Builder<ValueType> {
        private int total;
        private int change;
        private String resolution = "";
        private int quantity;
        private LinkedHashMap<String, ValueType> values = new LinkedHashMap<>();

        public Builder() {
        }

        public Builder total(int val) {
            total = val;
            return this;
        }

        public Builder change(int val) {
            change = val;
            return this;
        }

        public Builder resolution(String val) {
            resolution = val;
            return this;
        }

        public Builder quantity(int val) {
            quantity = val;
            return this;
        }

        public Builder value(String date, ValueType val) {
            values.put(date, val);
            return this;
        }

        public StatsValues build() {
            return new StatsValues(this);
        }
    }
}
