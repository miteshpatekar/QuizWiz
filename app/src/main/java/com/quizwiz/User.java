package com.quizwiz;

/**
 * Created by admin on 3/28/2015.
 */

public class User {
    //private int birthYear;
    private String fullName;
    private String email;
    private String uname;
    private String password;
    private String requests[];
    private String friend_list[];

    public User() {}
    public User(String usrname,String emailAdd, String pass)
    {
        this.uname= usrname;
        this.email = emailAdd;
        this.password = pass;
        this.fullName = null;
        this.requests = null;
        this.friend_list = null;
    }
    public String getUname() {
        return uname;
    }
    public String getFullName() {
        return fullName;
    }
    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}