package com.quizwiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.quizwiz.R;

public class Results extends ActionBarActivity {

    TextView pointsText;
    String points="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        pointsText=(TextView)findViewById(R.id.textView16);
        Intent i=getIntent();
        points = i.getStringExtra("points");
        pointsText.setText(points);
    }

    public void onFinish(View v)
    {
        Intent intent = new Intent(this,HomePage.class);
        startActivity(intent);
    }
}
