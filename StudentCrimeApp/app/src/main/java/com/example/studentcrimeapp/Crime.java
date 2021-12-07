package com.example.studentcrimeapp;

import java.util.Date;
import java.util.UUID;

public class Crime {

    private int mId;
    private String mTitle;
    private int mIndex;
    private Date mDate = new Date();
    private boolean mSolved;

    public Crime(){

    }

    public Crime(int id, int mIndex, String mTitle, Date mDate, boolean mSolved){
        this(mIndex, mTitle, mDate, mSolved);
        this.mId = id;
    }

    public Crime(int mIndex, String mTitle, Date mDate, boolean mSolved){
        this.mIndex = mIndex;
        this.mTitle = mTitle;
        this.mDate = mDate;
        this.mSolved = mSolved;
    }

    public int getIndex() {
        return mIndex;
    }
    public void setIndex(int mIndex) {
        this.mIndex = mIndex;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }
    public Date getDate() {
        return mDate;
    }
    public Integer getIntDate() {
        return (int) mDate.getTime()/1000;
    }

//    public int getId() {
//        return mId;
//    }

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
