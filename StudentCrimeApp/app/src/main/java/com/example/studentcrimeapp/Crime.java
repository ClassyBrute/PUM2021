package com.example.studentcrimeapp;

import java.util.Date;
import java.util.UUID;

public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }
    public Date getDate() {
        return mDate;
    }

    public UUID getId() {
        return mId;
    }

    public void setSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }
    public boolean isSolved() {
        return mSolved;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
    public String getTitle() {
        return mTitle;
    }
}
