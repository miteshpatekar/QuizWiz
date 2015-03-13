package com.quizwiz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;


public class InitialLoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initital_login);
    }

    public void onRegister(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    public void onLogin(View view) {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public void onGuest(View view) {
        Intent intent = new Intent(this,HomePage.class);
        startActivity(intent);
    }


}
