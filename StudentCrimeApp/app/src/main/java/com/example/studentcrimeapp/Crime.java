package com.example.studentcrimeapp;

import java.util.Date;
import java.util.UUID;

public class Crime {

    private int mId;
    private String mTitle;
    private Date mDate = new Date();
    private boolean mSolved;

    public Crime(){

    }

    public Crime(int id, String mTitle, Date mDate, boolean mSolved){
        this(mTitle, mDate, mSolved);
        this.mId = id;
    }

    public Crime(String mTitle, Date mDate, boolean mSolved){
        this.mTitle = mTitle;
        this.mDate = mDate;
        this.mSolved = mSolved;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }
    public Date getDate() {
        return mDate;
    }
    public int getIntDate() {
        return (int) mDate.getTime()/1000;
    }

    public void setId(int mId) { this.mId = mId; }
    public int getId() {
        return mId;
    }

    public void setSolved(boolean mSolved) { this.mSolved = mSolved; }
    public boolean isSolved() { return mSolved; }

    public void setTitle(String mTitle) { this.mTitle = mTitle; }
    public String getTitle() { return mTitle; }
}
