package com.quizwiz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectChallenger extends ActionBarActivity {
    String uname=null;
    String ukey=null;
    Firebase ref=null;
    Firebase userMapRef=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_challenger);
        ref=new Firebase(getString(R.string.FireBaseDBReference)+"/User");
        userMapRef=new Firebase(getString(R.string.FireBaseDBReference)+"/UserMap");

        // get the current Username
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        uname = prefs.getString("uname", "No name defined");//"No name defined" is the default value.
        // get the current user key
        ukey=prefs.getString("ukey", "No name defined");
        Log.d("userrnnnnn",uname);

        // to have a list of challengers
        final ListView listview = (ListView) findViewById(R.id.listView);
        final ArrayList<String> list = new ArrayList<String>();

        // to bind the list of challengers to UI
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);

        // Get a reference to our friends List
        final Firebase ref = new Firebase(getString(R.string.FireBaseDBReference)+"/User/");


        ref.child(ukey).child(uname).child("friendsList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                // store the values in map structure
                Map<String, Boolean> friendsList = (Map<String, Boolean>) snapshot.getValue();
                //iterate through the list
                if (friendsList != null) {
                    for (Map.Entry<String, Boolean> entry : friendsList.entrySet()) {
                        String key = entry.getKey(); // gets the key of object
                        Log.d("sssssffffffff", key);
                        list.add(key);
                        listview.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String unameChallenger = (String) parent.getItemAtPosition(position);
                SharedPreferences.Editor editor = getSharedPreferences("challengeFriend", MODE_PRIVATE).edit();
                editor.putString("challengeFriend", unameChallenger); //set username in shared session variable
                editor.commit();

                // Toast.makeText(this,"name is "+item,Toast.LENGTH_SHORT).show();

                // create game
                final Firebase refGame = new Firebase(getString(R.string.FireBaseDBReference)+"/Game/");
                Game quizgame= new Game(uname);
                Map<String, Game> games = new HashMap<String, Game>();
                games.put("game",quizgame);
                Firebase newPostRef = refGame.push();
                newPostRef.setValue(games);
                final String gameId = newPostRef.getKey();
                Log.d(" Entered key is :" , gameId);


                // Save value in Join Friend
                // set in his own list
                Map<String, String> reqFriend = new HashMap<String, String>();
                reqFriend.put(unameChallenger,gameId);
                // add in joinRequest List
                ref.child(ukey).child(uname).child("joinRequest").setValue(reqFriend);


                // set in challengers list
                final Map<String, String> challengeFriend = new HashMap<String, String>();
                challengeFriend.put(uname,gameId);

                userMapRef.child(unameChallenger).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        // store the values in map structure
                        if(snapshot.getValue()==null)
                        {
                           // AlertDialog alert = builder.setMessage("Username "+username+ " doesnt exists !").create();
                           // alert.show();
                        }
                        else {
                            Log.d("yesssssss", snapshot.getValue().toString());
                            final String userKey=snapshot.getValue().toString();

                            ref.child(userKey).child(unameChallenger).child("joinRequest").setValue(challengeFriend);
                            Log.d("username selected is- ",unameChallenger);
                            startActivity(new Intent(SelectChallenger.this,ChallengeStart.class));

                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });



            }
        });
    }

    // Class to bind the List of items
    private class StableArrayAdapter extends ArrayAdapter<String> {

        private ArrayList<String> list = new ArrayList<String>();
        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return 0;
            //return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
