package com.quizwiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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


public class PostQuiz extends ActionBarActivity {

    Firebase ref=null;
    TextView header;
    String category;
    EditText postQuestion;
    EditText postOptionA;
    EditText postOptionB;
    EditText postOptionC;
    EditText postOptionD;
    String question="";
    String optionA="";
    String optionB="";
    String optionC="";
    String optionD="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_quiz);
        header=(TextView)findViewById(R.id.postHeader);
        postQuestion=(EditText)findViewById(R.id.editText6);
        postOptionA=(EditText)findViewById(R.id.editText7);
        postOptionB=(EditText)findViewById(R.id.editText8);
        postOptionC=(EditText)findViewById(R.id.editText9);
        postOptionD=(EditText)findViewById(R.id.editText10);
        Intent i=getIntent();
        category=i.getStringExtra("category");
        header.setText("Posting question in: "+category);

    }

    public void onPost(View view){
        Intent i2=new Intent(PostQuiz.this,PreviewQuestion.class);
        question=postQuestion.getText().toString();
        optionA=postOptionA.getText().toString();
        optionB=postOptionB.getText().toString();
        optionC=postOptionC.getText().toString();
        optionD=postOptionD.getText().toString();

        if(optionA.equals("")||optionB.equals("")||optionC.equals("")||optionD.equals("")){
            Toast.makeText(this, "Fill out all fields before previewing", Toast.LENGTH_SHORT).show();
        }
        else {
            i2.putExtra("category", category);
            i2.putExtra("question", question);
            i2.putExtra("optionA", optionA);
            i2.putExtra("optionB", optionB);
            i2.putExtra("optionC", optionC);
            i2.putExtra("optionD", optionD);
            startActivity(i2);
        }
    }
/*
    public void retrieveData()
    {Log.d("Msg","Question " + snapshot.getKey() + " Components: " + snapshot.getValue());
        Log.d("RetrieveData", "Gathering Values");

        Firebase.setAndroidContext(this);
        Intent i=getIntent();

        Firebase myFirebaseRef = new Firebase("https://popping-heat-8474.firebaseio.com/Questions/"  +category);
        // Firebase rootRef = new Firebase("https://docs-examples.firebaseio.com/web/data");

        final Query queryRef = myFirebaseRef.orderByKey();
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                Object o=new Object();
                o=snapshot.getValue();
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

                    String question = snapshot.child(entry.getKey().toString() + "/QText").getValue().toString();
                    Log.d("Question", question);
                    String optionA = snapshot.child(entry.getKey().toString() + "/Op1").getValue().toString();
                    Log.d("OptionA", optionA);
                    String optionB = snapshot.child(entry.getKey().toString() + "/Op2").getValue().toString();
                    Log.d("OptionB", optionB);
                    String optionC = snapshot.child(entry.getKey().toString() + "/Op3").getValue().toString();
                    Log.d("OptionC", optionC);
                    String optionD = snapshot.child(entry.getKey().toString() + "/Op4").getValue().toString();
                    Log.d("OptionD", optionD);
                    String correct = snapshot.child(entry.getKey().toString() + "/Answer").getValue().toString();
                    Log.d("Answer", correct);

                    Question q = new Question(question, optionA, optionB, optionC, optionD, correct);
                    Log.d("Question", q.toString());
                    questionList.add(q);
                    questionCount++;
                    Log.d("QuestionCount", questionCount + "");
                }
                Collections.shuffle(questionList);
                if (questionList.size()>maxQuestions) {
                    questionList.subList(maxQuestions, questionList.size()).clear();
                }
                nextQuestion();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    public void onPost()
    {
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        String Question = prefs.getString("uname", "No name defined");//"No name defined" is the default value.
        EditText edtext=(EditText)findViewById(R.id.AddFriendText);
        String unameFriend=edtext.getText().toString();
        Log.d("username ", uname + " " + unameFriend);
        Map<String, Boolean> reqName = new HashMap<String, Boolean>();
        reqName.put(uname, false);
        ref.child(unameFriend).child("requestList").setValue(reqName);
        Toast.makeText(this, "Friend Request sent to " + unameFriend, Toast.LENGTH_SHORT).show();
    }*/
}
