package com.quizwiz;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.quizwiz.Question;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;


public class QuizQuestions extends ActionBarActivity {

    String question="Question";
    String optionA="A";
    String optionB="B";
    String optionC="C";
    String optionD="D";
    String correct="Answer";
    Button op1Button;
    Button op2Button;
    Button op3Button;
    Button op4Button;
    Button correctButton;
    TextView questionText;
    TextView categoryText;
    TextView pointsText;
    TextView qLeftText;
    TextView timerText;
    Drawable buttonColor;
    boolean aState=false;
    boolean bState=false;
    boolean cState=false;
    boolean dState=false;
    boolean answered=false;
    int points=0;
    String total="";
    private Timer timer = new Timer();
    int qTime=31;
    int timerVal=qTime;
    final Handler myHandler = new Handler();

    Question q1;
    Question q2;
    Question q3;
    ArrayList <Question> questionList;
    int qCount=-1;
    int questionCount=1;
    String category="";
    int maxQuestions=10;
    String colorVal="#FFFFFF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_questions);
        qCount=-1;
        questionText=(TextView)findViewById(R.id.textView6);
        categoryText=(TextView)findViewById(R.id.textView13);
        pointsText=(TextView)findViewById(R.id.textView17);
        qLeftText=(TextView)findViewById(R.id.textView18);
        timerText=(TextView)findViewById(R.id.textView19);
        op1Button=(Button)findViewById(R.id.button8);
        op2Button=(Button)findViewById(R.id.button9);
        op3Button=(Button)findViewById(R.id.button10);
        op4Button=(Button)findViewById(R.id.button11);
        buttonColor = op1Button.getBackground();
        questionList=new ArrayList<Question>();

