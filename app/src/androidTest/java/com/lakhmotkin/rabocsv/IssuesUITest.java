package com.lakhmotkin.rabocsv;

import android.os.SystemClock;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.lakhmotkin.rabocsv.view.ui.IssuesListActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static org.hamcrest.CoreMatchers.allOf;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class IssuesUITest {

    @Rule
    public ActivityTestRule<IssuesListActivity> mIssuesGridActivityTestRule =
            new ActivityTestRule<>(IssuesListActivity.class);

    @Before
    public void init(){
        mIssuesGridActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void clickProductInList_opensDetailedUi() throws Exception {
        SystemClock.sleep(500);
        onView(withId(R.id.issues_recycler_grid))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(allOf(withId(R.id.full_name), isDisplayed())).check(matches(isCompletelyDisplayed()));
    }
}