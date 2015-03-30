package com.quizwiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mitesh on 3/22/2015.
 */
public class CustomListAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;
    private String uname;

    public CustomListAdapter(ArrayList<String> list, Context context,String uname) {
        this.list = list;
        this.context = context;
        this.uname=uname;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
       // return list.get(pos).getId();
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_list, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position));

        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);
        Button addBtn = (Button)view.findViewById(R.id.add_btn);

        // Get a reference to our posts
        final Firebase requestRef = new Firebase("https://popping-heat-8474.firebaseio.com"+"/User");

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                list.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                final String requestUname=list.get(position);

                // Accept friend and add it in owns friends list
                requestRef.child(uname).child("friendsList").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        // store the values in map structure
                        Map<String, Boolean> friendList = (Map<String, Boolean>) snapshot.getValue();
                        if (friendList == null) {
                            Map<String, Boolean> friendName = new HashMap<String,Boolean>();
                            friendName.put(requestUname, false);
                            requestRef.child(uname).child("friendsList").setValue(friendName);

                        }
                        else
                        {
                            friendList.put(requestUname, false);
                            requestRef.child(uname).child("friendsList").setValue(friendList);
                        }
                        // set status to true
                        requestRef.child(uname).child("requestList").child(requestUname).setValue(true);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });



                // Accept friend and add it in friends friends list

                requestRef.child(requestUname).child("friendsList").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        // store the values in map structure
                        Map<String, Boolean> friendList = (Map<String, Boolean>) snapshot.getValue();
                        if (friendList == null) {
                            Map<String, Boolean> friendName = new HashMap<String,Boolean>();
                            friendName.put(uname, false);
                            requestRef.child(requestUname).child("friendsList").setValue(friendName);

                        }
                        else
                        {
                            friendList.put(uname, false);
                            requestRef.child(requestUname).child("friendsList").setValue(friendList);
                        }

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });


                list.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });

        return view;
    }
}