package com.lakhmotkin.rabocsv.di;

import com.lakhmotkin.rabocsv.view.ui.IssueFragment;
import com.lakhmotkin.rabocsv.view.ui.IssuesPagerFragment;
import com.lakhmotkin.rabocsv.view.ui.IssuesListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by igorlakhmotkin on 23/02/2018.
 */

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract IssuesListFragment contributeIssuesListFragment();

    @ContributesAndroidInjector
    abstract IssuesPagerFragment contributeIssuesPagerFragment();

    @ContributesAndroidInjector
    abstract IssueFragment contributeIssuesPageFragment();
}
