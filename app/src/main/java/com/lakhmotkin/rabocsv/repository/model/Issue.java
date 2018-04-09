package com.lakhmotkin.rabocsv.repository.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Igor Lakhmotkin on 24.02.2018, for HeartstoneAssessment.
 */
@Entity(
        tableName = "issues"
)
public class Issue implements Serializable{
    @NonNull
    @PrimaryKey
    private String lastName;
    private String firstName;
    private Integer issueCount;
    private Date dateOfBirth;

    public Issue(@NonNull String lastName, String firstName, Integer issueCount, Date dateOfBirth) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.issueCount = issueCount;
        this.dateOfBirth = dateOfBirth;
    }

    public Issue() {
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getDateOfBirthFormatted() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.format(dateOfBirth);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String mFirstName) {
        this.firstName = mFirstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getIssueCount() {
        return issueCount;
    }

    public void setIssueCount(Integer issueCount) {
        this.issueCount = issueCount;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getIssueCountString() {
        return String.valueOf(issueCount);
    }
}
