package com.lakhmotkin.rabocsv.repository.data;

import com.lakhmotkin.rabocsv.repository.model.Issue;

import java.util.List;

public interface CSVHelperType {
    List<Issue> loadIssueDataFromCSV();
}
