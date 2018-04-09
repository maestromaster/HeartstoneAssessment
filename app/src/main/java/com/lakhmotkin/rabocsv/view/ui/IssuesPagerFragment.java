package com.lakhmotkin.rabocsv.view.ui;

import android.animation.Animator;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

import com.lakhmotkin.rabocsv.R;
import com.lakhmotkin.rabocsv.repository.model.Issue;
import com.lakhmotkin.rabocsv.view.adapter.IssuesPagerAdapter;
import com.lakhmotkin.rabocsv.viewmodel.CardsListViewModel;
import com.lakhmotkin.rabocsv.viewmodel.CardsListViewModelFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * A fragment for displaying a pager of images.
 */
public class IssuesPagerFragment extends Fragment {
    private static final String KEY_CARDS = "com.lakhmotkin.heartstonecards.pager.key.card";

    @Inject
    CardsListViewModelFactory cardsModelFactory;

    private ViewPager mCardsPager;
    private List<Issue> mIssues = new ArrayList<>();
    private IssuesPagerAdapter mPagerAdapter;
    private ViewGroup mCardsRoot;
    private View mBgImage;
    private CardsListViewModel mViewModel;

    public static IssuesPagerFragment newInstance(List<Issue> issues) {
        IssuesPagerFragment fragment = new IssuesPagerFragment();
        Bundle argument = new Bundle();
        argument.putSerializable(KEY_CARDS, (Serializable) issues);
        fragment.setArguments(argument);
        return fragment;
    }

    @Nullable
    @Override
    @SuppressWarnings({"unchecked"})
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mIssues = (List<Issue>) getArguments().getSerializable(KEY_CARDS);
        View view = inflater.inflate(R.layout.fragment_pager, container, false);
        mCardsPager = (ViewPager) view.findViewById(R.id.cards_pager);
        mCardsRoot = (ViewGroup) view.findViewById(R.id.cards_pager_root);
        mBgImage = view.findViewById(R.id.cards_pager_bg);
        mBgImage.post(new Runnable() {
            @Override
            public void run() {
                circularReveal(mBgImage);
            }
        });

        mPagerAdapter = new IssuesPagerAdapter(this, mIssues);
        mCardsPager.setAdapter(mPagerAdapter);
        mCardsPager.setCurrentItem(IssuesListActivity.currentPosition);
        mCardsPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                IssuesListActivity.currentPosition = position;
            }
        });
        mCardsPager.post(new Runnable() {
            @Override
            public void run() {
                circularReveal(mCardsPager);
            }
        });

        prepareSharedElementTransition();
        if (savedInstanceState == null) {
            postponeEnterTransition();
        }

        return view;
    }

    protected void circularReveal(View view) {
        float finalRadius = (float) (Math.max(view.getWidth(), view.getHeight()) * 1.1);

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(view, view.getWidth() / 2, view.getHeight() / 2, 0, finalRadius);
        circularReveal.setDuration(500);
        circularReveal.setInterpolator(new AccelerateInterpolator());

        view.setVisibility(View.VISIBLE);
        circularReveal.start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this, cardsModelFactory)
                .get(CardsListViewModel.class);
        mViewModel.error().observe(this, this::onError);
        mViewModel.progress().observe(this, this::onProgress);

    }

    private void onProgress(Boolean aBoolean) {

    }

    private void onError(Throwable throwable) {

    }



    private void prepareSharedElementTransition() {
        Transition transition =
                TransitionInflater.from(getContext())
                        .inflateTransition(R.transition.image_shared_element_transition);
        setSharedElementEnterTransition(transition);

        setEnterSharedElementCallback(
                new SharedElementCallback() {
                    @Override
                    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                        Fragment currentFragment = (Fragment) mCardsPager.getAdapter()
                                .instantiateItem(mCardsPager, IssuesListActivity.currentPosition);
                        View view = currentFragment.getView();
                        if (view == null) {
                            return;
                        }

                        sharedElements.put(names.get(0), view.findViewById(R.id.card_image));
                    }
                });
    }
}
