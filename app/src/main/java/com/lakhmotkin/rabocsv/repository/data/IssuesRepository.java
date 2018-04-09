package com.lakhmotkin.rabocsv.repository.data;


import com.lakhmotkin.rabocsv.repository.data.db.DbHelper;
import com.lakhmotkin.rabocsv.repository.model.Issue;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by Igor Lakhmotkin on 09.04.2018
 */

@Singleton
public class IssuesRepository implements IssuesRepositoryType {
    private DbHelper mDbHelper;
    private CSVHelperType mCsvHelper;

    @Inject
    public IssuesRepository(DbHelper dbHelper, CSVHelperType csvHelper) {
        mDbHelper = dbHelper;
        mCsvHelper = csvHelper;
    }

    @Override
    public Observable<List<Issue>> fetchRawIssues() {
        return Observable.create(e -> {
            List<Issue> issues = mCsvHelper.loadIssueDataFromCSV();
            e.onNext(issues);
            e.onComplete();
        });
    }

    @Override
    public Observable<Boolean> insertIssues(List<Issue> issues) {
        return Observable.create(e -> {
            mDbHelper.saveIssuesList(issues).blockingFirst();
            e.onNext(true);
            e.onComplete();
        });
    }

    @Override
    public Observable<List<Issue>> getAllIssues() {
        return mDbHelper.getAllIssues();
    }

}
