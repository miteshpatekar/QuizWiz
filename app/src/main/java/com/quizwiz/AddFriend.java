package com.quizwiz;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.quizwiz.R;

import java.util.HashMap;
import java.util.Map;

public class AddFriend extends ActionBarActivity {

    Firebase ref=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        ref=new Firebase(getString(R.string.FireBaseDBReference)+"/User");
      //  Firebase usersRef = ref.child("users");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_friend, menu);
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
    public void AddFriendBtn(View v)
    {

        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        String uname = prefs.getString("uname", "No name defined");//"No name defined" is the default value.
        EditText edtext=(EditText)findViewById(R.id.AddFriendText);
        String unameFriend=edtext.getText().toString();
        Log.d("username ", uname + " " + unameFriend);
        Map<String, Boolean> reqName = new HashMap<String, Boolean>();
        reqName.put(uname, false);
        ref.child(unameFriend).child("requestList").setValue(reqName);
        Toast.makeText(this,"Friend Request sent to "+ unameFriend,Toast.LENGTH_SHORT).show();
    }
}
