package com.lakhmotkin.rabocsv.repository.data.db;

import com.lakhmotkin.rabocsv.repository.model.Issue;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Igor Lakhmotkin on 09.04.2018
 */

public interface DbHelper {

    Observable<List<Issue>> getAllIssues();

    Observable<Boolean> saveIssuesList(List<Issue> issuesList);

    Observable<Boolean> deleteAllIssues();
}
