package com.quizwiz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;


public class RegisterActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
    }

    public void onRegister(View view) {
        Intent intent = new Intent(this,HomePage.class);
        startActivity(intent);
    }


}
