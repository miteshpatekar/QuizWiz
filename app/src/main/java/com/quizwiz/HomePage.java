package com.quizwiz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;


public class HomePage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Firebase.setAndroidContext(this);

        // Firebase code
        // get the firebase db reference
        Firebase myFirebaseRef = new Firebase("https://popping-heat-8474.firebaseio.com/");

        Query queryRef = myFirebaseRef.orderByValue();
        // add event listener
        queryRef.addChildEventListener(new ChildEventListener() {
            // to read the data when child of object is added
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                System.out.println("Key is" + snapshot.getKey() + " value is " + snapshot.getValue());
                Log.d("Msg","Key is" + snapshot.getKey() + " value is " + snapshot.getValue());
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
            public void onCancelled(FirebaseError firebaseError) {

            }
            // ....
        });

        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }



    public void QuickQuiz(View v)
    {
        //Intent i=new Intent(this,Categories.class);
        startActivity(new Intent(HomePage.this, Categories.class));
    }

    public void Challenge(View v)
    {
        startActivity(new Intent(HomePage.this, SelectChallenger.class));
    }

    public void InviteFriends(View v)
    {
        startActivity(new Intent(HomePage.this, SelectChallenger.class));
    }

    public void Profile(View v)
    {
        startActivity(new Intent(HomePage.this, ProfileActivity.class));
    }

    public void PostQuestion(View v)
    {
        startActivity(new Intent(HomePage.this,PostQuiz.class ));
    }
}
