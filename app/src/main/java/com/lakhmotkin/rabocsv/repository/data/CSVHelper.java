package com.lakhmotkin.rabocsv.repository.data;

import android.content.Context;
import android.util.Log;

import com.lakhmotkin.rabocsv.R;
import com.lakhmotkin.rabocsv.repository.model.Issue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CSVHelper implements CSVHelperType{
    private static final String TAG = "CSVHelper";

    private final BufferedReader mReader;

    @Inject
    public CSVHelper(Context context) {
        InputStream is = context.getResources().openRawResource(R.raw.issues);
        mReader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
    }

    @Override
    public List<Issue> loadIssueDataFromCSV() {
        Log.d(TAG, "loadIssueDataFromCSV: ");
        List<Issue> issues = new ArrayList<>();
        try {
            String line = mReader.readLine();
            while ((line = mReader.readLine()) != null) {
                String[] tokens = line.split(",");
                Issue issue = new Issue();
                issue.setFirstName(removeQuotes(tokens[0]));
                issue.setLastName(removeQuotes(tokens[1]));
                issue.setIssueCount(Integer.valueOf(removeQuotes(tokens[2])));

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                String dateString = removeQuotes(tokens[3]);
                Date date = formatter.parse(dateString);
                issue.setDateOfBirth(date);

                issues.add(issue);
            }
        } catch (IOException | ParseException e1) {
            e1.printStackTrace();
        }
        return issues;
    }

    private String removeQuotes(String source){
        if (source == null || source.isEmpty()) {
            return "";
        } else {
            return source.replace("\"","");
        }
    }
}
