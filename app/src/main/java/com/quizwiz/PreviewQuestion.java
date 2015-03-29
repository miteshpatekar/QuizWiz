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

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PreviewQuestion extends ActionBarActivity {
    TextView previewHeader;
    TextView questionT;
    Button buttonA;
    Button buttonB;
    Button buttonC;
    Button buttonD;
    TextView answerT;
    Firebase myFirebaseRef;

    String category="";
    String question="";
    String optionA="";
    String optionB="";
    String optionC="";
    String optionD="";
    String answer="Answer: Select the answer with the buttons above";
    int questionCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_question);
        Intent i=getIntent();
        category=i.getStringExtra("category");
        myFirebaseRef = new Firebase("https://popping-heat-8474.firebaseio.com/Questions/"  +category);
        previewHeader=(TextView)findViewById(R.id.previewHeader);
        questionT=(TextView)findViewById(R.id.previewQuestion);
        answerT=(TextView)findViewById(R.id.previewAnswer);
        buttonA=(Button)findViewById(R.id.button15);
        buttonB=(Button)findViewById(R.id.button16);
        buttonC=(Button)findViewById(R.id.button17);
        buttonD=(Button)findViewById(R.id.button18);
        question=i.getStringExtra("question");
        optionA=i.getStringExtra("optionA");
        optionB=i.getStringExtra("optionB");
        optionC=i.getStringExtra("optionC");
        optionD=i.getStringExtra("optionD");
        update();
        questionCount();
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
            Map<String, String> ques = new HashMap<String, String>();
            ques.put("QText",question);
            ques.put("Op1",optionA);
            ques.put("Op2",optionB);
            ques.put("Op3",optionC);
            ques.put("Op4",optionD);
            ques.put("Answer",answer);
            myFirebaseRef.child("Q"+questionCount).setValue(ques);

            Toast.makeText(this, "Question submitted successfully", Toast.LENGTH_SHORT).show();
            Log.d("Preview Question", "Question submitted successfully");
            startActivity(new Intent(PreviewQuestion.this,HomePage.class));
        }
    }

    public void questionCount()
    {
        Firebase.setAndroidContext(this);
        final Query queryRef = myFirebaseRef.orderByKey();
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
            // ....
        });

        myFirebaseRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // store the values in map structure
                Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();
                //iterate through the list
                for (Map.Entry<String, Object> entry : newPost.entrySet()) {
                    questionCount++;
                    Log.d("Preview Question", ""+questionCount);
                }
                questionCount++;
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
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
