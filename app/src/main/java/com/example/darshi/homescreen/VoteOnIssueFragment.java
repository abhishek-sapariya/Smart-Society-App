package com.example.darshi.homescreen;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class VoteOnIssueFragment extends Fragment {

    Button uploadIssue,view_current_issue_btn,view_all_issues_btn;
    RecyclerView issue_recyclerview;
    LinearLayout linearLayout;
    TextView frag_title,frag_desc,frag_yes,frag_no,frag_voting_deadline,frag_deadline_time;
    private DatabaseReference LastIssueRef,IssueRef;
    Globals g = Globals.getInstance();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_vote_on_issue,container,false);

        uploadIssue = v.findViewById(R.id.upload_Issue);
        view_all_issues_btn = v.findViewById(R.id.view_all_issues);
        view_current_issue_btn = v.findViewById(R.id.view_current_issue);
        linearLayout =v.findViewById(R.id.currentIssue_view);
        issue_recyclerview = v.findViewById(R.id.issue_recyclerview);
        issue_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        issue_recyclerview.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);


        frag_title = v.findViewById(R.id.fragment_issue_title);
        frag_desc = v.findViewById(R.id.fragment_issue_desc);
        frag_yes = v.findViewById(R.id.fragment_yes_votes);
        frag_no = v.findViewById(R.id.fragment_no_votes);
        frag_voting_deadline = v.findViewById(R.id.voting_deadline_display);
        frag_deadline_time = v.findViewById(R.id.deadline_time_display);

        uploadIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateIssue dialog = new CreateIssue();
                dialog.show(getFragmentManager(),"MyCreateIssueDialog");
            }
        });

        view_current_issue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                issue_recyclerview.setVisibility(View.VISIBLE);

                IssueRef = FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("IssueMaster");
                // Filling the recycler view
                FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<IssueMaster>()
                        .setQuery(IssueRef,IssueMaster.class).build();

                FirebaseRecyclerAdapter<IssueMaster,IssueViewHolder> adapter = new FirebaseRecyclerAdapter<IssueMaster, IssueViewHolder>(options) {



                    @Override
                    protected void onBindViewHolder(@NonNull final IssueViewHolder holder, int position, @NonNull IssueMaster model) {

                        String residentID = getRef(position).getKey();

                        IssueRef.child(residentID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

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


                                    //comparing dates
                                    if (current_date.after(deadline_date))
                                    {
                                        holder.issue_cardview.setVisibility(View.GONE);
                                    }
                                    else if ((current_date.equals(deadline_date) && current_time.after(deadline_time)))
                                    {
                                        holder.issue_cardview.setVisibility(View.GONE);
                                    }
                                    else
                                    {
                                        holder.issue_cardview.setVisibility(View.VISIBLE);

                                        final String title1 = dataSnapshot.getKey();
                                        final String description1 = dataSnapshot.child("issue_description").getValue().toString();
                                        final String yes1 = dataSnapshot.child("yes_votes").getValue().toString();
                                        final String no1 = dataSnapshot.child("no_votes").getValue().toString();
                                        final String vote_deadline1 = dataSnapshot.child("voting_deadline").getValue().toString();
                                        final String deadline_time1 = dataSnapshot.child("deadline_time").getValue().toString();
                                        holder.title.setText(title1);
                                        holder.description.setText(description1);
                                        holder.yes.setText(yes1);
                                        holder.no.setText(no1);
                                        holder.vote_deadline.setText(vote_deadline1);
                                        holder.deadline_time.setText(deadline_time1);

                                    }
                                }catch (ParseException e) {
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
                    public IssueViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.issue_display_layout,viewGroup,false);
                        IssueViewHolder issueviewHolder = new IssueViewHolder(view);
                        return issueviewHolder;

                    }
                };

                issue_recyclerview.setAdapter(adapter);
                adapter.startListening();

            }
        });

        view_all_issues_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //linearLayout.setVisibility(View.GONE);
                issue_recyclerview.setVisibility(View.VISIBLE);

                IssueRef = FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("IssueMaster");
                // Filling the recycler view
                FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<IssueMaster>()
                        .setQuery(IssueRef,IssueMaster.class).build();

                FirebaseRecyclerAdapter<IssueMaster,IssueViewHolder> adapter = new FirebaseRecyclerAdapter<IssueMaster, IssueViewHolder>(options) {



                    @Override
                    protected void onBindViewHolder(@NonNull final IssueViewHolder holder, int position, @NonNull IssueMaster model) {

                        String residentID = getRef(position).getKey();

                        IssueRef.child(residentID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                final String title1 = dataSnapshot.getKey();
                                final String description1 = dataSnapshot.child("issue_description").getValue().toString();
                                final String yes1 = dataSnapshot.child("yes_votes").getValue().toString();
                                final String no1 = dataSnapshot.child("no_votes").getValue().toString();
                                final String vote_deadline1 = dataSnapshot.child("voting_deadline").getValue().toString();
                                final String deadline_time1 = dataSnapshot.child("deadline_time").getValue().toString();
                                holder.title.setText(title1);
                                holder.description.setText(description1);
                                holder.yes.setText(yes1);
                                holder.no.setText(no1);
                                holder.vote_deadline.setText(vote_deadline1);
                                holder.deadline_time.setText(deadline_time1);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    //mentioning the holder (xml layout) to use
                    @NonNull
                    @Override
                    public IssueViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.issue_display_layout,viewGroup,false);
                        IssueViewHolder issueviewHolder = new IssueViewHolder(view);
                        return issueviewHolder;

                    }
                };

                issue_recyclerview.setAdapter(adapter);
                adapter.startListening();

            }
        });
        return v;
    }

    //View Holder class
    public class IssueViewHolder extends RecyclerView.ViewHolder
    {
        TextView title,description,yes,no,vote_deadline,deadline_time;
        CardView issue_cardview;
        public IssueViewHolder(@NonNull final View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.display_issue_title);
            description = itemView.findViewById(R.id.display_issue_desc);
            yes = itemView.findViewById(R.id.display_yes_votes);
            no = itemView.findViewById(R.id.display_no_votes);
            vote_deadline = itemView.findViewById(R.id.display_voting_deadline_display);
            deadline_time = itemView.findViewById(R.id.display_deadline_time_display);
            issue_cardview = itemView.findViewById(R.id.issue_cardview);

        }

    }

}

