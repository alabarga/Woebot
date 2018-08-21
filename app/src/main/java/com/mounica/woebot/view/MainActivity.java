package com.mounica.woebot.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.mounica.woebot.viewmodel.MessageViewModel;
import com.mounica.woebot.R;
import com.mounica.woebot.model.Message;
import com.mounica.woebot.model.ModelRecyclerView;
import com.mounica.woebot.model.Route;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * MainActivity to display the conversation
 */
public class MainActivity extends AppCompatActivity implements OnClickListener {

    private static final int MAX_REPLIES = 4;
    private static final int MESSAGE_SENT = 1;
    private static final int MESSAGE_RECEIVED = 2;
    private static final String START_MESSAGE = "EIC";

    private Button mReplyButton1;
    private Button mReplyButton2;
    private Button mReplyButton3;
    private Button mReplyButton4;
    private Button[] mReplies;
    private MessageViewModel mMessageViewModel;
    private MessageAdapter mMessageAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mReplyButton1 = findViewById(R.id.button_reply1);
        mReplyButton2 = findViewById(R.id.button_reply2);
        mReplyButton3 = findViewById(R.id.button_reply3);
        mReplyButton4 = findViewById(R.id.button_reply4);
        createButtonArray();

        mMessageViewModel = ViewModelProviders.of(this).get(MessageViewModel.class);
        mRecyclerView = findViewById(R.id.rv_msg);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMessageAdapter = new MessageAdapter();
        mRecyclerView.setAdapter(mMessageAdapter);

        // Set the first message from Woebot
        setWoebotResponse(START_MESSAGE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(final View v) {
        Button reply = (Button) v;
        setUserResponse(reply.getText().toString());
        hideAllButtons();
        Route route = (Route) reply.getTag();

        // Check if the tag is bye and close the app accordingly
        if (route.getTag() == Message.BYE) {
            closeActivity();
        } else {
            setWoebotResponse(route.getRoute());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setWoebotResponse(String messageId) {
        final Message message = mMessageViewModel.getMessage(messageId);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!message.getText().contains("|")) {
                    updateAdapter(message.getText(), MESSAGE_RECEIVED);
                } else {
                    if (message.getText().contains("|")) {
                        for (String messageText : message.getText().split("\\|")) {
                            updateAdapter(messageText, MESSAGE_RECEIVED);
                        }
                    }
                }
                setButtons(message);
            }
        }, 1000);
    }

    private void setUserResponse(final String reply) {
        updateAdapter(reply, MESSAGE_SENT);
    }

    private void updateAdapter(final String text, final int type) {
        mMessageAdapter.addMessage(new ModelRecyclerView(text, getTimeStamp(),
                type));
        mRecyclerView.scrollToPosition(mMessageAdapter.getItemCount() - 1);
    }

    public static String getTimeStamp() {
        return new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
    }

    private void createButtonArray() {
        mReplies = new Button[MAX_REPLIES];
        mReplies[0] = mReplyButton1;
        mReplies[1] = mReplyButton2;
        mReplies[2] = mReplyButton3;
        mReplies[3] = mReplyButton4;
    }

    private void setButtons(final Message message) {
        int size = message.getReplies().size() - 1;
        for (int index = size; index >= 0; index--) {
            mReplies[index].setText(message.getReplies().get(size - index));
            mReplies[index].setTag(message.getRoute(message.getReplies().get(size - index)));
            mReplies[index].setVisibility(View.VISIBLE);
            mReplies[index].setOnClickListener(this);
        }
    }

    private void closeActivity() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);
    }

    private void hideAllButtons() {
        for (Button replyButton : mReplies) {
            replyButton.setVisibility(View.GONE);
        }
    }
}
