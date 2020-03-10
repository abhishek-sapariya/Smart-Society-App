package com.example.darshi.homescreen;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class ResidentVoteOnIssueFragment1 extends Fragment {

    RecyclerView resident_issue_recycler_view;
    DatabaseReference IssueRef;
    String x[];
    Globals g = Globals.getInstance();


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_resident_vote_on_issue, container, false);


        resident_issue_recycler_view = v.findViewById(R.id.resident_issue_recyclerview);
        resident_issue_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        IssueRef = FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("IssueMaster");

        // Filling the recycler view
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<IssueMaster>().setQuery(IssueRef, IssueMaster.class).build();

        FirebaseRecyclerAdapter<IssueMaster, ResidentIssueViewHolder> adapter = new FirebaseRecyclerAdapter<IssueMaster, ResidentIssueViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull final ResidentIssueViewHolder holder, int position, @NonNull IssueMaster model) {

                String residentID = getRef(position).getKey();


                IssueRef.child(residentID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {

                        try {

                            //getting deadline date and time from database
                            String vote_deadline = dataSnapshot.child("voting_deadline").getValue().toString();
                            String vote_deadline_time = dataSnapshot.child("deadline_time").getValue().toString();

                            //converting it into date format
                            Date deadline_date = new SimpleDateFormat("dd-MM-yyyy").parse(vote_deadline);
                            Date deadline_time = new SimpleDateFormat("hh : mm").parse(vote_deadline_time);

                            //getting current date and time
                            Calendar calendar = Calendar.getInstance();

                            SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy");
                            String strDate = mdformat.format(calendar.getTime());

                            SimpleDateFormat mdformat1 = new SimpleDateFormat("hh : mm");
                            String strTime = mdformat1.format(calendar.getTime());

                            //converting into date format
                            Date current_date = new SimpleDateFormat("dd-MM-yyyy").parse(strDate);
                            Date current_time = new SimpleDateFormat("hh : mm").parse(strTime);




                                //Toast.makeText(getContext(),"Inside",Toast.LENGTH_LONG).show();
//                                FileInputStream fis = null;
//                                try {
//
//                                    //setreportname
//                                    fis = getActivity().openFileInput("issues.txt");
//                                    InputStreamReader isr = new InputStreamReader(fis);
//                                    BufferedReader br = new BufferedReader(isr);
//                                    StringBuilder sb = new StringBuilder();
//                                    String Issues;
//
//
//                                    while ((Issues = br.readLine()) != null) {
//                                        sb.append(Issues);
//
//                                    }
//
//                                    if(sb.toString().equals(""))
//                                    {
//
//                                    }
//                                    else
//                                    {
//                                        x = sb.toString().split("@");
//                                        for (String i:x)
//                                        {
//                                            g.votedon.add(i);
//                                        }
//                                    }
//                                } catch (FileNotFoundException e) {
//                                    e.printStackTrace();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }finally {
//                                    if (fis != null) {
//                                        try {
//                                            fis.close();
//                                        } catch (IOException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                }
//                            }


                            //comparing dates
                            if (current_date.after(deadline_date) || g.votedon.contains(dataSnapshot.getKey()) )
                            {
                                holder.res_issue_cardview.setVisibility(View.GONE);
                            }
                            else if((current_date.equals(deadline_date) && current_time.after(deadline_time)) || g.votedon.contains(dataSnapshot.getKey()))
                            {
                                holder.res_issue_cardview.setVisibility(View.GONE);
                            }
                            else
                            {
                                holder.res_issue_cardview.setVisibility(View.VISIBLE);

                                final String title = dataSnapshot.getKey();
                                final String description = dataSnapshot.child("issue_description").getValue().toString();
                                String deadline = dataSnapshot.child("voting_deadline").getValue().toString();
                                String deadline_time1 = dataSnapshot.child("deadline_time").getValue().toString();

                                holder.res_frag_title.setText(title);
                                holder.res_frag_desc.setText(description);
                                holder.res_frag_deadline.setText("Voting Deadline Date : "+deadline);
                                holder.res_frag_deadline_time.setText("Voting Deadline Time : "+deadline_time1);
                                holder.vote_yes_btn.setText("YES");
                                holder.vote_no_btn.setText("NO");

                                holder.vote_yes_btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        // adding to array and file
                                        g.votedon.add(dataSnapshot.getKey());



                                        FileOutputStream fos = null;
                                        try {
                                            fos = getActivity().openFileOutput("issues.txt",MODE_PRIVATE);
                                            Object[] x = g.votedon.toArray();
                                            for (Object i:x)
                                            {
                                                fos.write(i.toString().concat("@").getBytes());
                                            }


                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }finally {
                                            if (fos != null) {
                                                try {
                                                    fos.close();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }

                                        String NumOfYes = dataSnapshot.child("yes_votes").getValue().toString();
                                        int x1 = Integer.parseInt(NumOfYes);
                                        int x2 = x1+1;
                                        String NumOfYes2 = Integer.toString(x2);
                                        FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("IssueMaster").child(dataSnapshot.getKey())
                                                .child("yes_votes").setValue(NumOfYes2);

                                    }
                                });

                                holder.vote_no_btn.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {

                                        g.votedon.add(dataSnapshot.getKey());

                                        FileOutputStream fos = null;
                                        try {
                                            fos = getActivity().openFileOutput("issues.txt",MODE_PRIVATE);
                                            Object[] x = g.votedon.toArray();
                                            for (Object i:x)
                                            {
                                                fos.write(i.toString().concat("@").getBytes());
                                            }


                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }finally {
                                            if (fos != null) {
                                                try {
                                                    fos.close();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }

                                        String NumOfNo = dataSnapshot.child("no_votes").getValue().toString();
                                        int x1 = Integer.parseInt(NumOfNo);
                                        int x2 = x1+1;
                                        String NumOfNo2 = Integer.toString(x2);
                                        FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("IssueMaster").child(dataSnapshot.getKey())
                                                .child("no_votes").setValue(NumOfNo2);


//                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                                        builder.setTitle("Are you sure you want to vote 'NO' ?")
//                                                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                                                    @Override
//                                                    public void onClick(DialogInterface dialog, int which) {
//
//                                                    }
//                                                })
//                                                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                                                    @Override
//                                                    public void onClick(DialogInterface dialog, int which) {
//                                                        g.votedon.add(dataSnapshot.getKey());
//
//                                                        String NumOfNo = dataSnapshot.child("no_votes").getValue().toString();
//                                                        int x1 = Integer.parseInt(NumOfNo);
//                                                        int x2 = x1+1;
//                                                        String NumOfNo2 = Integer.toString(x2);
//                                                        FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("IssueMaster").child(dataSnapshot.getKey())
//                                                                .child("no_votes").setValue(NumOfNo2);
//
//                                                    }
//                                                });

                                        }

                                });

                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            //mentioning the holder (xml layout) to use
            @NonNull
            @Override
            public ResidentIssueViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.resident_issue_layout_display, viewGroup, false);
                ResidentIssueViewHolder issueviewHolder = new ResidentIssueViewHolder(view);
                return issueviewHolder;

            }
        };

        resident_issue_recycler_view.setAdapter(adapter);
        adapter.startListening();

        return v;
    }

    public class ResidentIssueViewHolder extends RecyclerView.ViewHolder {
        TextView res_frag_title,res_frag_desc,res_frag_deadline,res_frag_deadline_time;
        TextView txt1,txt2;
        Button vote_yes_btn,vote_no_btn;
        CardView res_issue_cardview;

        public ResidentIssueViewHolder(@NonNull final View itemView) {
            super(itemView);

            res_frag_title = itemView.findViewById(R.id.res_fragment_issue_title);
            res_frag_desc = itemView.findViewById(R.id.res_fragment_issue_desc);
            res_frag_deadline = itemView.findViewById(R.id.res_fragment_deadline_display);
            res_frag_deadline_time = itemView.findViewById(R.id.res_fragment_deadline_time_display);
            vote_no_btn = itemView.findViewById(R.id.vote_no_btn);
            vote_yes_btn = itemView.findViewById(R.id.vote_yes_btn);
            txt1 = itemView.findViewById(R.id.txt1);
            txt2 = itemView.findViewById(R.id.txt2);
            res_issue_cardview = itemView.findViewById(R.id.res_issue_cardview);

        }

    }
}
