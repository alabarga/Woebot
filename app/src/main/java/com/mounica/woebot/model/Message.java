package com.mounica.woebot.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Message model
 */
public class Message {

    public static final String START = "start";
    public static final String BYE = "bye";

    private String mId;
    private String mText;
    private String mTag;
    private String mLesson;

    private List<Route> mRouteList = new ArrayList<>();
    private List<String> mReplies;
    private List<String> mPayloads;
    private List<String> mRoutes;

    public Message(String id, String text, List<String> replies, List<String> payloads, List<String> routes,
            String tag, String lesson) {

        mId = id;
        mText = text;
        mReplies = replies;
        mRoutes = routes;
        mPayloads = payloads;
        mLesson = lesson;

        if (tag.contains(START)) {
            mTag = START;
        } else if (tag.equals(BYE)) {
            mTag = BYE;
        } else {
            mTag = " ";
        }

        for (int index = 0; index < replies.size(); index++) {
            mRouteList.add(new Route(mReplies.get(index), mPayloads.get(index), mRoutes.get(index), mTag));
        }
    }

    public List<String> getReplies() {
        return mReplies;
    }

    public String getText() {
        return mText;
    }

    public String getTag() {
        return mTag;
    }

    public Route getRoute(String reply) {
        for (Route route : mRouteList) {
            if (route.getReply().equals(reply)) {
                return route;
            }
        }
        return null;
    }
}
