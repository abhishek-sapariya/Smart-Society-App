package com.example.darshi.homescreen;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class GetCode extends AppCompatDialogFragment{

    Button donebtn1;
    TextView code;
    int c;
    Globals g = Globals.getInstance();
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_get_code, container, false);

        donebtn1 = view.findViewById(R.id.donebtn1);
        code = view.findViewById(R.id.code);

        //Toast.makeText(getContext(),"Entered",Toast.LENGTH_LONG).show();
        FileInputStream fis = null;

        if(g.getSname().equals(""))
        {
            try {

                fis = getActivity().openFileInput("socname.txt");
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String LastRepName;

                while ((LastRepName = br.readLine()) != null) {
                    sb.append(LastRepName);
                    g.setSname(sb.toString());
                }

            }catch (FileNotFoundException e) {
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

        }

        //String society_code = "a" + (++c) + "ab";
        Random number = new Random();
        c = number.nextInt(10000);

        String society_code = "a" + c + "ab";
        code.setText(society_code);
        donebtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String society_code = "a" + c + "ab";


                //FirebaseDatabase.getInstance().getReference().child("maincode").setValue(society_code);
                FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("code").setValue(society_code);
                Intent intent = new Intent(getContext(), ReceiptInfo.class);
                startActivity(intent);

            }
        });
        return view;

    }
}
