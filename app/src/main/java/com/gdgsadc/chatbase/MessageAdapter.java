package com.gdgsadc.chatbase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by dan on 23/06/17.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private final int LEFT = 0, RIGHT = 1;

    private String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private Context mContext;
    private ArrayList<MessageModel> messages;

    MessageAdapter(Context mContext, ArrayList<MessageModel> messages){
        this.mContext = mContext;
        this.messages = messages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ViewHolder viewHolder = null;
        switch (i){
            case LEFT:
                viewHolder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.message_item_left, viewGroup, false));
                break;
            case RIGHT:
                viewHolder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.message_item_right, viewGroup, false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.usernameTextView.setText(messages.get(i).getUserId());
        viewHolder.timeStampTextView.setText(Utils.getDate(new Date(messages.get(i).getTimestamp()), mContext));
        viewHolder.messageTextView.setText(messages.get(i).getMessage());
    }

    @Override
    public int getItemCount() {
        return messages!=null?messages.size():0;
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).getUserId().equals(uid)?RIGHT:LEFT;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView timeStampTextView, usernameTextView, messageTextView;

        ViewHolder(View itemView) {
            super(itemView);

            timeStampTextView = (TextView) itemView.findViewById(R.id.timeStampTextView);
            usernameTextView = (TextView) itemView.findViewById(R.id.usernameTextView);
            messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);


        }
    }


}
