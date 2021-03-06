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

import java.util.ArrayList;
import java.util.Map;


public class Requests extends ActionBarActivity {

    String uname=null;
    String ukey=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        // get the current username
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        uname = prefs.getString("uname", "No name defined");//"No name defined" is the default value.
        // get the current userkey
        ukey = prefs.getString("ukey", "No name defined");

        //generate list
        final ArrayList<String> list = new ArrayList<String>();

        //instantiate custom adapter
        final CustomListAdapter adapter = new CustomListAdapter(list,this,uname);

        //to get the request List
        // Get a reference to our posts
        Firebase requestRef = new Firebase(getString(R.string.FireBaseDBReference)+"/User/"+ukey+"/"+uname+"/requestList");

        requestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                TextView tv=null;
                if(snapshot.getValue()!=null) {
                    ListView lView = (ListView) findViewById(R.id.listViewRequest);
                    list.clear();

                    // store the values in map structure
                    Map<String, Boolean> newRequests = (Map<String, Boolean>) snapshot.getValue();
                    tv = (TextView) findViewById(R.id.RequestText);
                    Log.d("requestsss ", Integer.toString(newRequests.size()));

                    int cnt=0;
                    //iterate through the list

                    for (Map.Entry<String, Boolean> entry : newRequests.entrySet()) {
                        String key = entry.getKey(); // gets the key of object
                        Log.d("Key is", key);
                        if (entry.getValue() == false) {
                            list.add(key);
                            cnt++;
                        }
                        //handle listview and assign adapter
                        lView.setAdapter(adapter);
                        Log.d("Vales is", entry.getValue().toString());
                    }
                    if(cnt==0)
                        tv.setText("No more Request !");
                    else
                        tv.setText("Requests ("+Integer.toString(cnt)+")");
                }
                else
                {
                    tv.setText("No Requests");
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }


 }
