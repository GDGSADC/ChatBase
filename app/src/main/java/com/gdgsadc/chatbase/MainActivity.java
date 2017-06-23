package com.gdgsadc.chatbase;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private ArrayList<MessageModel> messages;
    private MessageAdapter messageAdapter;

    TextInputEditText inputEditText;

    private String message;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messages = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        mAuth
                .signInAnonymously()
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    initMessages();
                }
                findViewById(R.id.progress_bar).setVisibility(View.GONE);
            }
        });

        inputEditText = (TextInputEditText) findViewById(R.id.messageTextInputEditText);
        findViewById(R.id.sendFloatingActionButton).setOnClickListener(this);

    }

    void initMessages(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter = new MessageAdapter(this, messages);
        recyclerView.setAdapter(messageAdapter);

        loadData();
    }

    void loadData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = firebaseDatabase.getReference().child(Constants.firebaseMessageRoot);
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
                messages.add(messageModel);
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void saveData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = firebaseDatabase.getReference().child(Constants.firebaseMessageRoot);
        DatabaseReference databaseReference = mRef.push();
        String key = databaseReference.getKey();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        MessageModel messageModel = new MessageModel(new Date().toString(), uid, key, getEditTextString());
        databaseReference.setValue(messageModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    recyclerView.scrollToPosition(messages.size()-1);
                } else {
                    inputEditText.setText(message);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sendFloatingActionButton:
                saveData();
                break;
        }
    }

    String getEditTextString(){
        message = inputEditText.getText().toString();
        inputEditText.setText("");
        findViewById(R.id.root_view).requestFocus();
        return message;
    }

}
