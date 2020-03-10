package com.example.darshi.homescreen;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ResidentReceiptsFragment extends Fragment {

    Button res_currentReportbtn;
    public RecyclerView res_currentreport;
    private DatabaseReference ResidentsRef;
    TextView res_report_name;
    Globals g = Globals.getInstance();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.resident_fragment_receipts, container, false);
        res_currentReportbtn = v.findViewById(R.id.res_currentReportbtn);
        res_report_name = v.findViewById(R.id.res_report_name);
        res_currentreport = v.findViewById(R.id.res_currentReport);
        res_currentreport.setLayoutManager(new LinearLayoutManager(getContext()));


        FileInputStream fis = null;
        try {

            //setreportname
//            fis = getActivity().openFileInput("data.txt");
//            InputStreamReader isr = new InputStreamReader(fis);
//            BufferedReader br = new BufferedReader(isr);
//            StringBuilder sb = new StringBuilder();
//            String LastRepName;
//
//            while ((LastRepName = br.readLine()) != null) {
//                sb.append(LastRepName);
//                g.setLastcreatedreport(sb.toString());
//            }

            fis = getActivity().openFileInput("socname.txt");
            InputStreamReader isr2 = new InputStreamReader(fis);
            BufferedReader br2 = new BufferedReader(isr2);
            StringBuilder sb2 = new StringBuilder();
            String SocName;

            while ((SocName = br2.readLine()) != null) {
                sb2.append(SocName);
                g.setSname(sb2.toString());
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



        res_currentReportbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase.getInstance().getReference().child(g.getSname()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("LastReportName").exists()) {
                            g.setLastcreatedreport(dataSnapshot.child("LastReportName").getValue().toString());




                            res_report_name.setText(g.getLastcreatedreport());


                            ResidentsRef = FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("SubReportMaster").child(g.getLastcreatedreport());


                            // Filling the recycler view
                            FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Report>()
                                    .setQuery(ResidentsRef, Report.class).build();

                            FirebaseRecyclerAdapter<Report, ResReportViewHolder> adapter = new FirebaseRecyclerAdapter<Report, ResReportViewHolder>(options) {

                                @Override
                                protected void onBindViewHolder(@NonNull final ResReportViewHolder holder, int position, @NonNull Report model) {

                                    String residentID = getRef(position).getKey();

                                    ResidentsRef.child(residentID).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            final String paidon1 = dataSnapshot.child("paidon1").getValue().toString();
                                            final String resname1 = dataSnapshot.child("residentname1").getValue().toString();

                                            String status1 = dataSnapshot.child("status1").getValue().toString();

                                            final String housename = dataSnapshot.getRef().getKey();
                                            final String reportname = dataSnapshot.getRef().getParent().getKey();

                                            holder.res_paidon.setText(paidon1);
                                            holder.res_resname.setText(resname1);
                                            holder.res_status.setText(status1);

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }

                                //mentioning the holder (xml layout) to use
                                @NonNull
                                @Override
                                public ResReportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                                    //Toast.makeText(getContext(),"Reached",Toast.LENGTH_LONG).show();
                                    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.res_resident_display_layout, viewGroup, false);
                                    ResReportViewHolder viewHolder = new ResReportViewHolder(view);
                                    return viewHolder;

                                }
                            };

                            res_currentreport.setAdapter(adapter);
                            adapter.startListening();



                        } else {
                            Toast.makeText(getActivity(), "No Reports", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }

        });


        return v;
    }

    public class ResReportViewHolder extends RecyclerView.ViewHolder
    {
        TextView res_paidon,res_resname,res_status;
        public ResReportViewHolder(@NonNull final View itemView) {
            super(itemView);

            res_paidon = itemView.findViewById(R.id.res_paidon);
            res_resname = itemView.findViewById(R.id.res_name);
            res_status = itemView.findViewById(R.id.res_status);

        }
    }
}
