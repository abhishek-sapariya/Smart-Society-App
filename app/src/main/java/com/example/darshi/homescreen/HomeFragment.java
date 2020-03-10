package com.example.darshi.homescreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class HomeFragment extends Fragment {

    Button upload_notice_btn,delete_notice_btn,show_notice_btn;
    DatabaseReference noticeMaster;
    public RecyclerView notice_recycler_view;
    DatabaseReference NoticeRef;
    Globals g = Globals.getInstance();
    CreateNotice createNotice;
    SocietyName societyName;
    String uploadDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_home,container,false);

        upload_notice_btn = v.findViewById(R.id.upload_notice_btn);
        delete_notice_btn = v.findViewById(R.id.delete_notice_btn);
        NoticeRef = FirebaseDatabase.getInstance().getReference(g.getSname());
        show_notice_btn = v.findViewById(R.id.show_notice_btn);
        notice_recycler_view = v.findViewById(R.id.notice_recycler_view);
        notice_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));

        //Toast.makeText(getContext(),g.getHm_usertype(),Toast.LENGTH_SHORT).show();
        if(g.getHm_usertype().equals("Resident"))
        {
            upload_notice_btn.setVisibility(View.GONE);
            delete_notice_btn.setVisibility(View.GONE);
        }
        else{
            upload_notice_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CreateNotice dialog = new CreateNotice();
                    dialog.show(getFragmentManager(),"MyCreateNoticeDialog");

                }
            });

        }

        show_notice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoticeRef = FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("NoticeMaster");
                // Filling the recycler view
                FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<NoticeMaster>()
                        .setQuery(NoticeRef,NoticeMaster.class).build();

                final FirebaseRecyclerAdapter<NoticeMaster,NoticeViewHolder> adapter = new FirebaseRecyclerAdapter<NoticeMaster, NoticeViewHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull final NoticeViewHolder holder, int position, @NonNull NoticeMaster model) {

                        String residentID = getRef(position).getKey();

                        NoticeRef.child(residentID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    final String title1 = dataSnapshot.getKey();
                                    final String description1 = dataSnapshot.child("notice_description").getValue().toString();
                                    final String uploadedon1 = dataSnapshot.child("notice_uploaded_on").getValue().toString();
                                    uploadDate = dataSnapshot.child("notice_uploaded_on").getValue().toString();
                                    //final String uploadedon1 = "21-09-2019";
                                    holder.title.setText(title1);
                                    holder.description.setText(description1);
                                    //holder.uploaded_on.setText(uploadedon1);
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
                    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notice_display_layout,viewGroup,false);
                        NoticeViewHolder noticeviewHolder = new NoticeViewHolder(view);
                        return noticeviewHolder;

                    }
                };

                notice_recycler_view.setAdapter(adapter);
                adapter.startListening();


            }
        });

        delete_notice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String str = createNotice.str1;
                if(str==""){
                    Toast.makeText(getActivity(),"Please select item before delete",Toast.LENGTH_LONG).show();
                }
                else{
                    NoticeRef.child("NoticeMaster").child(str).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            NoticeRef.child(str).removeValue();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    //Toast.makeText(getActivity(),str,Toast.LENGTH_LONG).show();
                }
            }
        });

        return v;
    }

    //View Holder class
    public class NoticeViewHolder extends RecyclerView.ViewHolder
    {
        TextView title,description,uploaded_on;

        public NoticeViewHolder(@NonNull final View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.display_notice_title);
            description = itemView.findViewById(R.id.display_notice_desc);
            //uploaded_on = itemView.findViewById(R.id.display_uploaded_on);

        }

    }
}

