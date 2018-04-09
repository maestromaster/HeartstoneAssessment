package com.lakhmotkin.rabocsv.view.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lakhmotkin.rabocsv.R;
import com.lakhmotkin.rabocsv.repository.model.Issue;
import com.lakhmotkin.rabocsv.view.adapter.IssuesGridAdapter;
import com.lakhmotkin.rabocsv.viewmodel.CardsListViewModel;
import com.lakhmotkin.rabocsv.viewmodel.CardsListViewModelFactory;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * A fragment for displaying a grid of images.
 */
public class IssuesListFragment extends Fragment {

    private static final String TAG = "IssuesListFragment";

    @Inject
    CardsListViewModelFactory cardsModelFactory;

    private RecyclerView mCardsRecyclerView;
    private IssuesGridAdapter mIssuesGridAdapter;
    private ViewGroup mLoadingContainer;
    private ViewGroup mErrorContainer;
    private TextView mErrorText;

    private CardsListViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        setHasOptionsMenu(true);
        mCardsRecyclerView = (RecyclerView) view.findViewById(R.id.cards_recycler_grid);
        mIssuesGridAdapter = new IssuesGridAdapter(this);
        mCardsRecyclerView.setAdapter(mIssuesGridAdapter);

        prepareTransitions();
        postponeEnterTransition();

        mLoadingContainer = view.findViewById(R.id.cards_loading);
        mErrorContainer = view.findViewById(R.id.cards_error);
        mErrorText = view.findViewById(R.id.cards_error_text);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this, cardsModelFactory)
                .get(CardsListViewModel.class);
        mViewModel.error().observe(this, this::onError);
        mViewModel.cards().observe(this, this::onCards);
        mViewModel.progress().observe(this, this::onProgress);
        prepare();
    }

    private void prepare() {
        mViewModel.prepare();
    }

    private void fetchAllCards() {
        mErrorContainer.setVisibility(View.INVISIBLE);
        mViewModel.fetchAllCards();
    }

    private void onProgress(Boolean aBoolean) {
        if (aBoolean) {
            mLoadingContainer.setVisibility(View.VISIBLE);
        } else {
            mLoadingContainer.setVisibility(View.INVISIBLE);
        }
    }

    private void onError(Throwable throwable) {
        mErrorText.setText(getString(R.string.error_message_with_message, throwable.getMessage()));
        mErrorContainer.setVisibility(View.VISIBLE);
    }


    private void onCards(List<Issue> issues){
        Log.d(TAG, "onCards: " + issues.size());
        mIssuesGridAdapter.setCardList(issues);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scrollToPosition();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_all:
                fetchAllCards();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void scrollToPosition() {
        mCardsRecyclerView.addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v,
                                       int left,
                                       int top,
                                       int right,
                                       int bottom,
                                       int oldLeft,
                                       int oldTop,
                                       int oldRight,
                                       int oldBottom) {
                mCardsRecyclerView.removeOnLayoutChangeListener(this);
                final RecyclerView.LayoutManager layoutManager = mCardsRecyclerView.getLayoutManager();
                View viewAtPosition = layoutManager.findViewByPosition(IssuesListActivity.currentPosition);
                if (viewAtPosition == null || layoutManager
                        .isViewPartiallyVisible(viewAtPosition, false, true)) {
                    mCardsRecyclerView.post(() -> layoutManager.scrollToPosition(IssuesListActivity.currentPosition));
                }
            }
        });
    }

    private void prepareTransitions() {
        setExitTransition(TransitionInflater.from(getContext())
                .inflateTransition(R.transition.grid_exit_transition));

        setExitSharedElementCallback(
                new SharedElementCallback() {
                    @Override
                    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                        RecyclerView.ViewHolder selectedViewHolder = mCardsRecyclerView
                                .findViewHolderForAdapterPosition(IssuesListActivity.currentPosition);
                        if (selectedViewHolder == null || selectedViewHolder.itemView == null) {
                            return;
                        }

                        sharedElements
                                .put(names.get(0), selectedViewHolder.itemView.findViewById(R.id.card_image));
                    }
                });
    }
}
