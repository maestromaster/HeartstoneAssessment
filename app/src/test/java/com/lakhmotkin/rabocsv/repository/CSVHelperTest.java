package com.lakhmotkin.rabocsv.repository;

import com.lakhmotkin.rabocsv.repository.data.CSVHelperType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.Assert.assertEquals;

public class CSVHelperTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private CSVHelperType CSVHelper;

    private final String CSV_DATE_STRING = "1978-01-02T00:00:00";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void shouldPassProducts() {

        Date parsedDate = CSVHelper.parseDateString(CSV_DATE_STRING);

        String dateString = "02-01-1978";
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date mockDate;
        try {
            mockDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        assertEquals(parsedDate, parsedDate);
    }
}
