package com.lakhmotkin.rabocsv.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lakhmotkin.rabocsv.R;
import com.lakhmotkin.rabocsv.repository.model.Issue;
import com.lakhmotkin.rabocsv.view.ui.IssuesListActivity;
import com.lakhmotkin.rabocsv.view.ui.IssuesPagerFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class IssuesGridAdapter extends RecyclerView.Adapter<IssuesGridAdapter.ImageViewHolder> {

    private static final String TAG = "IssuesGridAdapter";

    private interface ViewHolderListener {

        void onLoadCompleted(ImageView view, int adapterPosition);

        void onItemClicked(View view, int adapterPosition);
    }

    private final ViewHolderListener viewHolderListener;
    private List<Issue> mIssues = new ArrayList<>();

    public IssuesGridAdapter(Fragment fragment) {
        this.viewHolderListener = new ViewHolderListenerImpl(fragment);
    }

    public void setCardList(final List<Issue> issues) {
        mIssues = issues;
        notifyDataSetChanged();
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item_card, parent, false);
        return new ImageViewHolder(view, viewHolderListener);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.onBind(mIssues.get(position));
    }

    @Override
    public int getItemCount() {
        return mIssues.size();
    }

    private class ViewHolderListenerImpl implements ViewHolderListener {

        private Fragment fragment;
        private AtomicBoolean enterTransitionStarted;

        ViewHolderListenerImpl(Fragment fragment) {
            this.fragment = fragment;
            this.enterTransitionStarted = new AtomicBoolean();
        }

        @Override
        public void onLoadCompleted(ImageView view, int position) {
            if (IssuesListActivity.currentPosition != position) {
                return;
            }
            if (enterTransitionStarted.getAndSet(true)) {
                return;
            }
            fragment.startPostponedEnterTransition();
        }

        @Override
        public void onItemClicked(View view, int position) {
            IssuesListActivity.currentPosition = position;

            if (fragment.getExitTransition() != null) {
                ((TransitionSet) fragment.getExitTransition()).excludeTarget(view, true);
            }
            ImageView transitioningView = view.findViewById(R.id.card_image);
            fragment.getFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .addSharedElement(transitioningView, transitioningView.getTransitionName())
                    .replace(R.id.fragment_container, IssuesPagerFragment.newInstance(mIssues), IssuesPagerFragment.class
                            .getSimpleName())
                    .addToBackStack(null)
                    .commit();
        }
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        private final ImageView image;
        private final TextView name;
        private final TextView dateOfBirth;
        private final TextView issueCount;
        private final ViewHolderListener viewHolderListener;

        ImageViewHolder(View itemView,
                        ViewHolderListener viewHolderListener) {
            super(itemView);
            this.image = itemView.findViewById(R.id.card_image);
            this.name = itemView.findViewById(R.id.full_name);
            this.dateOfBirth = itemView.findViewById(R.id.date_of_birth);
            this.issueCount = itemView.findViewById(R.id.issues_count);
            this.viewHolderListener = viewHolderListener;
            itemView.findViewById(R.id.grid_item_root).setOnClickListener(this);
        }

        void onBind(Issue issue) {
            image.setTransitionName(issue.getFirstName());
            name.setText(issue.getFullName());
            dateOfBirth.setText(issue.getDateOfBirthFormatted());
            issueCount.setText(issue.getIssueCountString());
            viewHolderListener.onLoadCompleted(image, getAdapterPosition());
        }

        @Override
        public void onClick(View view) {
            viewHolderListener.onItemClicked(view, getAdapterPosition());
        }
    }

}