package com.lakhmotkin.rabocsv.repository.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.lakhmotkin.rabocsv.repository.model.Issue;

import java.util.List;

/**
 * Created by Igor Lakhmotkin on 25.02.2018, for HeartstoneAssessment.
 */

@Dao
public interface IssueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Issue issue);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Issue> issues);

    @Query("SELECT * FROM issues ORDER BY lastName")
    List<Issue> loadAll();

    @Query("DELETE FROM issues")
    void deleteAllIssues();
}
