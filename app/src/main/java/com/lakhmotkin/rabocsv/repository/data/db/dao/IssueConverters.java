package com.lakhmotkin.rabocsv.repository.data.db.dao;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Igor Lakhmotkin on 09.04.2018
 */

public class IssueConverters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
