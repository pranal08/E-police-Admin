package com.example.arafat_213.e_policephase2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.arafat_213.e_policephase2.Adapters.NotificationAdapter;
import com.example.arafat_213.e_policephase2.Models.Notification;
import com.example.arafat_213.e_policephase2.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    NotificationAdapter notificationAdapter;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_arrow_back_black_24dp);

        mRecyclerView = findViewById(R.id.notificationRV);
        mProgressBar = findViewById(R.id.notificationPB);
        mProgressBar.setVisibility(View.VISIBLE);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(layoutManager);
//        notificationArrayList = new ArrayList<Notification>();

//        for (int i=0; i<10; i++)
//            notificationArrayList.add(new Notification("NOTIFICATION TYPE" , " Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s."));
////        No policemanAdapter = new PolicemanAdapter(policemanArrayList);
////        policeRecyclerView.setAdapter(policemanAdapter);
//        NotificationAdapter notificationAdapter = new NotificationAdapter(notificationArrayList);
//        mRecyclerView.setAdapter(notificationAdapter);

        FirebaseApp.initializeApp(this);

        Query notificationRef = FirebaseDatabase.getInstance()
                .getReference()
                .child("notifications");


        FirebaseRecyclerOptions<Notification> options =
                new FirebaseRecyclerOptions.Builder<Notification>()
                        .setQuery(notificationRef, Notification.class)
                        .build();
        notificationAdapter = new NotificationAdapter(options, getApplicationContext()) {
            @Override
            public void onDataChanged() {
                super.onDataChanged();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        };
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setReverseLayout(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(notificationAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(NotificationActivity.this, NewNotificationActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        notificationAdapter.startListening();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
