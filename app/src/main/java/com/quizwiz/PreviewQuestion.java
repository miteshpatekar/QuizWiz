package com.quizwiz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PreviewQuestion extends ActionBarActivity {
    TextView previewHeader;
    TextView questionT;
    Button buttonA;
    Button buttonB;
    Button buttonC;
    Button buttonD;
    TextView answerT;

    String category="";
    String question="";
    String optionA="";
    String optionB="";
    String optionC="";
    String optionD="";
    String answer="Answer: Select the answer with the buttons above";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_question);
        previewHeader=(TextView)findViewById(R.id.previewHeader);
        questionT=(TextView)findViewById(R.id.previewQuestion);
        answerT=(TextView)findViewById(R.id.previewAnswer);
        buttonA=(Button)findViewById(R.id.button15);
        buttonB=(Button)findViewById(R.id.button16);
        buttonC=(Button)findViewById(R.id.button17);
        buttonD=(Button)findViewById(R.id.button18);
        Intent i=getIntent();
        category=i.getStringExtra("category");
        question=i.getStringExtra("question");
        optionA=i.getStringExtra("optionA");
        optionB=i.getStringExtra("optionB");
        optionC=i.getStringExtra("optionC");
        optionD=i.getStringExtra("optionD");
        update();
    }

    public void update(){
        previewHeader.setText("Category - "+category);
        questionT.setText(question);
        buttonA.setText(optionA);
        buttonB.setText(optionB);
        buttonC.setText(optionC);
        buttonD.setText(optionD);
    }


    public void onClickA(View view){
        answerT.clearComposingText();
        answer=optionA;
        Log.d("Preview Question", "Answer set to option A: "+answer);
        answerT.setText("Answer: "+answer);
    }

    public void onClickB(View view){
        answerT.clearComposingText();
        answer=optionB;
        Log.d("Preview Question", "Answer set to option B: "+answer);
        answerT.setText("Answer: "+answer);
    }

    public void onClickC(View view){
        answerT.clearComposingText();
        answer=optionC;
        Log.d("Preview Question", "Answer set to option C: "+answer);
        answerT.setText("Answer: "+answer);
    }

    public void onClickD(View view){
        answerT.clearComposingText();
        answer=optionD;
        Log.d("Preview Question", "Answer set to option D: "+answer);
        answerT.setText("Answer: "+answer);
    }

    public void onSubmit(View view){
        post();
    }

    public void post(){
        if(answer.equals("Answer: Select the answer with the buttons above"))
        {
            Toast.makeText(this, "Select the correct answer before submitting", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Question submitted successfully", Toast.LENGTH_SHORT).show();
            Log.d("Preview Question", "Question submitted successfully");
            startActivity(new Intent(PreviewQuestion.this,HomePage.class));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preview_question, menu);
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
}
