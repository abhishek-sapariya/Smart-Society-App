package com.example.darshi.homescreen;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateIssue extends DialogFragment implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

    Button issue_cancel_btn,issue_upload_btn,vote_deadline_btn,vote_time_deadline_btn;
    EditText issue_desc,issue_title;
    DatabaseReference IssueRef,NewIssueRef;
    IssueMaster issueMaster;
    static  int c=0;
    Globals g = Globals.getInstance();
    long counter = 0;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_create_issue, container, false);
        issue_cancel_btn = view.findViewById(R.id.issue_cancel_btn);
        issue_upload_btn = view.findViewById(R.id.issue_upload_btn);
        vote_deadline_btn = view.findViewById(R.id.vote_deadline_btn);
        vote_time_deadline_btn = view.findViewById(R.id.vote_time_deadline_btn);
        issue_desc = view.findViewById(R.id.issue_desc);
        issue_title = view.findViewById(R.id.issue_title);
        IssueRef = FirebaseDatabase.getInstance().getReference();

        NewIssueRef = FirebaseDatabase.getInstance().getReference();
        NewIssueRef.child(g.getSname()).child("NoticeMaster").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    counter = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        vote_deadline_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        vote_time_deadline_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        issue_upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(issue_title.getText().toString().equals(""))
                {
                    issue_title.setError("You must enter Issue Title");
                    issue_title.requestFocus();
                }
                else if(issue_desc.getText().toString().equals(""))
                {
                    issue_title.setError("You must enter Issue Description");
                    issue_desc.requestFocus();
                }
                else
                {
                    FirebaseDatabase.getInstance().getReference()
                            .addListenerForSingleValueEvent(new ValueEventListener() {

                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        for (DataSnapshot supportItem: dataSnapshot.getChildren()) {
                                            issueMaster = new IssueMaster();
                                            issueMaster.setIssue_description(issue_desc.getText().toString());
                                            issueMaster.setYes_votes("0");
                                            issueMaster.setNo_votes("0");
                                            issueMaster.setVoting_deadline(vote_deadline_btn.getText().toString());
                                            issueMaster.setDeadline_time(vote_time_deadline_btn.getText().toString());
                                            String issueTitle = issue_title.getText().toString();
                                            IssueRef.child(g.getSname()).child("IssueMaster").child((String.valueOf(counter+1))+" "+issueTitle).setValue(issueMaster);
                                        }
                                    }

                                }


                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                    getDialog().dismiss();
                }

            }
        });

        issue_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(getContext(),"Closing",Toast.LENGTH_LONG).show();
                getDialog().dismiss();
            }
        });
        return view;
    }

    private void showDatePickerDialog()
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private void showTimePickerDialog()
    {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),this,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                true
        );

        timePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        vote_deadline_btn.setText(dayOfMonth + "-" + (month+1)+ "-"+year);

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        vote_time_deadline_btn.setText(hourOfDay+" : "+minute);
        c++;
    }
}