        retrieveData();
        // nextQuestion();
    }

    public void retrieveData()
    {
        Log.d("RetrieveData", "Gathering Values");

        Firebase.setAndroidContext(this);
        Intent i=getIntent();
        category = i.getStringExtra("category");
        categoryText.setText("Category - "+category);
        pointsText.setText("Points: " +points);

        Firebase myFirebaseRef = new Firebase("https://popping-heat-8474.firebaseio.com/Questions/"  +category);
        // Firebase rootRef = new Firebase("https://docs-examples.firebaseio.com/web/data");

        final Query queryRef = myFirebaseRef.orderByKey();
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                Log.d("Msg","Question " + snapshot.getKey() + " Components: " + snapshot.getValue());
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
        //myFirebaseRef.orderByKey();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz_questions, menu);
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

    public void nextQuestion()
    {
        qCount++;
        //pointsText.setText("Points: " +points);
        if ((questionList.size()-qCount)<=0){
            qLeftText.setText("Questions Left: 0");
        }
        else {
            qLeftText.setText("Questions Left: " + (questionList.size() - qCount - 1));
        }


        if(qCount<questionList.size()) {
            question = questionList.get(qCount).getQuestion().toString();
            correct = questionList.get(qCount).getAnswer().toString();
            optionA = questionList.get(qCount).getOption1().toString();
            optionB = questionList.get(qCount).getOption2().toString();
            optionC = questionList.get(qCount).getOption3().toString();
            optionD = questionList.get(qCount).getOption4().toString();

            questionText.setText(question);
            op1Button.setText(optionA);
            op2Button.setText(optionB);
            op3Button.setText(optionC);
            op4Button.setText(optionD);
            if (op1Button.getText().equals(correct)) {
                correctButton = op1Button;
            } else if (op2Button.getText().equals(correct)) {
                correctButton = op2Button;
            } else if (op3Button.getText().equals(correct)) {
                correctButton = op3Button;
            } else if (op4Button.getText().equals(correct)) {
                correctButton = op4Button;
            }
            op1Button.setBackgroundColor(Color.parseColor(colorVal));
            op2Button.setBackgroundColor(Color.parseColor(colorVal));
            op3Button.setBackgroundColor(Color.parseColor(colorVal));
            op4Button.setBackgroundColor(Color.parseColor(colorVal));
            time();
        }
        else
        {
            Toast.makeText(this, "No More Questions", Toast.LENGTH_SHORT).show();
            total=String.valueOf(points)+"/"+String.valueOf(questionList.size());
            Intent i=new Intent(QuizQuestions.this,Results.class);
            i.putExtra("points",total);
            startActivity(i);
        }
        aState=false;
        bState=false;
        cState=false;
        dState=false;
        answered=false;
        timerVal=qTime;
    }

    public void time() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                UpdateGUI();
            }
        }, 0, 1000);
    }

    private void UpdateGUI() {
        timerVal--;
        myHandler.post(myRunnable);
    }

    final Runnable myRunnable = new Runnable() {
        public void run() {
            if (timerVal==0)
            {
                if (correctButton == op1Button) {
                    op1Button.setBackgroundColor(Color.GREEN);
                } else {
                    op1Button.setBackgroundColor(Color.RED);
                }
                if (correctButton == op2Button) {
                    op2Button.setBackgroundColor(Color.GREEN);
                } else {
                    op2Button.setBackgroundColor(Color.RED);
                }
                if (correctButton == op3Button) {
                    op3Button.setBackgroundColor(Color.GREEN);
                } else {
                    op3Button.setBackgroundColor(Color.RED);
                }
                if (correctButton == op4Button) {
                    op4Button.setBackgroundColor(Color.GREEN);
                } else {
                    op4Button.setBackgroundColor(Color.RED);
                }
                answered=true;
                timer.cancel();
                Toast.makeText(QuizQuestions.this, "Time Up - Incorrect", Toast.LENGTH_SHORT).show();
            }
            if(timerVal<10) {
                String t="0"+String.valueOf(timerVal);
                timerText.setText(String.valueOf(t));
            }
            else {
                timerText.setText(String.valueOf(timerVal));
            }

        }
    };


    public void onClickA(View v)
    {
        if(aState==false&&answered==false) {
            if (correctButton == op1Button) {
                op1Button.setBackgroundColor(Color.GREEN);
                op2Button.setBackgroundColor(Color.parseColor(colorVal));
                op3Button.setBackgroundColor(Color.parseColor(colorVal));
                op4Button.setBackgroundColor(Color.parseColor(colorVal));

                Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
                points++;
            } else {
                op1Button.setBackgroundColor(Color.RED);
                correctButton.setBackgroundColor(Color.GREEN);
                Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
            }
            pointsText.setText("Points: " +points);
            answered=true;
            timer.cancel();
        }
        else {
            Toast.makeText(this, "Question answered. Go to next question", Toast.LENGTH_SHORT).show();
        }
    }
    public void onClickB(View v)
    {
        if(bState==false&&answered==false) {
            if (correctButton == op2Button) {
                op2Button.setBackgroundColor(Color.GREEN);

                op1Button.setBackgroundColor(Color.parseColor(colorVal));
                op3Button.setBackgroundColor(Color.parseColor(colorVal));
                op4Button.setBackgroundColor(Color.parseColor(colorVal));

                Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
                points++;
            } else {
                op2Button.setBackgroundColor(Color.RED);
                correctButton.setBackgroundColor(Color.GREEN);
                Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
            }
            pointsText.setText("Points: " +points);
            answered=true;
            timer.cancel();
        }
        else {
            Toast.makeText(this, "Question answered. Go to next question", Toast.LENGTH_SHORT).show();
        }
    }
    public void onClickC(View v)
    {
        if(cState==false&&answered==false) {
            if (correctButton == op3Button) {
                op3Button.setBackgroundColor(Color.GREEN);
                op1Button.setBackgroundColor(Color.parseColor(colorVal));
                op2Button.setBackgroundColor(Color.parseColor(colorVal));
                op4Button.setBackgroundColor(Color.parseColor(colorVal));

                Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
                points++;
                pointsText.setText("Points: " +points);
            } else {
                op3Button.setBackgroundColor(Color.RED);
                correctButton.setBackgroundColor(Color.GREEN);
                Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
            }
            pointsText.setText("Points: " +points);
            answered=true;
            timer.cancel();
        }
        else {
            Toast.makeText(this, "Question answered. Go to next question", Toast.LENGTH_SHORT).show();
        }
    }
    public void onClickD(View v)
    {
        if(dState==false&&answered==false) {
            if (correctButton == op4Button) {
                op4Button.setBackgroundColor(Color.GREEN);

                op1Button.setBackgroundColor(Color.parseColor(colorVal));
                op2Button.setBackgroundColor(Color.parseColor(colorVal));
                op3Button.setBackgroundColor(Color.parseColor(colorVal));

                Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
                points++;
            } else {
                op4Button.setBackgroundColor(Color.RED);
                correctButton.setBackgroundColor(Color.GREEN);
                Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
            }
            pointsText.setText("Points: " +points);
            answered=true;
            timer.cancel();
        }
        else{
            Toast.makeText(this, "Question answered. Go to next question", Toast.LENGTH_SHORT).show();
        }
    }
    public void onNextQ(View v)
    {
        if(answered==true) {
            if (timer!=null) {
                timer.cancel();
            }
            nextQuestion();
        }
        else{
            Toast.makeText(this, "Please answer question first.", Toast.LENGTH_SHORT).show();
        }
    }
}
