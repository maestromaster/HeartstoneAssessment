package com.lakhmotkin.rabocsv.repository.data;

import com.lakhmotkin.rabocsv.repository.model.Issue;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Igor Lakhmotkin on 23.02.2018
 */

public interface CardsRepositoryType {
    Observable<List<Issue>> fetchRawIssues();

    Observable<Boolean> insertCards(List<Issue> issues);

    Observable<Boolean> updateCard(Issue issue);

    Observable<List<Issue>> getAllCards();

}
