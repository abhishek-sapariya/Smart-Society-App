//package com.example.darshi.homescreen;
//
//import android.app.Activity;
//import android.content.SharedPreferences;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.DialogFragment;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//
//public class CreateComplaint extends DialogFragment {
//
//    Button complaint_cancel_btn,complaint_upload_btn;
//    EditText complaint_desc,complaint_title;
//    DatabaseReference ComplaintRef;
//    static  int d=0;
//    Globals g = Globals.getInstance();
//    long counter1 = 0,counter2 = 0;
//    static String str11;
//    static String complaintTitle;
//    int prefMode = Activity.MODE_PRIVATE;
//    public static String MY_PREFS = "MY_PREFS";
//    private SharedPreferences mySharedPreferences;
//
//
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.activity_create_complaint, container, false);
//        complaint_cancel_btn = view.findViewById(R.id.complaint_cancel_btn);
//        complaint_upload_btn = view.findViewById(R.id.complaint_upload_btn);
//        complaint_desc = view.findViewById(R.id.complaint_desc);
//        complaint_title = view.findViewById(R.id.complaint_title);
//
//
//
//        ComplaintRef = FirebaseDatabase.getInstance().getReference();
//        ComplaintRef.child(g.getSname()).child("ComplaintMaster").child(g.getHm_blockno()+g.getHm_flatno()).child("Complaints").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    counter1 = (dataSnapshot.getChildrenCount());
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        /*NoticeRef.child(g.getSname()).child("HouseMaster").child(g.getHm_blockno()+g.getHm_flatno()).child("Complaints")
//                .addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    counter2 = (dataSnapshot.getChildrenCount());
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });*/
//
//        complaint_upload_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                d++;
//
//                /*FirebaseDatabase.getInstance().getReference().child("Complaints")
//                        .addListenerForSingleValueEvent(new ValueEventListener() {
//
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                if(dataSnapshot.exists()){
//                                    for (DataSnapshot supportItem: dataSnapshot.getChildren()) {
//                                        ComplaintMaster complaintMaster = new ComplaintMaster();
//                                        complaintMaster.setComplaint_description(complaint_desc.getText().toString());
//
//                                        Calendar calendar = Calendar.getInstance();
//                                        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy ");
//                                        String strDate = mdformat.format(calendar.getTime());
//
//                                        complaintMaster.setComplaint_uploaded_on(strDate);
//
//                                        complaintTitle = complaint_title.getText().toString();
//                                        NoticeRef.child(g.getSname()).child("ComplaintMaster").child(g.getHm_blockno()+g.getHm_flatno()).child("Complaints").child((String.valueOf(counter1+1))+" " +complaintTitle).setValue(complaintMaster);
//                                        str11 = String.valueOf(counter1+1)+" "+complaintTitle;
//                                    }
//                                }
//
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });*/
//
//
//                FirebaseDatabase.getInstance().getReference().child("ComplaintMaster")
//                        .addListenerForSingleValueEvent(new ValueEventListener() {
//
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                if(dataSnapshot.exists()){
//
////                                    mySharedPreferences = getActivity().getSharedPreferences(MY_PREFS, prefMode);
////                                    String string1 = mySharedPreferences.getString("newkey1x", null);
////                                    String string2 = mySharedPreferences.getString("newkey2x", null);
////
////                                    g.setHm_blockno(string1);
////                                    g.setHm_flatno(string2);
//
//                                    for (DataSnapshot supportItem: dataSnapshot.getChildren()) {
//                                        ComplaintMaster complaintMaster = new ComplaintMaster();
//                                        complaintMaster.setComplaint_description(complaint_desc.getText().toString());
//
//                                        Calendar calendar = Calendar.getInstance();
//                                        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy ");
//                                        String strDate = mdformat.format(calendar.getTime());
//
//                                        complaintMaster.setComplaint_uploaded_on(strDate);
//
//                                        complaintTitle = complaint_title.getText().toString();
//                                        ComplaintRef.child(g.getSname()).child("ComplaintMaster").child(g.getHm_blockno()+g.getHm_flatno()).child("Complaints").child((String.valueOf(counter1+1))+" " +complaintTitle).setValue(complaintMaster);
//                                        str11 = String.valueOf(counter1+1)+" "+complaintTitle;
//                                    }
//                                }
//
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
//
//                getDialog().dismiss();
//
//            }
//        });
//
//        complaint_cancel_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(),"Closing",Toast.LENGTH_LONG).show();
//                getDialog().dismiss();
//            }
//        });
//
//        return view;
//
//    }
//}
package com.example.darshi.homescreen;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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

public class CreateComplaint extends DialogFragment {

    Button complaint_cancel_btn,complaint_upload_btn;
    EditText complaint_desc,complaint_title;
    DatabaseReference NoticeRef;
    static  int d=0;
    Globals g = Globals.getInstance();
    long counter1 = 0,counter2 = 0;
    static String str11,str22,str33;
    static String complaintTitle;
    SharedPreferences mySharedPreferences;
    int prefMode = Activity.MODE_PRIVATE;
    public static String MY_PREFS = "MY_PREFS";


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_create_complaint, container, false);
        complaint_cancel_btn = view.findViewById(R.id.complaint_cancel_btn);
        complaint_upload_btn = view.findViewById(R.id.complaint_upload_btn);
        complaint_desc = view.findViewById(R.id.complaint_desc);
        complaint_title = view.findViewById(R.id.complaint_title);
        NoticeRef = FirebaseDatabase.getInstance().getReference();
        NoticeRef.child(g.getSname()).child("ComplaintMaster").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    counter1 = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mySharedPreferences = getActivity().getSharedPreferences(MY_PREFS, prefMode);
        String string1 = mySharedPreferences.getString("newkey1x", null);
        String string2 = mySharedPreferences.getString("newkey2x", null);

        g.setHm_blockno(string1);
        g.setHm_flatno(string2);

        NoticeRef.child(g.getSname()).child("HouseMaster").child(g.getHm_blockno()+(g.getHm_flatno())).child("Complaints")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            counter2 = (dataSnapshot.getChildrenCount());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        Toast.makeText(getContext(),g.getHm_blockno(),Toast.LENGTH_LONG).show();

        complaint_upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //d++;

                FirebaseDatabase.getInstance().getReference().child("Complaints")
                        .addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    //for (DataSnapshot supportItem: dataSnapshot.getChildren()) {
                                    ComplaintMaster complaintMaster = new ComplaintMaster();
                                    complaintMaster.setComplaint_description(complaint_desc.getText().toString());

                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy ");
                                    String strDate = mdformat.format(calendar.getTime());

                                    complaintMaster.setComplaint_uploaded_on(strDate);

                                    complaintTitle = complaint_title.getText().toString();
                                    NoticeRef.child(g.getSname()).child("HouseMaster").child("T"+(g.getHm_flatno())).child("Complaints").child((String.valueOf(counter2+1))+" " +complaintTitle).setValue(complaintMaster);
                                    str11 = String.valueOf(counter2+1)+" "+complaintTitle;
                                    //}
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                FirebaseDatabase.getInstance().getReference().child("ComplaintMaster")
                        .addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    //for (DataSnapshot supportItem: dataSnapshot.getChildren()) {
                                    ComplaintMaster complaintMaster = new ComplaintMaster();
                                    complaintMaster.setComplaint_description(complaint_desc.getText().toString());

                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy ");
                                    String strDate = mdformat.format(calendar.getTime());

                                    complaintMaster.setComplaint_uploaded_on(strDate);

                                    complaintTitle = complaint_title.getText().toString();
                                    //str33 = g.getHm_blockno()+g.getHm_flatno();
                                    NoticeRef.child(g.getSname()).child("ComplaintMaster").child((String.valueOf(counter1+1))+" " +complaintTitle).setValue(complaintMaster);
                                    str22 = String.valueOf(counter1+1)+" "+complaintTitle;
                                    //}
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

        complaint_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"Closing",Toast.LENGTH_LONG).show();
                getDialog().dismiss();
            }
        });

        return view;

    }
}
