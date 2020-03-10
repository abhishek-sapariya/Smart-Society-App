package com.example.darshi.homescreen;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


//Registering
//Opens only one time
public class UserType extends AppCompatActivity {

    EditText house_blockno, house_flatno, house_members, house_residentname;
    Button residentbtn, secretarybtn;
    DatabaseReference HouseRef;
    int prefMode = Activity.MODE_PRIVATE;
    public static String MY_PREFS = "MY_PREFS";
    private SharedPreferences mySharedPreferences;
    HouseMaster houseMaster;

    Globals g = Globals.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Shared Preferences Here
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);
        mySharedPreferences = getSharedPreferences(MY_PREFS,prefMode);

        final SharedPreferences first_pref_user_type = getSharedPreferences("first_pref_user_type2", MODE_PRIVATE);
        boolean firstStartUserType = first_pref_user_type.getBoolean("first_start_user_type2", true);
        if (firstStartUserType)
        {

        residentbtn = findViewById(R.id.residentbtn);
        secretarybtn = findViewById(R.id.secretarybtn);

        //Toast.makeText(getApplicationContext(), g.getSname(), Toast.LENGTH_LONG).show();


        residentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                house_blockno = findViewById(R.id.Global_BlockNo);
                house_flatno = findViewById(R.id.Global_FlatNo);
                house_members = findViewById(R.id.Global_No_Of_Members);
                house_residentname = findViewById(R.id.Global_ResidentName);

                if(house_residentname.getText().toString().equals("")) {
                    house_residentname.setError("You must fill this field");
                    house_residentname.requestFocus();
                }
                else if(house_blockno.getText().toString().equals(""))
                {
                    house_blockno.setError("You must fill this field");
                    house_blockno.requestFocus();
                }
                else if(house_flatno.getText().toString().equals(""))
                {
                    house_flatno.setError("You must fill this field");
                    house_flatno.requestFocus();
                }
                else if(house_members.getText().toString().equals(""))
                {
                    house_members.setError("You must fill this field");
                    house_members.requestFocus();
                }
                else
                {

                    FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(g.getSname()).exists())
                            {
                                g.setHm_blockno(house_blockno.getText().toString());
                                g.setHm_flatno(house_flatno.getText().toString());
                                g.setHm_members(house_members.getText().toString());
                                g.setHm_resname(house_residentname.getText().toString());
                                g.setHm_usertype("Resident");

                                SharedPreferences.Editor editor = mySharedPreferences.edit();
                                editor.putString("newkey1x", house_blockno.getText().toString());
                                editor.putString("newkey2x", house_flatno.getText().toString());
                                editor.putString("newkey3x", house_members.getText().toString());
                                editor.putString("newkey4x", house_residentname.getText().toString());
                                editor.putString("newkey5x", "Resident");
                                editor.commit();


                                mySharedPreferences = getSharedPreferences(MY_PREFS, prefMode);
                                String string1 = mySharedPreferences.getString("newkey1x", null);
                                String string2 = mySharedPreferences.getString("newkey2x", null);
                                String string3 = mySharedPreferences.getString("newkey3x", null);
                                String string4 = mySharedPreferences.getString("newkey4x", null);
                                String string5 = mySharedPreferences.getString("newkey5x", null);

                                g.setHm_blockno(string1);
                                g.setHm_flatno(string2);
                                g.setHm_members(string3);
                                g.setHm_resname(string4);
                                g.setHm_usertype(string5);


                                PutSocietyCode dialog = new PutSocietyCode();
                                dialog.show(getSupportFragmentManager(),"PutSocietyCodeDialog");



                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Society Not Found",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

            }
        });


        secretarybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                house_blockno = findViewById(R.id.Global_BlockNo);
                house_flatno = findViewById(R.id.Global_FlatNo);
                house_members = findViewById(R.id.Global_No_Of_Members);
                house_residentname = findViewById(R.id.Global_ResidentName);

                if(house_residentname.getText().toString().equals(""))
                {
                    house_residentname.setError("You must fill this field");
                    house_residentname.requestFocus();
                }
                else if(house_blockno.getText().toString().equals(""))
                {
                    house_blockno.setError("You must fill this field");
                    house_blockno.requestFocus();
                }
                else if(house_flatno.getText().toString().equals(""))
                {
                    house_flatno.setError("You must fill this field");
                    house_flatno.requestFocus();
                }
                else if(house_members.getText().toString().equals(""))
                {
                    house_members.setError("You must fill this field");
                    house_members.requestFocus();
                }
                else
                {
                    HouseRef = FirebaseDatabase.getInstance().getReference();

                    HouseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            houseMaster = new HouseMaster();

                            houseMaster.setBlock(g.getHm_blockno());
                            houseMaster.setFlatNo(g.getHm_flatno());
                            houseMaster.setResidentName(g.getHm_resname());
                            houseMaster.setNoOfMembers(g.getHm_members());
                            houseMaster.setUserType("Secretary");
                            HouseRef.child(g.getSname()).child("HouseMaster").child(g.getHm_blockno().concat(g.getHm_flatno())).setValue(houseMaster);
//
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    g.setHm_blockno(house_blockno.getText().toString());
                    g.setHm_flatno(house_flatno.getText().toString());
                    g.setHm_members(house_members.getText().toString());
                    g.setHm_resname(house_residentname.getText().toString());
                    g.setHm_usertype("Secretary");

                    SharedPreferences.Editor editor = mySharedPreferences.edit();
                    editor.putString("newkey1x", house_blockno.getText().toString());
                    editor.putString("newkey2x", house_flatno.getText().toString());
                    editor.putString("newkey3x", house_members.getText().toString());
                    editor.putString("newkey4x", house_residentname.getText().toString());
                    editor.putString("newkey5x", "Secretary");
                    editor.commit();


                    mySharedPreferences = getSharedPreferences(MY_PREFS, prefMode);
                    String string1 = mySharedPreferences.getString("newkey1x", null);
                    String string2 = mySharedPreferences.getString("newkey2x", null);
                    String string3 = mySharedPreferences.getString("newkey3x", null);
                    String string4 = mySharedPreferences.getString("newkey4x", null);
                    String string5 = mySharedPreferences.getString("newkey5x", null);

                    g.setHm_blockno(string1);
                    g.setHm_flatno(string2);
                    g.setHm_members(string3);
                    g.setHm_resname(string4);
                    g.setHm_usertype(string5);


                    GetCode dialog = new GetCode();
                    dialog.show(getSupportFragmentManager(),"GetTheCodeDialog");

                }
                SharedPreferences.Editor editor1 = first_pref_user_type.edit();
                editor1.putBoolean("first_start_user_type2", false);
                editor1.apply();

            }
        });



        }
        else {

            mySharedPreferences = getSharedPreferences(MY_PREFS, prefMode);
            String string1 = mySharedPreferences.getString("newkey1x", null);
            String string2 = mySharedPreferences.getString("newkey2x", null);
            String string3 = mySharedPreferences.getString("newkey3x", null);
            String string4 = mySharedPreferences.getString("newkey4x", null);
            String string5 = mySharedPreferences.getString("newkey5x", null);

            g.setHm_blockno(string1);
            g.setHm_flatno(string2);
            g.setHm_members(string3);
            g.setHm_members(string4);
            g.setHm_usertype(string5);

            Intent intent = new Intent(getApplicationContext(), ReceiptInfo.class);
            startActivity(intent);
        }

    }


}
