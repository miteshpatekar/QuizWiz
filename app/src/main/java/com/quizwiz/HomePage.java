package com.quizwiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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
    String usrnameIntent=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Firebase.setAndroidContext(this);

        // get the username from login and register
       // Intent intent=getIntent();
        //if(intent.getStringExtra("username")!=null) {
          //  usrnameIntent = intent.getStringExtra("username");
        //}
        usrnameIntent="avelankar";

        // Save the session state username
        SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
        //editor.putString("uname", usrnameIntent); //set username in shared session variable
        editor.commit();

        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        uname = prefs.getString("uname", "No name defined");//"No name defined" is the default value.
        Toast.makeText(this,"Welcome " +usrnameIntent,Toast.LENGTH_SHORT).show();

        //to get the request List Count
        Firebase requestRef = new Firebase(getString(R.string.FireBaseDBReference)+"/User/"+uname+"/requestList");

        requestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Button reqBtn=null;
                if(snapshot.getValue()!=null) {
                    // store the values in map structure
                    Map<String, Boolean> newRequests = (Map<String, Boolean>) snapshot.getValue();
                    reqBtn = (Button) findViewById(R.id.RequestBtn);

                    int cnt=0;
                    //iterate through the list

                    for (Map.Entry<String, Boolean> entry : newRequests.entrySet()) {
                        if (entry.getValue() == false) {
                            cnt++;
                        }
                    }
                    if(cnt!=0)
                        reqBtn.setText("Requests("+cnt+")");
                }
                else{}

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
        i.putExtra("activity", "challenge");
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

