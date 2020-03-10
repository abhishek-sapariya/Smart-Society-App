package com.example.darshi.homescreen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
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

import static android.content.Context.MODE_PRIVATE;

public class PutSocietyCode extends AppCompatDialogFragment {

    Globals g = Globals.getInstance();
    EditText society_code;
    Button check_code_btn;
    DatabaseReference HouseRef;
    HouseMaster houseMaster;
    SharedPreferences mySharedPreferences;
    int prefMode = Activity.MODE_PRIVATE;
    public static String MY_PREFS = "MY_PREFS";


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_put_society_code, container, false);

        FileInputStream fis = null;

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

        society_code = view.findViewById(R.id.soc_code);
        check_code_btn = view.findViewById(R.id.check_code);


        check_code_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String societyCode = society_code.getText().toString();
                //Toast.makeText(getContext(),societyCode,Toast.LENGTH_LONG).show();

                FirebaseDatabase.getInstance().getReference().child(g.getSname()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("code").getValue().toString().equals(societyCode))
                        {
                            Toast.makeText(getContext(),"Welcome",Toast.LENGTH_LONG).show();

                            HouseRef = FirebaseDatabase.getInstance().getReference();

                            HouseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    houseMaster = new HouseMaster();

                                    houseMaster.setBlock(g.getHm_blockno());
                                    houseMaster.setFlatNo(g.getHm_flatno());
                                    houseMaster.setResidentName(g.getHm_resname());
                                    houseMaster.setNoOfMembers(g.getHm_members());
                                    houseMaster.setUserType("Resident");
                                    HouseRef.child(g.getSname()).child("HouseMaster").child(g.getHm_blockno().concat(g.getHm_flatno())).setValue(houseMaster);
//
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

////                            g.setHm_blockno(house_blockno.getText().toString());
////                            g.setHm_flatno(house_flatno.getText().toString());
////                            g.setHm_members(house_members.getText().toString());
////                            g.setHm_resname(house_residentname.getText().toString());
//                            g.setHm_usertype("Resident");
//
//                            FileOutputStream fos = null;
//                            try {
//                                fos = getActivity().openFileOutput("memeberdetail.txt", MODE_PRIVATE);
//                                fos.write(g.getHm_blockno().concat(" ").concat(g.getHm_flatno()).concat(" ").concat(g.getHm_members())
//                                        .concat(" ").concat(g.getHm_usertype()).getBytes());
//
//                                fos = getActivity().openFileOutput("memebername.txt", MODE_PRIVATE);
//                                fos.write(g.getHm_resname().getBytes());
//
//                            } catch (FileNotFoundException e) {
//                                e.printStackTrace();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            } finally {
//                                if (fos != null) {
//                                    try {
//                                        fos.close();
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//
//
                            Intent intent = new Intent(getContext(),MainActivity.class);
                            startActivity(intent);

                            SharedPreferences preferences = getActivity().getSharedPreferences("first_pref_user_type2",MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean("first_start_user_type2", false);
                            editor.commit();

                        }
                        else
                        {
                            Toast.makeText(getContext(),"Wrong code",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        });
        return view;
    }
}
