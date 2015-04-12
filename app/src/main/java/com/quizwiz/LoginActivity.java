package com.quizwiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;


public class LoginActivity extends ActionBarActivity {

    Firebase reference = null;
    Firebase userMapRef = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Firebase.setAndroidContext(this);
        reference =new Firebase(getString(R.string.FireBaseDBReference)+"/User");
        userMapRef = new Firebase(getString(R.string.FireBaseDBReference)+"/UserMap");
    }

    public void onLogin(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });


        final Intent intent = new Intent(this,HomePage.class);

        EditText loginUname=(EditText)findViewById(R.id.username);
        final String username=loginUname.getText().toString();

        EditText loginPwd=(EditText)findViewById(R.id.password);
        final String pwd=loginPwd.getText().toString();

        userMapRef.child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                // store the values in map structure
               if(snapshot.getValue()==null)
               {
                   AlertDialog alert = builder.setMessage("Username "+username+ " doesnt exists !").create();
                   alert.show();
               }
                else {
                   Log.d("yesssssss", snapshot.getValue().toString());
                   final String userKey=snapshot.getValue().toString();

                   reference.child(snapshot.getValue().toString()).child(username).child("password").addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot snapshot) {

                           // store the values in map structure
                           if (snapshot.getValue() == null) {

                           } else {
                               if (snapshot.getValue().toString().equals(pwd)) {
                                   intent.putExtra("username", username);
                                   intent.putExtra("userKey",userKey);
                                   startActivity(intent);
                               }
                               else
                               {
                                   AlertDialog alert = builder.setMessage("Incorrect Password..!").create();
                                   alert.show();
                               }
                           }

                       }

                       @Override
                       public void onCancelled(FirebaseError firebaseError) {
                           System.out.println("The read failed: " + firebaseError.getMessage());
                       }
                   });

               }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

}
