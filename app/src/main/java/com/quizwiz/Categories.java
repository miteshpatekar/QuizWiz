package com.quizwiz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;


public class Categories extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
    }

    public void Categories(View v)
    {
        //Intent i=new Intent(this,Categories.class);
        startActivity(new Intent(Categories.this, QuizQuestions.class));
    }



}
