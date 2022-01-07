package com.example.wydatkiapp;

import java.util.Date;

public class Expense {

    private int mId;
    private String mOwner;
    private int mAmount;
    private Date mDate = new Date();

    public Expense(){}

    public Expense(int id, String mOwner, int mAmount, Date mDate) {
        this(mOwner, mAmount, mDate);
        this.mId = id;
    }

    public Expense(String mOwner, int mAmount, Date mDate) {
        this.mOwner = mOwner;
        this.mAmount = mAmount;
        this.mDate = mDate;
    }

    public void setOwner(String mOwner) { this.mOwner = mOwner; }
    public String getOwner() { return mOwner; }

//    public void setDate(Date mDate) {
//        this.mDate = mDate;
//    }
    public Date getDate() {
        return mDate;
    }

    public void setId(int mId) { this.mId = mId; }
    public int getId() {
        return mId;
    }

    public void setAmount() { this.mAmount = mAmount; }
    public int getAmount() { return mAmount; }
}
