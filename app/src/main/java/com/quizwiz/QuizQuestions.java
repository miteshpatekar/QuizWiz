package com.quizwiz;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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


public class QuizQuestions extends ActionBarActivity {

    String question="Question";
    String optionA="A";
    String optionB="B";
    String optionC="C";
    String optionD="D";
    String correct=optionA;
    Button op1Button;
    Button op2Button;
    Button op3Button;
    Button op4Button;
    Button correctButton;
    TextView questionText;
    Drawable buttonColor;
    boolean aState=false;
    boolean bState=false;
    boolean cState=false;
    boolean dState=false;
    boolean answered=false;
    int points=0;
    String total="";

    Question q1;
    Question q2;
    Question q3;
    ArrayList <Question> questions;
    int qCount=-1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_questions);
        qCount=-1;
        questionText=(TextView)findViewById(R.id.textView6);
        op1Button=(Button)findViewById(R.id.button8);
        op2Button=(Button)findViewById(R.id.button9);
        op3Button=(Button)findViewById(R.id.button10);
        op4Button=(Button)findViewById(R.id.button11);
        buttonColor = op1Button.getBackground();


        q1=new Question("The correct declaration of an integer named i is:","int i;","i = int;","integer = i;"
        ,"int = i;","int i;");
        q2=new Question("What is the brain of the computer?","Motherboard","RAM","CPU","NIC","CPU");
        q3=new Question("An IP address is a numeric quantity that identifies","a network adapter to other devices on the network","the manufacturer of a computer","the physical location of a computer","none of the above","a network adapter to other devices on the network");
        questions=new ArrayList<Question>();
        questions.add(q1);
        questions.add(q2);
        questions.add(q3);

        //retrieveData();
        nextQuestion();
    }

    public void retrieveData()
    {
        Firebase.setAndroidContext(this);

        Firebase myFirebaseRef = new Firebase("https://popping-heat-8474.firebaseio.com/Questions/CS");
        // Firebase rootRef = new Firebase("https://docs-examples.firebaseio.com/web/data");

        final Query queryRef = myFirebaseRef.orderByKey();
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                //System.out.println("!!!!!!! The " + snapshot.getKey() + " dinosaur's score is " + snapshot.getValue());
                Log.d("Msg","Question " + snapshot.getKey() + " components: " + snapshot.getValue());
                Object o=new Object();
                o=snapshot.getValue();
               // Log.d("Here it is"," Question " + o.toString());
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

        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());
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
        if(qCount<questions.size()) {
            question = questions.get(qCount).getQuestion().toString();
            correct = questions.get(qCount).getAnswer().toString();
            optionA = questions.get(qCount).getOption1().toString();
            optionB = questions.get(qCount).getOption2().toString();
            optionC = questions.get(qCount).getOption3().toString();
            optionD = questions.get(qCount).getOption4().toString();

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
            op1Button.setBackground(buttonColor);
            op2Button.setBackground(buttonColor);
            op3Button.setBackground(buttonColor);
            op4Button.setBackground(buttonColor);
        }
        else
        {
            Toast.makeText(this, "No More Questions", Toast.LENGTH_SHORT).show();
            total=String.valueOf(points)+"/"+String.valueOf(questions.size());
            Intent i=new Intent(QuizQuestions.this,Results.class);
            i.putExtra("points",total);
            startActivity(i);
        }

        aState=false;
        bState=false;
        cState=false;
        dState=false;
        answered=false;
    }
    public void onClickA(View v)
    {
        if(aState==false) {
            if (correctButton == op1Button) {
                op1Button.setBackgroundColor(Color.GREEN);
                points++;
            } else {
                op1Button.setBackgroundColor(Color.RED);
                correctButton.setBackgroundColor(Color.GREEN);
            }
            answered=true;
        }
    }
    public void onClickB(View v)
    {
        if(bState==false) {
            if (correctButton == op2Button) {
                op2Button.setBackgroundColor(Color.GREEN);
                points++;
            } else {
                op2Button.setBackgroundColor(Color.RED);
                correctButton.setBackgroundColor(Color.GREEN);
            }
            answered=true;
        }
    }
    public void onClickC(View v)
    {
        if(cState==false) {
            if (correctButton == op3Button) {
                op3Button.setBackgroundColor(Color.GREEN);
                points++;
            } else {
                op3Button.setBackgroundColor(Color.RED);
                correctButton.setBackgroundColor(Color.GREEN);
            }
            answered=true;
        }
    }
    public void onClickD(View v)
    {
        if(dState==false) {
            if (correctButton == op4Button) {
                op4Button.setBackgroundColor(Color.GREEN);
                points++;
            } else {
                op4Button.setBackgroundColor(Color.RED);
                correctButton.setBackgroundColor(Color.GREEN);
            }
            answered=true;
        }
    }
    public void onNextQ(View v)
    {
        if(answered==true) {
            nextQuestion();
        }
        else{
            Toast.makeText(this, "Please answer question first.", Toast.LENGTH_SHORT).show();
        }
    }
}
