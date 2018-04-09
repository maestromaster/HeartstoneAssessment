package com.lakhmotkin.rabocsv.repository.data;

import com.lakhmotkin.rabocsv.repository.model.Issue;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Igor Lakhmotkin on 09.04.2018
 */

public interface IssuesRepositoryType {
    Observable<List<Issue>> fetchRawIssues();

    Observable<Boolean> insertIssues(List<Issue> issues);

    Observable<List<Issue>> getAllIssues();

}
