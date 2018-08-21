package com.mounica.woebot.model;

/**
 * Display Message model
 */
public class ModelRecyclerView {

    private String mText;
    private String mTimeStamp;
    private int mMessageType;

    public ModelRecyclerView(final String text, final String timeStamp, final int type) {
        mText = text;
        mTimeStamp = timeStamp;
        mMessageType = type;
    }

    public String getText() {
        return mText;
    }

    public String getTimeStamp() {
        return mTimeStamp;
    }

    public int getMessageType() {
        return mMessageType;
    }

}
