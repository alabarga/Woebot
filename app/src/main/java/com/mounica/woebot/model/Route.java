package com.mounica.woebot.model;

/**
 * Model for Route
 */
public class Route {

    private String mReply;
    private String mPayload;
    private String mRoute;
    private String mTag;

    public Route(final String reply, final String payload, final String route, String tag) {
        mReply = reply;
        mPayload = payload;
        mRoute = route;
        mTag = tag;
    }

    public String getReply() {
        return mReply;
    }

    public String getRoute() {
        return mRoute;
    }

    public String getTag() {
        return mTag;
    }
}
