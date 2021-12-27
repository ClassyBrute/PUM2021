package com.example.studentcrimeapp;

import java.util.Date;

public class Crime {

    private int mId;
    private String mTitle;
    private Date mDate = new Date();
    private boolean mSolved;
    private String mPicture;

    public Crime(){

    }

    public Crime(int id, String mTitle, Date mDate, boolean mSolved, String mPicture){
        this(mTitle, mDate, mSolved, mPicture);
        this.mId = id;
    }

    public Crime(String mTitle, Date mDate, boolean mSolved, String mPicture){
        this.mTitle = mTitle;
        this.mDate = mDate;
        this.mSolved = mSolved;
        this.mPicture = mPicture;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }
    public Date getDate() {
        return mDate;
    }

    public void setId(int mId) { this.mId = mId; }
    public int getId() {
        return mId;
    }

    public void setSolved(boolean mSolved) { this.mSolved = mSolved; }
    public boolean isSolved() { return mSolved; }

    public void setTitle(String mTitle) { this.mTitle = mTitle; }
    public String getTitle() { return mTitle; }

    public String getPicture() { return mPicture; }
}
