package com.quizwiz;

/**
 * Created by Carlos on 3/19/2015.
 */
public class Question {
    String question;
    String option1;
    String option2;
    String option3;
    String option4;
    String answer;

    public Question()
    {
        question="Question goes here";
        option1="a";
        option2="b";
        option3="c";
        option4="d";
        answer="answer";
    }
    public Question(String ques, String op1, String op2, String op3, String op4, String ans)
    {
        question=ques;
        option1=op1;
        option2=op2;
        option3=op3;
        option4=op4;
        answer=ans;
    }

    public String getQuestion()
    {
        return question;
    }

    public String getAnswer()
    {
        return answer;
    }

    public String getOption1()
    {
        return option1;
    }

    public String getOption2()
    {
        return option2;
    }

    public String getOption3()
    {
        return option3;
    }

    public String getOption4()
    {
        return option4;
    }
}
