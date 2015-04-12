package com.quizwiz;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.quizwiz.R;

import java.util.Map;

public class ChallengeStart extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_start);
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);

        final String uname = prefs.getString("uname", "No name defined");//"No name defined" is the default value.
        final TextView player1= (TextView)findViewById(R.id.player1);
        player1.setText(uname);

        final TextView player2= (TextView)findViewById(R.id.player1);

        // get the value of the friend challenged
        SharedPreferences prefs1 = getSharedPreferences("challengeFriend", MODE_PRIVATE);
        final String unameChallenger = prefs.getString("challengeFriend", "No name defined");//"No name defined" is the default value.

        Firebase requestRef = new Firebase(getString(R.string.FireBaseDBReference)+"/User/"+unameChallenger+"/joinRequest");

        requestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.getValue()!=null) {
                    // store the values in map structure
                    Map<String, Boolean> newRequests = (Map<String, Boolean>) snapshot.getValue();

                    for (Map.Entry<String, Boolean> entry : newRequests.entrySet()) {
                        String key = entry.getKey(); // gets the key of object
                        Log.d("Key is", key);
                        if (entry.getValue() == true) {
                            player2.setText(uname);
                        }
                    }
                }
                else
                {

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_challenge_start, menu);
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
}
