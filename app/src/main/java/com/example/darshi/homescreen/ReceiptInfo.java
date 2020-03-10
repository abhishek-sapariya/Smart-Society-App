package com.example.darshi.homescreen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReceiptInfo extends AppCompatActivity {

    EditText receipt_title,addline1,addline2,addline3,pincode;
    Button receipts_info_done;
    Globals g = Globals.getInstance();


    int prefMode = Activity.MODE_PRIVATE;
    public static String MY_PREFS = "MY_PREFS";
    private SharedPreferences mySharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_info);

        mySharedPreferences = getSharedPreferences(MY_PREFS,prefMode);
        final SharedPreferences first_pref_receipt_info = getSharedPreferences("first_pref_receipt_info2", MODE_PRIVATE);
        boolean firstStartReceiptInfo = first_pref_receipt_info.getBoolean("first_start_receipt_info2", true);

        if (firstStartReceiptInfo)

        {
            if(g.getHm_usertype().equals("Secretary"))
            {
                receipt_title = findViewById(R.id.receipt_title);
                addline1 = findViewById(R.id.addline1);
                addline2 = findViewById(R.id.addline2);
                addline3 = findViewById(R.id.addline3);
                pincode = findViewById((R.id.pincode));
                receipts_info_done = findViewById(R.id.receipts_info_done);

                receipts_info_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(receipt_title.getText().toString().equals(""))
                        {
                            receipt_title.setError("You must fill this field");
                            receipt_title.requestFocus();
                        }
                        else if(addline1.getText().toString().equals(""))
                        {
                            addline1.setError("You must fill this field");
                            addline1.requestFocus();
                        }
                        else if(addline2.getText().toString().equals(""))
                        {
                            addline2.setError("You must fill this field");
                            addline2.requestFocus();
                        }
                        else if(addline3.getText().toString().equals(""))
                        {
                            addline3.setError("You must fill this field");
                            addline3.requestFocus();
                        }
                        else if(pincode.getText().toString().equals(""))
                        {
                            pincode.setError("You must fill this field");
                            pincode.requestFocus();
                        }
                        else
                        {

                            SharedPreferences.Editor editor = mySharedPreferences.edit();
                            editor.putString("key1x", receipt_title.getText().toString());
                            editor.putString("key2x", addline1.getText().toString());
                            editor.putString("key3x", addline2.getText().toString());
                            editor.putString("key4x", addline3.getText().toString());
                            editor.putString("key5x", pincode.getText().toString());
                            editor.commit();


                            mySharedPreferences = getSharedPreferences(MY_PREFS, prefMode);
                            String string1 = mySharedPreferences.getString("key1x", null);
                            String string2 = mySharedPreferences.getString("key2x", null);
                            String string3 = mySharedPreferences.getString("key3x", null);
                            String string4 = mySharedPreferences.getString("key4x", null);
                            String string5 = mySharedPreferences.getString("key5x", null);

                            g.setGlobal_receipt_title(string1);
                            g.setGlobal_addline1(string2);
                            g.setGlobal_addline2(string3);
                            g.setGlobal_addline3(string4);
                            g.setPincode(string5);


                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);


                            SharedPreferences.Editor editor1 = first_pref_receipt_info.edit();
                            editor1.putBoolean("first_start_receipt_info2", false);
                            editor1.apply();
                        }
                    }
                });

            }
            else
            {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        }
        else
        {
            mySharedPreferences = getSharedPreferences(MY_PREFS, prefMode);
            String string1 = mySharedPreferences.getString("key1x", null);
            String string2 = mySharedPreferences.getString("key2x", null);
            String string3 = mySharedPreferences.getString("key3x", null);
            String string4 = mySharedPreferences.getString("key4x", null);
            String string5 = mySharedPreferences.getString("key5x", null);

            g.setGlobal_receipt_title(string1);
            g.setGlobal_addline1(string2);
            g.setGlobal_addline2(string3);
            g.setGlobal_addline3(string4);
            g.setPincode(string5);

            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

    }
}
