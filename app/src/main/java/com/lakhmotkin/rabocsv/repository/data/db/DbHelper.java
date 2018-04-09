package com.lakhmotkin.rabocsv.repository.data.db;

import com.lakhmotkin.rabocsv.repository.model.Issue;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Igor Lakhmotkin on 25.02.2018, for HeartstoneAssessment.
 */

public interface DbHelper {

    Observable<List<Issue>> getAllCards();

    Observable<Boolean> saveCardsList(List<Issue> cardsList);

    Observable<Boolean> deleteAllCards();

    Observable<Boolean> updateCard(Issue issue);
}
