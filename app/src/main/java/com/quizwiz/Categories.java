package com.quizwiz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class Categories extends ActionBarActivity {

    String category="";
    String activity="quiz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Intent intent=getIntent();
        if(intent.getStringExtra("activity")!=null) {
            activity = intent.getStringExtra("activity");
        }
        Log.d("Categories",activity);
    }

    public void onCS(View v)
    {
        Intent i;
        category="CS";
        if (activity.equals("quiz")) {
            i = new Intent(Categories.this, QuizQuestions.class);
        }
        else{
            i = new Intent(Categories.this, PostQuiz.class);
        }
        i.putExtra("category",category);
        startActivity(i);
    }
    public void onGK(View v)
    {
        Intent i;
        category="General Knowledge";
        if (activity.equals("quiz")) {
            i=new Intent(Categories.this, QuizQuestions.class);
        }
        else{
            i = new Intent(Categories.this, PostQuiz.class);
        }
        i.putExtra("category",category);
        startActivity(i);
    }
    public void onMovies(View v)
    {
        Intent i;
        category="Movies";
        if (activity.equals("quiz")) {
            i=new Intent(Categories.this, QuizQuestions.class);
        }
        else{
            i = new Intent(Categories.this, PostQuiz.class);
        }
        i.putExtra("category",category);
        startActivity(i);
    }
}
