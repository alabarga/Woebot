package com.mounica.woebot.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mounica.woebot.model.Message;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Viewmodel to parse the json file and load into a Map
 */
public class MessageViewModel extends AndroidViewModel {

    private static final String TAG = "MessageViewModel";
    private Map<String, Message> mMessageMap;

    /**
     * Constructor to get the application context
     */
    public MessageViewModel(@NonNull final Application application) {
        super(application);
        mMessageMap = new HashMap<>();
        loadMessages();
    }

    /**
     * Load messages into the map from json file
     */
    public void loadMessages() {
        String messagesJson = getJsonStringFromFile("allornothing.json");
        if (messagesJson == null) {
            return;
        }

        try {
            JSONObject messages = new JSONObject(messagesJson);
            Iterator keys = messages.keys();
            while (keys.hasNext()) {
                String messageId = keys.next().toString();
                JSONObject jsonObject = messages.getJSONObject(messageId);
                Message message = new Message(jsonObject.getString("id"),
                        jsonObject.getString("text"),
                        arrayToList(jsonObject.getJSONArray("replies")),
                        arrayToList(jsonObject.getJSONArray("payloads")),
                        arrayToList(jsonObject.getJSONArray("routes")),
                        jsonObject.getString("tag"),
                        jsonObject.getString("lesson"));
                mMessageMap.put(messageId, message);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    /***
     * Simple array to list conversion function
     * @param jsonArray
     * @return jsonList
     */
    private List<String> arrayToList(JSONArray jsonArray) {
        List<String> jsonList = new ArrayList<>();
        if (jsonArray == null) {
            return jsonList;
        }

        try {
            for (int index = 0; index < jsonArray.length(); index++) {
                jsonList.add(jsonArray.getString(index));
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return jsonList;
    }

    /**
     * Converts the json file to a string
     *
     * @return json string
     */
    private String getJsonStringFromFile(String fileName) {
        String json = null;
        InputStream inputStream = null;
        try {
            inputStream = getApplication().getApplicationContext().getAssets().open(fileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
        return json;
    }

    /***
     * Returns corresponding Message object based on the id
     * @param id
     * @return message
     */
    public Message getMessage(String id) {
        if (id == null) {
            return null;
        }
        return mMessageMap.get(id);
    }
}
