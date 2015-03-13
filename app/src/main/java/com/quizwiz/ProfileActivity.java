package com.quizwiz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;


public class ProfileActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
    }
    public void onLogout(View view) {
        Intent intent = new Intent(this,InitialLoginActivity.class);
        startActivity(intent);
    }


}
