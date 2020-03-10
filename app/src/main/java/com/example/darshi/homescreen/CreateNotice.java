package com.example.darshi.homescreen;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateNotice extends DialogFragment {

    Button notice_cancel_btn,notice_upload_btn;
    EditText notice_desc,notice_title;
    DatabaseReference NoticeRef;
    static  int c=0;
    long counter = 0;
    Globals g = Globals.getInstance();
    static String str1,str2;
    static String noticeTitle;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_create_notice, container, false);
        notice_cancel_btn = view.findViewById(R.id.notice_cancel_btn);
        notice_upload_btn = view.findViewById(R.id.notice_upload_btn);
        notice_desc = view.findViewById(R.id.notice_desc);
        notice_title = view.findViewById(R.id.notice_title);
        NoticeRef = FirebaseDatabase.getInstance().getReference();
        NoticeRef.child(g.getSname()).child("NoticeMaster").addValueEventListener(new ValueEventListener() {
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

        notice_upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c++;

                FirebaseDatabase.getInstance().getReference().child("NoticeMaster")
                        .addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    for (DataSnapshot supportItem: dataSnapshot.getChildren()) {
                                        NoticeMaster noticeMaster = new NoticeMaster();
                                        noticeMaster.setNotice_description(notice_desc.getText().toString());

                                        Calendar calendar = Calendar.getInstance();
                                        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy ");
                                        String strDate = mdformat.format(calendar.getTime());

                                        noticeMaster.setNotice_uploaded_on(strDate);

                                        noticeTitle = notice_title.getText().toString();
                                        NoticeRef.child(g.getSname()).child("NoticeMaster").child((String.valueOf(counter+1))+" " +noticeTitle+" "+strDate).setValue(noticeMaster);
                                        str1 = String.valueOf(counter+1)+" "+noticeTitle+" "+strDate;
                                        str2 = notice_desc.getText().toString();
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                getDialog().dismiss();
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setTitle("Upload this notice ?")
//                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        })
//                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                c++;
//
//                                FirebaseDatabase.getInstance().getReference().child("NoticeMaster")
//                                        .addListenerForSingleValueEvent(new ValueEventListener() {
//
//                                            @Override
//                                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                                if(dataSnapshot.exists()){
//                                                    for (DataSnapshot supportItem: dataSnapshot.getChildren()) {
//                                                        NoticeMaster noticeMaster = new NoticeMaster();
//                                                        noticeMaster.setNotice_description(notice_desc.getText().toString());
//
//                                                        Calendar calendar = Calendar.getInstance();
//                                                        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy ");
//                                                        String strDate = mdformat.format(calendar.getTime());
//
//                                                        noticeMaster.setNotice_uploaded_on(strDate);
//
//                                                        String noticeTitle = notice_title.getText().toString();
//                                                        NoticeRef.child(g.getSname()).child("NoticeMaster").child((c)+" " +noticeTitle).setValue(noticeMaster);
//                                                    }
//                                                }
//
//                                            }
//
//                                            @Override
//                                            public void onCancelled(DatabaseError databaseError) {
//
//                                            }
//                                        });
//
//                            }
//                        });
//


            }
        });

        notice_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"Closing",Toast.LENGTH_LONG).show();
                getDialog().dismiss();
            }
        });

        return view;

    }
}
