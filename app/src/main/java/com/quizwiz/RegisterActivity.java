package com.quizwiz;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends ActionBarActivity {

    Firebase reference = null;
    Firebase userMapRef = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        Firebase.setAndroidContext(this);
        reference =new Firebase(getString(R.string.FireBaseDBReference)+"/User");
        userMapRef = new Firebase(getString(R.string.FireBaseDBReference)+"/UserMap");

//
    }

    public void onRegister(View view) {

        EditText regEdittext=(EditText)findViewById(R.id.editText);
        String regEmailAddress=regEdittext.getText().toString();
        EditText regEdittex2 = (EditText)findViewById(R.id.editText2);
        final String usrname = regEdittex2.getText().toString();

        EditText regEdittex3 = (EditText)findViewById(R.id.editText3);
        String pass = regEdittex3.getText().toString();

        //  EditText regEdittex4 = (EditText)findViewById(R.id.editText11);
        //  String name = regEdittex4.getText().toString();

        User usr = new User(usrname, regEmailAddress,pass);

        Map<String, User> users = new HashMap<String, User>();
        users.put(usrname,usr);

        Firebase newPostRef = reference.push();
        newPostRef.setValue(users);

        final String postId = newPostRef.getKey();
        Log.d(" Entered key is :" , postId);

        final Intent intent = new Intent(this,HomePage.class);

        // to make usermap table
        userMapRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // store the values in map structure
                Map<String, String> usermaps = (Map<String, String>) snapshot.getValue();
                if (usermaps == null) {
                    Map<String, String> userMapName = new HashMap<String, String>();
                    userMapName.put(usrname, postId);
                    userMapRef.setValue(userMapName);
                } else {
                    usermaps.put(usrname, postId);
                    userMapRef.setValue(usermaps);
                }

                intent.putExtra("username",usrname);
                intent.putExtra("userKey",postId);
                startActivity(intent);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


    }

}
