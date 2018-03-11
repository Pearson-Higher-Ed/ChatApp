package com.example.rohit.chatapp;

import android.support.v7.widget.Toolbar;

import com.github.nkzawa.socketio.client.Socket;
import android.content.Intent;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rohit.chatapp.services.AuthInterceptor;
import com.example.rohit.chatapp.services.ChatApiService;
import com.example.rohit.chatapp.services.MessageArrayAdapter;
import com.example.rohit.chatapp.services.model.Chat;
import com.example.rohit.chatapp.services.model.MessageList;
import com.example.rohit.chatapp.services.model.SentMessage;
import com.example.rohit.chatapp.services.model.UserMessage;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



public class ChatActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private static final String STUDENT = "Hari";
    private static final String TOKEN = "eyJhbGciOiJSUzUxMiIsImtpZCI6ImsxMDY5NDgxOTAifQ.eyJleHAiOjE1MDY4MTEwMTksInN1YiI6ImZmZmZmZmZmNTdhYTExZjFlNGIwYjk3ZmY0YTkwYzYzIiwic2Vzc2lkIjoiZjBkOWJhYjQtNThiNy00ZDk0LThkOTgtNDliY2Y1MDM3OTQyIiwiaGNjIjoiVVMiLCJ0eXBlIjoiYXQiLCJpYXQiOjE1MDY4MDAyMTl9.QFeBfHVlXtt0e6kN7ovBOXkGgxlb7vNBMy9-O_RQ6939HUJ-m1voBICdbZ3CAC2MMYBB70greWlmtt_o2spsL3XbHzokxgrpsfOTyrI1bJzPvUe1aDWigSvbkOpMveU_ldsCWaMKnvAmrZPoD7Rlf9eTaJdMIMXeS8ZybnF3yJg";
    private static final String USER_ID = "ffffffff57aa11f1e4b0b97ff4a90c63";
    private static final String BASE_URL = "https://dialogmanagerservice-qac.dev-prsn.com";
    private static final String SOURCE = "revel-ilp-qac.pearson.com";
    private static final String DIALOG_ENTRY_POINT = "KC";
    private static final String DIALOG_ID = "2e66edf0-be10-43f9-9ecc-6a0de14a46bb";
    private static final String LEARNING_CONTEXT_ID = "596f000ce4b0c2226c534d7d";
    private static final String SESSION_ID = "c48f7080-82d1-11e7-a2b4-b064c33aa77c";
    private String conversationId;
    private static final String EVENT = "converse";
    private static final String LEARNING_OBJECTIVE_ID = "8.4";
    private static final String CORRELATION_ID = "872a0f3e-ba31-4924-9fa1-f8c18f1610a6";
    private static final String GOAL_FRAMEWORK_ID = "feldman_2017_02_2";
    private ProgressBar progressBar;
    private static Retrofit retrofit = null;
    MessageArrayAdapter adapter = null;
    List<Chat> arrayOfChats;
    private LinearLayoutManager mLinearLayoutManager;

    @BindView(R.id.chat_view)
    RecyclerView listView;

    public Socket mSocket;
    {
        try {
            mSocket = IO.socket("https://chatservice-qac.dev-openclass.com");
        } catch (URISyntaxException e) {
            Log.i("Rohit","mSocket not working", e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Rohit","before connect");
        setContentView(R.layout.chat_activity);
        Intent intent = getIntent();
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on("processingMessage",onProcessingMessage);
        mSocket.on("errorMessage", onErrorMessage);
        mSocket.on("authenticated", onAuthentication);
        mSocket.on("unauthenticated", onUnauthenticated);
        mSocket.on("providerMessage", onProviderMessage);
        mSocket.connect();
        ButterKnife.bind(this);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        progressBar = (ProgressBar) findViewById(R.id.indeterminateBar);

        getAPIData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MessageList>>() {
                    @Override
                    public void accept(List<MessageList> messageLists) throws Exception {
                        Log.i(TAG , messageLists.toString());
//                        updateChats(messageLists.get(0));
                        for (MessageList messageList : messageLists) {
                            updateChats(messageList);
                        }
                        conversationId = messageLists.get(messageLists.size()-1).getConversationId();
                        UserMessage userMessage = new UserMessage(messageLists.get(0), GOAL_FRAMEWORK_ID, USER_ID,
                                CORRELATION_ID, LEARNING_OBJECTIVE_ID, EVENT, SOURCE, DIALOG_ENTRY_POINT,
                                LEARNING_CONTEXT_ID, SESSION_ID, conversationId);
                        String message = new Gson().toJson(userMessage);
                        Log.i(TAG, message);
                        mSocket.emit("userMessage", new JSONObject(message));
                    }
                });

    }
    private void updateChats(MessageList messageList){
        if (messageList != null) {
            if(adapter == null) {
                arrayOfChats = messageList.getChats();
                adapter = new MessageArrayAdapter(this, STUDENT, arrayOfChats);
                listView.setAdapter(adapter);
                mLinearLayoutManager = new LinearLayoutManager(this);
                listView.setLayoutManager(mLinearLayoutManager);
                mLinearLayoutManager.scrollToPosition(adapter.getItemCount() - 1);
            } else {
                adapter.addChats(messageList.getChats());
                adapter.notifyDataSetChanged();
                mLinearLayoutManager.scrollToPosition(adapter.getItemCount() - 1);
            }
        }
    }

    public Observable<List<MessageList>> getAPIData() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient
                    .addInterceptor(logging)
                    .addInterceptor(new AuthInterceptor(TOKEN));
            retrofit = new Retrofit.Builder()
                    .client(httpClient.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        ChatApiService chatApiService = retrofit.create(ChatApiService.class);
        return chatApiService.fetchPreviousMessages(SOURCE, LEARNING_CONTEXT_ID, USER_ID, DIALOG_ID);

    }
    private  Emitter.Listener onProviderMessage = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            Log.i("Rohit","onProviderMessage" + args[0]);
            JSONObject jsonObject = (JSONObject) args[0];
            final MessageList messageList = new MessageList();
            try {
                JSONArray jsonChats = jsonObject.getJSONArray("message");
                List<Chat> chats = new ArrayList<Chat>();
                for (int i = 0; i < jsonChats.length(); i++) {
                    JSONObject temp = jsonChats.getJSONObject(i);
                    Chat chat = new Chat();
                    String sender = temp.getString("sender");
                    JSONArray chatText = temp.optJSONArray("chatText");
                    String chatTextLine = "";
                    if (chatText == null) {
                        Log.i(TAG, "Transcript loop");
                        JSONArray chatTranscript = temp.optJSONArray("Transcript");
                        Log.i(TAG, chatTranscript.toString());
                        for (int j = 0; j < chatTranscript.length(); j++ ) {
                            if (j != 0) {
                                chatTextLine += "\n";
                            }
                            chatTextLine += chatTranscript.getString(j);
                        }
                    } else {
                        for (int j = 0; j < chatText.length(); j++ ) {
                            chatTextLine += (chatText.getString(j) + "\n");
                        }
                    }
                    chat.setChatText(chatTextLine);
                    chat.setSender(sender);
                    chats.add(chat);
                }
                messageList.setChats(chats);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateChats(messageList);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

        }
    };
    private Emitter.Listener onProcessingMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i("Rohit", "onProcessingMessage");
            for (Object arg : args) {
                Log.i("Rohit",arg.toString());
            }
        }
    };

    private Emitter.Listener onUnauthenticated = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            Log.i("Rohit", "onUnauthenticated");
            for (Object arg : args) {
                Log.i("Rohit",arg.toString());
            }
        }
    };

    private Emitter.Listener onErrorMessage = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            Log.i("Rohit", "onErrorMessage" + args[0]);
        }
    };
    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i("Rohit", "onConnect");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("token", TOKEN);
                jsonObject.put("userId", USER_ID);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mSocket.emit("authenticate", jsonObject);
        }

    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i("Rohit", "onDisconnect");
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i("Rohit", "onConnectError");
        }
    };


    public void attemptSend(View view) {

        EditText mInputMessageView = (EditText) findViewById(R.id.edit_text_view);
        String message = mInputMessageView.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            return;
        }

        mInputMessageView.setText("");
        MessageList messageList= new MessageList();
        Chat chat = new Chat();
        chat.setChatText(message);
        chat.setSender(STUDENT);
        List<Chat> chats = new ArrayList<Chat>();
        chats.add(chat);
        messageList.setChats(chats);
        updateChats(messageList);
//        scrollListViewToBottom();
        String[] mList = new String[1];
        mList[0] = message;
        SentMessage userMessage = new SentMessage(mList, GOAL_FRAMEWORK_ID, USER_ID,
                CORRELATION_ID, LEARNING_OBJECTIVE_ID, EVENT, SOURCE, DIALOG_ENTRY_POINT,
                LEARNING_CONTEXT_ID, SESSION_ID, conversationId);
        String userM = new Gson().toJson(userMessage);
        Log.i(TAG, "userM " + userM);
        progressBar.setVisibility(View.VISIBLE);
        try {
            mSocket.emit("userMessage", new JSONObject(userM));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    private Emitter.Listener onAuthentication = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            Log.i("rohit", "Test");
            for (Object arg : args) {
                Log.i("rohit", "" + arg);
            }
        }
    };
}
