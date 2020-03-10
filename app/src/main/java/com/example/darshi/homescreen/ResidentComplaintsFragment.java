//package com.example.darshi.homescreen;
//
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//public class ResidentComplaintsFragment extends Fragment {
//
//    Button upload_complaint_btn,delete_complaint_btn,show_complaint_btn;
//    DatabaseReference noticeMaster;
//    public RecyclerView complaint_recycler_view;
//    DatabaseReference ComplaintRef;
//    Globals g = Globals.getInstance();
//    CreateComplaint createComplaint;
//    SocietyName societyName;
//    String uploadDate;
//    long counter = 0;
//    String temp,temp1;
//    List list = new ArrayList<String>();
//
//
//    //FOR SECRETARY
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v =inflater.inflate(R.layout.activity_resident_complaints_fragment,container,false);
//
//        upload_complaint_btn = v.findViewById(R.id.upload_complaint_btn);
//        delete_complaint_btn = v.findViewById(R.id.delete_complaint_btn);
//        ComplaintRef = FirebaseDatabase.getInstance().getReference(g.getSname());
//        show_complaint_btn = v.findViewById(R.id.show_complaint_btn);
//        complaint_recycler_view = v.findViewById(R.id.complaint_recycler_view);
//        complaint_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
//        //final String str  = g.getHm_blockno()+g.getHm_flatno();
//
//        if(g.getHm_usertype().equals("Secretary"))
//        {
//            upload_complaint_btn.setVisibility(View.GONE);
//            delete_complaint_btn.setVisibility(View.GONE);
//        }
//        else{
//            upload_complaint_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    CreateComplaint dialog = new CreateComplaint();
//                    dialog.show(getFragmentManager(),"MyCreateComplaintDialog");
//
//                }
//            });
//        }
//
//        ComplaintRef.child(g.getSname()).child("ComplaintMaster").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    counter = (dataSnapshot.getChildrenCount());
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("ComplaintMaster").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot item:dataSnapshot.getChildren()){
//                    temp = item.getKey();
//                    list.add(temp);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        show_complaint_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Toast.makeText(getActivity(),list.get(1).toString(),Toast.LENGTH_LONG).show();
//                if (g.getHm_usertype().equals("Secretary")) {
//                    final int size1 = list.size();
//                   // for(int i=0;i<size;i++){
//                        //final String res = list.get(i).toString();
//                        ComplaintRef = FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("ComplaintMaster");
//                        // Filling the recycler view
//                        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<ComplaintMaster>()
//                                .setQuery(ComplaintRef, ComplaintMaster.class).build();
//
//                        FirebaseRecyclerAdapter<ComplaintMaster, ComplaintViewHolder> adapter = new FirebaseRecyclerAdapter<ComplaintMaster, ComplaintViewHolder>(options) {
//
//                            @Override
//                            protected void onBindViewHolder(@NonNull final ComplaintViewHolder holder, int position, @NonNull ComplaintMaster model) {
//
//                                String residentID = getRef(position).getKey();
//                               // Toast.makeText(getActivity(), residentID, Toast.LENGTH_LONG).show();
//
//                                for(int i=0;i<size1;i++) {
//                                    final String res = list.get(i).toString();
//                                    ComplaintRef = FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("ComplaintMaster");
//                                    ComplaintRef.child(res).addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(DataSnapshot dataSnapshot) {
//                                            if (dataSnapshot.exists()) {
//                                                final String title1 = dataSnapshot.child("Complaints").child("complaint_description").getRef().getParent().getKey();
//                                                //final String description1 = dataSnapshot.child(list.get(i).toString()).child("Complaints").child(title1).child("complaint_description").getValue().toString();
//                                                //final String uploadedon1 = dataSnapshot.child("notice_uploaded_on").getValue().toString();
//                                                //uploadDate = dataSnapshot.child("complaint_uploaded_on").getValue().toString();
//                                                //final String uploadedon1 = "21-09-2019";
//                                                holder.title.setText(title1);
//                                                //holder.description.setText(description1);
//                                                holder.which_resident.setText(res);
//                                                //holder.uploaded_on.setText(uploadedon1);
//
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onCancelled(DatabaseError databaseError) {
//
//                                        }
//                                    });
//                                }
//
//                            }
//
//                            //mentioning the holder (xml layout) to use
//                            @NonNull
//                            @Override
//                            public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//                                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.complaint_display_layout, viewGroup, false);
//                                ComplaintViewHolder complaintViewHolder = new ComplaintViewHolder(view);
//                                return complaintViewHolder;
//
//                            }
//                        };
//
//                        complaint_recycler_view.setAdapter(adapter);
//                        adapter.startListening();
//
//
//                    //}
//
//                }
//                else {
//                    ComplaintRef = FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("ComplaintMaster").child(g.getHm_blockno() + g.getHm_flatno()).child("Complaints");
//                    // Filling the recycler view
//                    FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<ComplaintMaster>()
//                            .setQuery(ComplaintRef, ComplaintMaster.class).build();
//
//                    FirebaseRecyclerAdapter<ComplaintMaster, ComplaintViewHolder> adapter = new FirebaseRecyclerAdapter<ComplaintMaster, ComplaintViewHolder>(options) {
//
//                        @Override
//                        protected void onBindViewHolder(@NonNull final ComplaintViewHolder holder, int position, @NonNull ComplaintMaster model) {
//
//                            String residentID = getRef(position).getKey();
//
//                            ComplaintRef.child(residentID).addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                    if (dataSnapshot.exists()) {
//                                        final String title1 = dataSnapshot.getKey();
//                                        final String description1 = dataSnapshot.child("complaint_description").getValue().toString();
//                                        //final String uploadedon1 = dataSnapshot.child("notice_uploaded_on").getValue().toString();
//                                        uploadDate = dataSnapshot.child("complaint_uploaded_on").getValue().toString();
//                                        //final String uploadedon1 = "21-09-2019";
//                                        holder.title.setText(title1);
//                                        holder.description.setText(description1);
//                                        //holder.uploaded_on.setText(uploadedon1);
//
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(DatabaseError databaseError) {
//
//                                }
//                            });
//
//                        }
//
//                        //mentioning the holder (xml layout) to use
//                        @NonNull
//                        @Override
//                        public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//                            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.complaint_display_layout, viewGroup, false);
//                            ComplaintViewHolder complaintViewHolder = new ComplaintViewHolder(view);
//                            return complaintViewHolder;
//
//                        }
//                    };
//
//                    complaint_recycler_view.setAdapter(adapter);
//                    adapter.startListening();
//                }
//            }
//        });
//
//        delete_complaint_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final String str = createComplaint.str11;
//                if(str==""){
//                    Toast.makeText(getActivity(),"Please select item before delete",Toast.LENGTH_LONG).show();
//                }
//                else{
//                    ComplaintRef.child("ComplaintMaster").child(g.getHm_blockno()+g.getHm_flatno()).child("Complaints").child(str).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            ComplaintRef.child(str).removeValue();
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//
//                    /*NoticeRef.child("HouseMaster").child(g.getHm_blockno()+g.getHm_flatno()).child("Complaints").child(str).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            NoticeRef1.child(str).removeValue();
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });*/
//                    Toast.makeText(getActivity(),str,Toast.LENGTH_LONG).show();
//                }
////
//            }
//        });
//
//        return v;
//    }
//
//    //View Holder class
//    public class ComplaintViewHolder extends RecyclerView.ViewHolder
//    {
//        TextView title,description,uploaded_on,which_resident;
//
//        public ComplaintViewHolder(@NonNull final View itemView) {
//            super(itemView);
//
//            title = itemView.findViewById(R.id.display_complaint_title);
//            description = itemView.findViewById(R.id.display_complaint_desc);
//            which_resident = itemView.findViewById(R.id.display_complaint_res);
//            //uploaded_on = itemView.findViewById(R.id.display_uploaded_on);
//
//        }
//
//    }
//}


package com.example.darshi.homescreen;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ResidentComplaintsFragment extends Fragment {

    Button upload_complaint_btn,delete_complaint_btn,show_complaint_btn;
    DatabaseReference noticeMaster;
    public RecyclerView complaint_recycler_view;
    DatabaseReference NoticeRef;
    Globals g = Globals.getInstance();
    CreateComplaint createComplaint;
    SocietyName societyName;
    String uploadDate;
    long counter = 0;
    String temp,temp1,str1;
    List list = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.activity_resident_complaints_fragment,container,false);

        upload_complaint_btn = v.findViewById(R.id.upload_complaint_btn);
        //delete_complaint_btn = v.findViewById(R.id.delete_complaint_btn);
        NoticeRef = FirebaseDatabase.getInstance().getReference(g.getSname());
        show_complaint_btn = v.findViewById(R.id.show_complaint_btn);
        complaint_recycler_view = (RecyclerView) v.findViewById(R.id.complaint_recycler_view);
        complaint_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        final String str  = g.getHm_blockno()+g.getHm_flatno();

        if(g.getHm_usertype().equals("Secretary"))
        {
            upload_complaint_btn.setVisibility(View.GONE);
            //delete_complaint_btn.setVisibility(View.GONE);
        }
        else{
            upload_complaint_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CreateComplaint dialog = new CreateComplaint();
                    dialog.show(getFragmentManager(),"MyCreateComplaintDialog");

                }
            });
        }

        NoticeRef.child(g.getSname()).child("ComplaintMaster").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    counter = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("ComplaintMaster").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot item:dataSnapshot.getChildren()){
                    temp = item.getKey();
                    list.add(temp);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        show_complaint_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), CreateComplaint.str33, Toast.LENGTH_LONG).show();
                if(g.getHm_usertype().equals("Secretary")){
                    NoticeRef = FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("ComplaintMaster");
                }
                else{
                    NoticeRef = FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("HouseMaster").child(g.getHm_blockno()+g.getHm_flatno()).child("Complaints");
                }
                // Filling the recycler view
                FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<ComplaintMaster>()
                        .setQuery(NoticeRef, ComplaintMaster.class).build();

                final FirebaseRecyclerAdapter<ComplaintMaster, ComplaintViewHolder> adapter = new FirebaseRecyclerAdapter<ComplaintMaster, ComplaintViewHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull final ComplaintViewHolder holder, int position, @NonNull ComplaintMaster model) {

                        final String residentID = getRef(position).getKey();

                        NoticeRef.child(residentID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    final String title1 = dataSnapshot.getKey();
                                    final String description1 = dataSnapshot.child("complaint_description").getValue().toString();
                                    //final String uploadedon1 = dataSnapshot.child("notice_uploaded_on").getValue().toString();
                                    //uploadDate = dataSnapshot.child("complaint_uploaded_on").getValue().toString();
                                    //final String uploadedon1 = "21-09-2019";
                                    String str = g.getHm_blockno()+g.getHm_flatno();
                                    holder.title.setText(title1);
                                    holder.description.setText(description1);
                                    if(g.getHm_usertype().equals("Secretary")){
                                        holder.which_resident.setText(CreateComplaint.str33);
                                    }
                                    else{
                                        holder.which_resident.setText(str);
                                    }
                                    //holder.uploaded_on.setText(uploadedon1);

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        //}

                    }

                    //mentioning the holder (xml layout) to use
                    @NonNull
                    @Override
                    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.complaint_display_layout, viewGroup, false);
                        ComplaintViewHolder complaintViewHolder = new ComplaintViewHolder(view);
                        return complaintViewHolder;

                    }
                };

                complaint_recycler_view.setAdapter(adapter);
                adapter.startListening();


                //}

                //}
                /*else {
                    NoticeRef = FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("ComplaintMaster").child(g.getHm_blockno() + g.getHm_flatno()).child("Complaints");
                // Filling the recycler view
                FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<ComplaintMaster>()
                        .setQuery(NoticeRef, ComplaintMaster.class).build();

                FirebaseRecyclerAdapter<ComplaintMaster, ComplaintViewHolder> adapter = new FirebaseRecyclerAdapter<ComplaintMaster, ComplaintViewHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull final ComplaintViewHolder holder, int position, @NonNull ComplaintMaster model) {

                        String residentID = getRef(position).getKey();

                        NoticeRef.child(residentID).addValueEventListener(new ValueEventListener() {
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
                    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.complaint_display_layout, viewGroup, false);
                        ComplaintViewHolder complaintViewHolder = new ComplaintViewHolder(view);
                        return complaintViewHolder;

                    }
                };

                    complaint_recycler_view.setAdapter(adapter);
                    adapter.startListening();
                //}*/
            }
        });

        /*delete_complaint_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String str = CreateComplaint.str11;
                final String str1 = CreateComplaint.str22;
                Toast.makeText(getActivity(),CreateComplaint.str22,Toast.LENGTH_LONG).show();
                if(str=="" || str1==""){
                    Toast.makeText(getActivity(),"Please select item before delete",Toast.LENGTH_LONG).show();
                }
                else{
                    NoticeRef.child("ComplaintMaster").child(str1).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            NoticeRef.child(str1).removeValue();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    NoticeRef.child("HouseMaster").child(g.getHm_blockno()+g.getHm_flatno()).child("Complaints").child(str).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            NoticeRef.child(str).removeValue();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    Toast.makeText(getActivity(),str,Toast.LENGTH_LONG).show();
                }
//
            }
        });*/

        return v;
    }

    //View Holder class
    public class ComplaintViewHolder extends RecyclerView.ViewHolder
    {
        TextView title,description,uploaded_on,which_resident;

        public ComplaintViewHolder(@NonNull final View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.display_complaint_title);
            description = itemView.findViewById(R.id.display_complaint_desc);
            which_resident = itemView.findViewById(R.id.display_complaint_res);
            //uploaded_on = itemView.findViewById(R.id.display_uploaded_on);

        }

    }
}

