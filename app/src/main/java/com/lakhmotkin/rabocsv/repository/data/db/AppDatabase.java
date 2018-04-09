package com.lakhmotkin.rabocsv.repository.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.lakhmotkin.rabocsv.repository.data.db.dao.IssueConverters;
import com.lakhmotkin.rabocsv.repository.data.db.dao.IssueDao;
import com.lakhmotkin.rabocsv.repository.model.Issue;

/**
 * Created by Igor Lakhmotkin on 09.04.2018
 */

@Database(entities = {Issue.class}, version = 5, exportSchema = false)
@TypeConverters({IssueConverters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract IssueDao issueDao();

}
