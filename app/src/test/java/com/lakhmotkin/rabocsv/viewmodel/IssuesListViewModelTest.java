package com.lakhmotkin.rabocsv.viewmodel;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.lakhmotkin.rabocsv.repository.data.CSVHelperType;
import com.lakhmotkin.rabocsv.repository.data.IssuesRepository;
import com.lakhmotkin.rabocsv.repository.data.IssuesRepositoryType;
import com.lakhmotkin.rabocsv.repository.data.db.DbHelper;
import com.lakhmotkin.rabocsv.repository.model.Issue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Igor Lakhmotkin on 09.04.2018
 */
public class IssuesListViewModelTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IssuesRepositoryType mRepository;

    @Mock
    private DbHelper dbHelper;

    @Mock
    private CSVHelperType CSVHelper;

    private IssuesListViewModel mViewModel;

    private final List<Issue> MANY_ISSUES = Arrays.asList(
            new Issue("Jansen", "Theo", 5, Calendar.getInstance().getTime()),
            new Issue("de Vries", "Fiona", 7, Calendar.getInstance().getTime()),
            new Issue("Petra", "Boersma", 1, Calendar.getInstance().getTime())
            );

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mViewModel = new IssuesListViewModel(mRepository);
    }

    @Test
    public void shouldPassProducts() {
        when(mRepository.fetchRawIssues()).thenReturn(Observable.just(MANY_ISSUES));

        TestObserver<List<Issue>> subscriber = mRepository
                .fetchRawIssues()
                .test();

        subscriber.awaitTerminalEvent();
        subscriber.assertComplete();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        assertEquals(subscriber.values().get(0).get(0).getLastName(), MANY_ISSUES.get(0).getLastName());
    }
}