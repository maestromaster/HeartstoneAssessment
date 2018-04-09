package com.lakhmotkin.rabocsv.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.lakhmotkin.rabocsv.repository.data.IssuesRepositoryType;


/**
 * Created by Igor Lakhmotkin on 09.04.2018
 */

public class IssuesListViewModelFactory implements ViewModelProvider.Factory {
    private final IssuesRepositoryType repository;

    public IssuesListViewModelFactory(IssuesRepositoryType repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new IssuesListViewModel(repository);
    }
}
