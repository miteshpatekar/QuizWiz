package com.quizwiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class HomePage extends ActionBarActivity {

    String uname=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Firebase.setAndroidContext(this);

        // Save the session state username
        SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
        editor.putString("uname", "avelankar"); //set username in shared session variable
        editor.commit();

        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        uname = prefs.getString("uname", "No name defined");//"No name defined" is the default value.
        Toast.makeText(this,"Welcome"+uname,Toast.LENGTH_SHORT).show();

        // Firebase code
        // get the firebase db reference
        Firebase myFirebaseRef = new Firebase(getString(R.string.FireBaseDBReference));

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

        //to get the request List
        // Get a reference to our posts

        Firebase requestRef = new Firebase(getString(R.string.FireBaseDBReference)+"/User/"+uname+"/requestList");

        requestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Log.d("Values are ", snapshot.getValue().toString());

                // store the values in map structure
                Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();
                //iterate through the list
                for(Map.Entry<String, Object> entry : newPost.entrySet()) {
                    String key = entry.getKey(); // gets the key of object
                    Log.d("Key is", key);
                    Object value = entry.getValue();
                    Log.d("Vales is", entry.getValue().toString());
                }
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
        Intent i=new Intent(HomePage.this,Categories.class);
        i.putExtra("activity","quiz");
        startActivity(i);
    }

    public void Challenge(View v) {
        Intent i = new Intent(HomePage.this, Categories.class);
        i.putExtra("activity", "quiz");
        Intent i2 = new Intent(new Intent(HomePage.this, SelectChallenger.class));
        startActivity(i2);
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
        Intent i=new Intent(HomePage.this,Categories.class);
        i.putExtra("activity","post");
        startActivity(i);
    }

    public void AddFriend(View v)
    {
        startActivity(new Intent(HomePage.this,AddFriend.class ));
    }

    public void Requests(View v)
    {
        startActivity(new Intent(HomePage.this,Requests.class ));
    }

}

