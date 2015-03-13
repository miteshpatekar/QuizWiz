package com.quizwiz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;


public class HomePage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    public void QuickQuiz(View v)
    {
        //Intent i=new Intent(this,Categories.class);
        startActivity(new Intent(HomePage.this, Categories.class));
    }

    public void Challenge(View v)
    {
        startActivity(new Intent(HomePage.this, SelectChallenger.class));
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
        startActivity(new Intent(HomePage.this,PostQuiz.class ));
    }
}
