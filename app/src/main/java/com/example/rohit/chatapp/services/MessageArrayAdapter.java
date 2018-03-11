package com.example.rohit.chatapp.services;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rohit.chatapp.R;
import com.example.rohit.chatapp.services.model.Chat;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by rohit on 8/15/17.
 */

public class MessageArrayAdapter extends RecyclerView.Adapter<MessageArrayAdapter.ViewHolder> {

    private List<Chat> mMessages;
    private String mStudentName;

    public MessageArrayAdapter(Context context, String studentName, List<Chat> messages) {
        mMessages = messages;
        mStudentName = studentName;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int watson = 1;
        int layout = R.layout.student_message;
        if (viewType == watson) {
            layout = R.layout.message_view;
        }
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Chat message = mMessages.get(position);
        String text = message.getReformatText();
        String senderStr = message.getSender().substring(0,1).toUpperCase() + message.getSender().substring(1);
        viewHolder.setMessage(text);
        viewHolder.setUsername(senderStr);
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mMessages.get(position).getSender().trim().equals("watson")) {
            return 1;
        }
        return 0;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mUsernameView;
        private TextView mMessageView;

        public ViewHolder(View itemView) {
            super(itemView);

            mUsernameView = (TextView) itemView.findViewById(R.id.sender);
            mMessageView = (TextView) itemView.findViewById(R.id.message);
        }

        public void setUsername(String username) {
            if (null == mUsernameView) {
                return;
            }
            if (username.equals("Watson")){
                mUsernameView.setTextColor(Color.parseColor("#107ACA"));
                mUsernameView.setText(username);
            } else {
                mUsernameView.setText(mStudentName);
            }

        }

        public void setMessage(String message) {
            if (null == mMessageView) {
                return;
            }
            mMessageView.setText(message);
        }


    }
    public void addChats(List<Chat> chats) {
        for (Chat chat : chats) {
            mMessages.add(chat);
        }
    }

}