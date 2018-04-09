package com.lakhmotkin.rabocsv.repository.data.db;

import com.lakhmotkin.rabocsv.repository.model.Issue;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by Igor Lakhmotkin on 25.02.2018, for HeartstoneAssessment.
 */

@Singleton
public class AppDbHelper implements DbHelper {

    private final AppDatabase mAppDatabase;

    @Inject
    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Observable<List<Issue>> getAllCards() {
        return Observable.fromCallable(new Callable<List<Issue>>() {
            @Override
            public List<Issue> call() throws Exception {
                return mAppDatabase.issueDao().loadAll();
            }
        });
    }

    @Override
    public Observable<Boolean> saveCardsList(List<Issue> cardsList) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.issueDao().insertAll(cardsList);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> deleteAllCards() {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.issueDao().deleteAllIssues();
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> updateCard(Issue issue) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.issueDao().insert(issue);
                return true;
            }
        });
    }
}