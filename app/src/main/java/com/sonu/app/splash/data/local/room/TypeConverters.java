package com.sonu.app.splash.data.local.room;

import android.arch.persistence.room.TypeConverter;

/**
 * Created by amanshuraikwar on 07/02/18.
 */

public class TypeConverters {

    @TypeConverter
    public static PhotoDownload.Status toStatus(int ordinal) {
        return PhotoDownload.Status.values()[ordinal];
    }

    @TypeConverter
    public static int toOrdinal(PhotoDownload.Status status) {
        return status.ordinal();
    }
}
