package com.lakhmotkin.rabocsv.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.lakhmotkin.rabocsv.repository.data.CardsRepositoryType;
import com.lakhmotkin.rabocsv.repository.model.Issue;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Igor Lakhmotkin on 24.02.2018
 */

public class CardsListViewModel extends ViewModel {
    private static final String TAG = "CardsListViewModel";

    private final MutableLiveData<Boolean> mProgress = new MutableLiveData<>();
    private final MutableLiveData<List<Issue>> mCards = new MutableLiveData<>();
    private final MutableLiveData<Throwable> mError = new MutableLiveData<>();
    private final CardsRepositoryType mCardsRepository;

    @Inject
    public CardsListViewModel(@NonNull CardsRepositoryType cardsRepository) {
        mCardsRepository = cardsRepository;
    }

    public void prepare() {
        Log.d(TAG, "prepare: ");
        if (mCards.getValue() == null) {
            fetchLocal();
        }
    }

    public void fetchAllCards() {
        fetchLocal();
    }

    private void fetchRaw() {
        Log.d(TAG, "fetchRaw: ");
        mProgress.postValue(true);
        mCardsRepository
                .fetchRawIssues()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRemoteCardsList, this::onError);
    }

    public void fetchLocal() {
        Log.d(TAG, "fetchLocal: ");
        mProgress.postValue(true);
        mCardsRepository
                .getAllCards()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLocalCardsList, this::onErrorLocal);
    }

    private void onErrorLocal(Throwable throwable) {
        fetchRaw();
    }

    private void onError(Throwable throwable) {
        throwable.printStackTrace();
        mProgress.postValue(false);
        this.mError.postValue(throwable);
    }

    private void onLocalCardsList(List<Issue> issues) {
        Log.d(TAG, "onLocalCardsList: ");
        mProgress.postValue(false);
        if (!issues.isEmpty()) {
            this.mCards.postValue(issues);
        } else {
            fetchRaw();
        }
    }

    private void onRemoteCardsList(List<Issue> issues) {
        mProgress.postValue(false);
        this.mCards.postValue(issues);
        mCardsRepository
                .insertCards(issues)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onCardsInserted, this::onError);
    }

    private void onCardsInserted(Boolean result) {
    }


    private void onCardUpdated(Boolean result) {
    }

    public LiveData<Boolean> progress() {
        return mProgress;
    }

    public LiveData<List<Issue>> cards() {
        return mCards;
    }

    public LiveData<Throwable> error() {
        return mError;
    }

    public Observable<Boolean> addToFavorites(Issue issue) {
        return mCardsRepository.updateCard(issue);
    }
}
