package com.example.darshi.homescreen;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Globals g = Globals.getInstance();
    int prefMode = Activity.MODE_PRIVATE;
    public static String MY_PREFS = "MY_PREFS";
    private SharedPreferences mySharedPreferences;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.soc_code_option:
                FirebaseDatabase.getInstance().getReference().child(g.getSname()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String code = dataSnapshot.child("code").getValue().toString();
                        Toast.makeText(getApplicationContext(),code,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.soc_code,menu);

        if(g.getHm_usertype().equals("Resident"))
        {
            for (int i = 0; i < menu.size(); i++) {
                menu.getItem(i).setVisible(false);
            }        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FileInputStream fis = null;
        try {

            //setreportname
            fis = openFileInput("socname.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String Socname;

            while ((Socname = br.readLine()) != null) {
                sb.append(Socname);
                g.setSname(sb.toString());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        mySharedPreferences = getSharedPreferences(MY_PREFS, prefMode);
        String string5 = mySharedPreferences.getString("newkey5x", null);

        g.setHm_usertype(string5);

        String userType = g.getHm_usertype();
        Toast.makeText(getApplicationContext(),g.getHm_usertype(),Toast.LENGTH_LONG).show();


        Toast.makeText(getApplicationContext(),userType,Toast.LENGTH_LONG).show();
        if(userType.equals("Secretary"))
        {
            BottomNavigationView bottomnav = findViewById(R.id.bottomnav);
            bottomnav.setOnNavigationItemSelectedListener(navListener);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();



        }
        else if(userType.equals("Resident"))
        {
            BottomNavigationView bottomnav = findViewById(R.id.bottomnav);
            bottomnav.setOnNavigationItemSelectedListener(navListenerResident);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();


        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.home:
                            selectedFragment = new HomeFragment();
                            break;

                        case R.id.receipts:
                            selectedFragment = new ReceiptsFragment();
                            break;

                        case R.id.complaintBox:
                            selectedFragment = new ResidentComplaintsFragment();
                            break;

                        case R.id.voteOnIssue:
                            selectedFragment = new VoteOnIssueFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };



    private BottomNavigationView.OnNavigationItemSelectedListener navListenerResident =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.home:
                            selectedFragment = new HomeFragment();
                            break;

                        case R.id.receipts:
                            selectedFragment = new ResidentReceiptsFragment();
                            break;

                        case R.id.complaintBox:
                            selectedFragment = new ResidentComplaintsFragment();
                            break;

                        case R.id.voteOnIssue:
                            selectedFragment = new ResidentVoteOnIssueFragment1();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
