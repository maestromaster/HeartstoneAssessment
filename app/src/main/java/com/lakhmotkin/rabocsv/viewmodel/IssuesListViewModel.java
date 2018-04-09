package com.lakhmotkin.rabocsv.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.lakhmotkin.rabocsv.repository.data.IssuesRepositoryType;
import com.lakhmotkin.rabocsv.repository.model.Issue;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Igor Lakhmotkin on 09.04.2018
 */

public class IssuesListViewModel extends ViewModel {

    private final MutableLiveData<Boolean> mProgress = new MutableLiveData<>();
    private final MutableLiveData<List<Issue>> mIssues = new MutableLiveData<>();
    private final MutableLiveData<Throwable> mError = new MutableLiveData<>();
    private final IssuesRepositoryType mIssuesRepository;

    @Inject
    public IssuesListViewModel(@NonNull IssuesRepositoryType issuesRepository) {
        mIssuesRepository = issuesRepository;
    }

    public void prepare() {
        if (mIssues.getValue() == null) {
            fetchFromDB();
        }
    }

    public void fetchAllIssues() {
        fetchFromDB();
    }

    private void fetchRaw() {
        mProgress.postValue(true);
        mIssuesRepository
                .fetchRawIssues()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRawIssuesList, this::onError);
    }

    public void fetchFromDB() {
        mProgress.postValue(true);
        mIssuesRepository
                .getAllIssues()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDBIssuesList, this::onErrorLocal);
    }

    private void onErrorLocal(Throwable throwable) {
        fetchRaw();
    }

    private void onError(Throwable throwable) {
        throwable.printStackTrace();
        mProgress.postValue(false);
        this.mError.postValue(throwable);
    }

    private void onDBIssuesList(List<Issue> issues) {
        mProgress.postValue(false);
        if (!issues.isEmpty()) {
            this.mIssues.postValue(issues);
        } else {
            fetchRaw();
        }
    }

    private void onRawIssuesList(List<Issue> issues) {
        mProgress.postValue(false);
        this.mIssues.postValue(issues);
        mIssuesRepository
                .insertIssues(issues)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onIssuesInserted, this::onError);
    }

    private void onIssuesInserted(Boolean result) {
    }

    public LiveData<Boolean> progress() {
        return mProgress;
    }

    public LiveData<List<Issue>> issues() {
        return mIssues;
    }

    public LiveData<Throwable> error() {
        return mError;
    }

}
