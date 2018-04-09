package com.lakhmotkin.rabocsv.repository.data;


import android.util.Log;

import com.lakhmotkin.rabocsv.repository.data.db.DbHelper;
import com.lakhmotkin.rabocsv.repository.model.Issue;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by Igor Lakhmotkin on 23.02.2018
 */

@Singleton
public class CardsRepository implements CardsRepositoryType {
    private static final String TAG = "CardsRepository";
    private DbHelper mDbHelper;
    private CSVHelperType mCsvHelper;

    @Inject
    public CardsRepository(DbHelper dbHelper, CSVHelperType csvHelper) {
        mDbHelper = dbHelper;
        mCsvHelper = csvHelper;
    }

    @Override
    public Observable<List<Issue>> fetchRawIssues() {
        return Observable.create(e -> {
//            List<Issue> issues = Arrays.asList(new Issue("Jansen", "Theo", 22, Calendar.getInstance().getTime()),
//                    new Issue("de Vries", "Fiona", 3, Calendar.getInstance().getTime()),
//                    new Issue("Boersma", "Petra", 7, Calendar.getInstance().getTime())
//            );
            Log.d(TAG, "fetchRawIssues: ");
            List<Issue> issues = mCsvHelper.loadIssueDataFromCSV();
            e.onNext(issues);
            e.onComplete();
        });
    }

    @Override
    public Observable<Boolean> insertCards(List<Issue> issues) {
        return Observable.create(e -> {
            mDbHelper.saveCardsList(issues).blockingFirst();
            e.onNext(true);
            e.onComplete();
        });
    }

    @Override
    public Observable<Boolean> updateCard(Issue issue) {
        return mDbHelper.updateCard(issue);
    }

    @Override
    public Observable<List<Issue>> getAllCards() {
        return mDbHelper.getAllCards();
    }

}
