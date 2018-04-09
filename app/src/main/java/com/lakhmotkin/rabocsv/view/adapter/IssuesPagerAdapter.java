package com.lakhmotkin.rabocsv.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lakhmotkin.rabocsv.repository.model.Issue;
import com.lakhmotkin.rabocsv.view.ui.IssueFragment;
import com.lakhmotkin.rabocsv.view.ui.IssuesListActivity;

import java.util.List;

public class IssuesPagerAdapter extends FragmentStatePagerAdapter {

    private List<Issue> mCardsList;

    public IssuesPagerAdapter(Fragment fragment, List<Issue> issues) {
        super(fragment.getChildFragmentManager());
        mCardsList = issues;
    }

    public void setCardsList(List<Issue> list){
        mCardsList = list;
    }

    @Override
    public int getCount() {
        return mCardsList.size();
    }

    @Override
    public Fragment getItem(int position) {
        boolean selected = (position == IssuesListActivity.currentPosition);
        return IssueFragment.newInstance(mCardsList.get(position), selected);
    }
}
