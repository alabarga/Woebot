package com.mounica.woebot.view;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mounica.woebot.R;
import com.mounica.woebot.model.ModelRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for the recycler view, inflates sent or received message by type
 */
public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int MESSAGE_SENT = 1;
    private static final int MESSAGE_RECEIVED = 2;

    private List<ModelRecyclerView> mMessageList = new ArrayList<>();

    // Adds any new message to the list and updates the Recycler view
    public void addMessage(ModelRecyclerView message) {
        mMessageList.add(message);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(final int position) {
        if (mMessageList.get(position).getMessageType() == MESSAGE_RECEIVED) {
            return MESSAGE_RECEIVED;
        } else {
            return MESSAGE_SENT;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        if (viewType == MESSAGE_SENT) {
            return new SentMessageHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_send, parent, false));
        } else {
            return new ReceivedMessageHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_received, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ModelRecyclerView message = mMessageList.get(position);
        if (holder.getItemViewType() == MESSAGE_SENT) {
            SentMessageHolder sentMessageHolder = (SentMessageHolder) holder;
            sentMessageHolder.sent.setText(message.getText());
            sentMessageHolder.sentTimeStamp.setText(message.getTimeStamp());
        } else {
            ReceivedMessageHolder receivedMessageHolder = (ReceivedMessageHolder) holder;
            receivedMessageHolder.received.setText(message.getText());
            receivedMessageHolder.receivedTimeStamp.setText(message.getTimeStamp());
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }


    /**
     * Inner class to set sent message holder
     */
    private class SentMessageHolder extends RecyclerView.ViewHolder {

        private TextView sent;
        private TextView sentTimeStamp;

        public SentMessageHolder(final View itemView) {
            super(itemView);
            sent = itemView.findViewById(R.id.text_sent);
            sentTimeStamp = itemView.findViewById(R.id.text_sent_time);
        }
    }

    /**
     * Inner class to set receive message holder
     */
    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {

        private TextView received;
        private TextView receivedTimeStamp;

        public ReceivedMessageHolder(final View itemView) {
            super(itemView);
            received = itemView.findViewById(R.id.text_received);
            receivedTimeStamp = itemView.findViewById(R.id.text_received_time);
        }
    }
}
