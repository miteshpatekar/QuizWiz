package com.quizwiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    String userKeyIntent=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Firebase.setAndroidContext(this);

        // get the username from login and register
        Intent intent=getIntent();
        if(intent.getStringExtra("username")!=null) {
            usrnameIntent = intent.getStringExtra("username");
        }
        if(intent.getStringExtra("userKey")!=null) {
            userKeyIntent = intent.getStringExtra("userKey");
        }

        // Save the session state username
        SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
        editor.putString("uname", usrnameIntent); //set username in shared session variable
        editor.commit();

        // Save the session state username key
        SharedPreferences.Editor editor1 = getSharedPreferences("user", MODE_PRIVATE).edit();
        editor.putString("ukey", userKeyIntent); //set username in shared session variable
        editor.commit();

        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        uname = prefs.getString("uname", "No name defined");//"No name defined" is the default value.
        Toast.makeText(this,"Welcome " +usrnameIntent,Toast.LENGTH_SHORT).show();

        //to get the request List Count
        final Firebase requestRef =
                new Firebase(getString(R.string.FireBaseDBReference)+"/User/");

        requestRef.child(userKeyIntent).child(uname).child("requestList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Button reqBtn = null;
                if (snapshot.getValue() != null) {
                    // store the values in map structure
                    Map<String, Boolean> newRequests = (Map<String, Boolean>) snapshot.getValue();
                    reqBtn = (Button) findViewById(R.id.RequestBtn);

                    int cnt = 0;
                    //iterate through the list

                    for (Map.Entry<String, Boolean> entry : newRequests.entrySet()) {
                        if (entry.getValue() == false) {
                            cnt++;
                        }
                    }
                    if (cnt != 0)
                        reqBtn.setText("Requests(" + cnt + ")");
                } else {
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });



        // alert to accept request
        final Intent i1 = new Intent(HomePage.this, ChallengeStart.class);

        final Firebase ref=  new Firebase(getString(R.string.FireBaseDBReference));
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestRef.child(userKeyIntent).child(uname).child("joinRequest").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if(snapshot.getValue()!=null) {
                                    // store the values in map structure
                                    Map<String, String> reqFriendJoin = (Map<String, String>) snapshot.getValue();

                                    String gkey=null;
                                    String player1=null;

                                    for (Map.Entry<String, String> entry : reqFriendJoin.entrySet()) {
                                        gkey=entry.getKey();
                                      //  Log.d("strrr",gkey+" "+ entry.getValue().toString());
                                       // Game gm=entry.g
                                        player1=entry.getKey().toString();
                                        gkey=entry.getValue().toString();
                                    }

                                    Game gm1=new Game(player1,uname);
                                    Firebase hopperRef = ref.child("Game").child(gkey);

                                    Map<String, Game> gamesup = new HashMap<String, Game>();
                                    gamesup.put("game", gm1);

                                    hopperRef.setValue(gamesup);

                                    // i1.putExtra("userChallenger", );
                                    startActivity(i1);
                                }

                                else{}
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                                System.out.println("The read failed: " + firebaseError.getMessage());
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });


        //to get the join request List
        requestRef.child(userKeyIntent).child(uname).child("joinRequest").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.getValue()!=null) {
                    // store the values in map structure
                    Map<String, String> newRequests = (Map<String, String>) snapshot.getValue();

                    for (Map.Entry<String, String> entry : newRequests.entrySet()) {

                            AlertDialog alert = builder.setMessage("Play Quiz request from " + entry.getKey()).create();
                            alert.show();
                    }
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
        startActivity(i);
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

