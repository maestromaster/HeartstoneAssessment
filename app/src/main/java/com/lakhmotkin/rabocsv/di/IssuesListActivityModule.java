package com.lakhmotkin.rabocsv.di;

import com.lakhmotkin.rabocsv.view.ui.IssuesListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by igorlakhmotkin on 23/02/2018.
 */

@Module
public abstract class IssuesListActivityModule {
    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract IssuesListActivity issuesGridActivity();
}
