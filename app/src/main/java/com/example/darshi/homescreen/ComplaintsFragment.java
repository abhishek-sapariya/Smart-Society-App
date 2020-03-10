package com.example.darshi.homescreen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class ComplaintsFragment extends Fragment {
    Button show_complaint_btn_sec;
    public RecyclerView complaint_recycler_view_sec;
    Globals g = Globals.getInstance();
    DatabaseReference ComplaintRef;
    String uploadDate;
    CreateComplaint createComplaint;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_complaint,container,false);

        ComplaintRef = FirebaseDatabase.getInstance().getReference(g.getSname());
        show_complaint_btn_sec = v.findViewById(R.id.show_complaint_btn_sec);
        complaint_recycler_view_sec = v.findViewById(R.id.complaint_recycler_view_sec);


        // NOT USED
        show_complaint_btn_sec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComplaintRef.child("ComplaintMaster").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ComplaintRef = FirebaseDatabase.getInstance().getReference().child(g.getSname())
                                .child("ComplaintMaster").child(g.getHm_blockno().concat(g.getHm_flatno())).child("Complaints");


                        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<ComplaintMaster>()
                                .setQuery(ComplaintRef, ComplaintMaster.class).build();

                        FirebaseRecyclerAdapter<ComplaintMaster, ComplaintsFragment.ComplaintViewHolder> adapter = new FirebaseRecyclerAdapter<ComplaintMaster, ComplaintsFragment.ComplaintViewHolder>(options) {

                            @Override
                            protected void onBindViewHolder(@NonNull final ComplaintsFragment.ComplaintViewHolder holder, int position, @NonNull ComplaintMaster model) {

                                String residentID = getRef(position).getKey();

                                ComplaintRef.child(residentID).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {


                                            final String title1 = dataSnapshot.getKey();
                                            final String description1 = dataSnapshot.child("complaint_description").getValue().toString();
                                            //final String uploadedon1 = dataSnapshot.child("notice_uploaded_on").getValue().toString();
                                            uploadDate = dataSnapshot.child("complaint_uploaded_on").getValue().toString();
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
                            public ComplaintsFragment.ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.complaint_display_layout, viewGroup, false);
                                ComplaintsFragment.ComplaintViewHolder complaintViewHolder = new ComplaintsFragment.ComplaintViewHolder(view);
                                return complaintViewHolder;

                            }
                        };

                        complaint_recycler_view_sec.setAdapter(adapter);
                        adapter.startListening();
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

//        show_complaint_btn_sec.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                    FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("ComplaintMaster").addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//                                for (DataSnapshot item : dataSnapshot.getChildren()) {
//                                    String str = dataSnapshot.getKey();
//                                    ComplaintRef = FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("ComplaintMaster").child(str).child("Complaints");
//                                    // Filling the recycler view
//                                    FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<ComplaintMaster>()
//                                            .setQuery(ComplaintRef, ComplaintMaster.class).build();
//
//                                    FirebaseRecyclerAdapter<ComplaintMaster, ComplaintsFragment.ComplaintViewHolder> adapter = new FirebaseRecyclerAdapter<ComplaintMaster, ComplaintsFragment.ComplaintViewHolder>(options) {
//
//                                        @Override
//                                        protected void onBindViewHolder(@NonNull final ComplaintsFragment.ComplaintViewHolder holder, int position, @NonNull ComplaintMaster model) {
//
//                                            String residentID = getRef(position).getKey();
//
//                                            ComplaintRef.child(residentID).addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                                    if (dataSnapshot.exists()) {
//
//
//                                                        final String title1 = dataSnapshot.getKey();
//                                                        final String description1 = dataSnapshot.child("complaint_description").getValue().toString();
//                                                        //final String uploadedon1 = dataSnapshot.child("notice_uploaded_on").getValue().toString();
//                                                        uploadDate = dataSnapshot.child("complaint_uploaded_on").getValue().toString();
//                                                        //final String uploadedon1 = "21-09-2019";
//                                                        holder.title.setText(title1);
//                                                        holder.description.setText(description1);
//                                                        //holder.uploaded_on.setText(uploadedon1);
//
//                                                    }
//                                                }
//
//                                                @Override
//                                                public void onCancelled(DatabaseError databaseError) {
//
//                                                }
//                                            });
//
//                                        }
//
//                                        //mentioning the holder (xml layout) to use
//                                        @NonNull
//                                        @Override
//                                        public ComplaintsFragment.ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//                                            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.complaint_display_layout, viewGroup, false);
//                                            ComplaintsFragment.ComplaintViewHolder complaintViewHolder = new ComplaintsFragment.ComplaintViewHolder(view);
//                                            return complaintViewHolder;
//
//                                        }
//                                    };
//
//                                    complaint_recycler_view_sec.setAdapter(adapter);
//                                    adapter.startListening();
//                                }
//                            }
//                        }
//
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//
//            });

        return v;
    }

    public class ComplaintViewHolder extends RecyclerView.ViewHolder
    {
        TextView title,description,uploaded_on;

        public ComplaintViewHolder(@NonNull final View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.display_complaint_title);
            description = itemView.findViewById(R.id.display_complaint_desc);
            //uploaded_on = itemView.findViewById(R.id.display_uploaded_on);

        }

    }
}
