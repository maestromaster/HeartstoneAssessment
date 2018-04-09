package com.lakhmotkin.rabocsv.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lakhmotkin.rabocsv.repository.model.Issue;
import com.lakhmotkin.rabocsv.view.ui.IssueFragment;
import com.lakhmotkin.rabocsv.view.ui.IssuesListActivity;

import java.util.List;

public class IssuesPagerAdapter extends FragmentStatePagerAdapter {

    private List<Issue> mIssuesList;

    public IssuesPagerAdapter(Fragment fragment, List<Issue> issues) {
        super(fragment.getChildFragmentManager());
        mIssuesList = issues;
    }

    public void setIssuesList(List<Issue> list){
        mIssuesList = list;
    }

    @Override
    public int getCount() {
        return mIssuesList.size();
    }

    @Override
    public Fragment getItem(int position) {
        boolean selected = (position == IssuesListActivity.currentPosition);
        return IssueFragment.newInstance(mIssuesList.get(position), selected);
    }
}
