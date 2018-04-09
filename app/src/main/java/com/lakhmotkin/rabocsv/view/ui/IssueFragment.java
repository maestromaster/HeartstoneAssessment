package com.lakhmotkin.rabocsv.view.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lakhmotkin.rabocsv.R;
import com.lakhmotkin.rabocsv.repository.model.Issue;

/**
 * A fragment for displaying an image.
 */
public class IssueFragment extends Fragment {

    private static final String KEY_ISSUE = "com.lakhmotkin.rabocsv.issue.key.issue";
    private static final String KEY_SELECTED_ISSUE = "com.lakhmotkin.rabocsv.issue.key.selected";

    private ImageView mUserPicture;
    private TextView mFullName;
    private TextView mDateOfBirth;
    private TextView mIssues;
    private Issue mIssue;

    public static IssueFragment newInstance(Issue issue, Boolean selected) {
        IssueFragment fragment = new IssueFragment();
        Bundle argument = new Bundle();
        argument.putSerializable(KEY_ISSUE, issue);
        argument.putBoolean(KEY_SELECTED_ISSUE, selected);
        fragment.setArguments(argument);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_issue, container, false);
        Bundle arguments = getArguments();
        mIssue = (Issue) arguments.getSerializable(KEY_ISSUE);
        Boolean selected = arguments.getBoolean(KEY_SELECTED_ISSUE);

        mFullName = view.findViewById(R.id.full_name);
        mFullName.setText(mIssue.getFullName());

        mDateOfBirth = view.findViewById(R.id.date_of_birth_text);
        mDateOfBirth.setText(mIssue.getDateOfBirthFormatted());

        mIssues = view.findViewById(R.id.issues_count_text);
        mIssues.setText(mIssue.getIssueCountString());

        mUserPicture = view.findViewById(R.id.user_picture);
        mUserPicture.setTransitionName(mIssue.getFirstName());
        if (selected) {
            if (getParentFragment() != null) {
                getParentFragment().startPostponedEnterTransition();
            }
        }
        return view;
    }

}
