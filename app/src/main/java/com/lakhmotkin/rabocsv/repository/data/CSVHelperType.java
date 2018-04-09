package com.lakhmotkin.rabocsv.repository.data;

import com.lakhmotkin.rabocsv.repository.model.Issue;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface CSVHelperType {
    List<Issue> loadIssueDataFromCSV();

    Date parseDateString(String src);
}
