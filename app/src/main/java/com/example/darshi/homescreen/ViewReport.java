//package com.example.darshi.homescreen;
//
//import android.Manifest;
//import android.app.DatePickerDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Environment;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.DialogFragment;
//import android.support.v4.content.FileProvider;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
//import com.itextpdf.text.pdf.draw.LineSeparator;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//
//public class ViewReport extends DialogFragment implements DatePickerDialog.OnDateSetListener {
//
//    Button view_done, view_cancel, vfrom, vto,show_report_btn;
//    EditText fromdate, todate;
//    DatePickerDialog.OnDateSetListener setListener;
//    int year1, month1, day1;
//    DatabaseReference Reportrefff;
//    private static final int STORAGE_CODE = 1000;
//    RecyclerView anyReport;
//    public final ClassForReportName cl1 = new ClassForReportName();
//    Globals g = Globals.getInstance();
//
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.activity_view_report, container, false);
//
//        view_done = view.findViewById(R.id.view_done);
//        view_cancel = view.findViewById(R.id.view_cancel);
//        fromdate = view.findViewById(R.id.from_date);
//        todate = view.findViewById(R.id.to_date);
//        vfrom = view.findViewById(R.id.vfrom);
//        vto = view.findViewById(R.id.vto);
//        anyReport = view.findViewById(R.id.anyReport);
//        //show_report_btn = view.findViewById(R.id.show_report_btn);
//        anyReport.setLayoutManager(new LinearLayoutManager(getContext()));
//
//
//        view_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "Closing", Toast.LENGTH_LONG).show();
//                getDialog().dismiss();
//            }
//        });
//
//        view_done.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                if(fromdate.getText().toString().equals(""))
////                {
////                    fromdate.setError("You must give the starting date");
////                    fromdate.requestFocus();
////                }
////                else if(todate.getText().toString().equals(""))
////                {
////                    todate.setError("You must give the ending date");
////                    todate.requestFocus();
////                }
////                else
////                {
//                    String repname = fromdate.getText().toString()+ " To " + todate.getText().toString();
//                    Reportrefff = FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("SubReportMaster").child(repname);
//
//                    FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("ReportMaster").child(repname)
//                            .addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//
//                                    String amt = dataSnapshot.child("amt").getValue().toString();
//                                    cl1.setAmtx(amt);
//                                    //g.setAmt(amt);
//
//                                    String extraexp = dataSnapshot.child("expense").getValue().toString();
//                                    cl1.setExtraexpx(extraexp);
//                                    //g.setExp(extraexp);
//
//                                }
//
//
//
//                                @Override
//                                public void onCancelled(DatabaseError databaseError) {
//
//                                }
//                            });
////
////                    view_done.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View view) {
//                            //Recycler View
//                            FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Report>()
//                                    .setQuery(Reportrefff,Report.class).build();
//
//                            //Populating Receipts Fragment
//                            FirebaseRecyclerAdapter<Report,ViewReport.VRReportViewHolder> adapter = new FirebaseRecyclerAdapter<Report, ViewReport.VRReportViewHolder>(options) {
//
//                                @Override
//                                protected void onBindViewHolder(@NonNull final ViewReport.VRReportViewHolder holder, int position, @NonNull Report model) {
//
//                                    String residentID = getRef(position).getKey();
//                                    Reportrefff.child(residentID).addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(DataSnapshot dataSnapshot) {
//
//                                            final String paidon1 = dataSnapshot.child("paidon1").getValue().toString();
//                                            final String resname1 = dataSnapshot.child("residentname1").getValue().toString();
//                                            String status1 = dataSnapshot.child("status1").getValue().toString();
//
//                                            final String housename = dataSnapshot.getRef().getKey();
//                                            final String reportname = dataSnapshot.getRef().getParent().getKey();
//
//                                            holder.paidon.setText(paidon1);
//                                            holder.resname.setText(resname1);
//                                            holder.status.setText(status1);
//
//                                            Toast.makeText(getContext(),cl1.getAmtx(),Toast.LENGTH_SHORT).show();
//
//
//                                            holder.status.setOnClickListener(new View.OnClickListener() {
//                                                @Override
//                                                public void onClick(View v) {
//
//                                                    if(holder.status.getText().equals("Done"))
//                                                    {
//                                                        Toast.makeText(getContext(),"Receipt has already been sent",Toast.LENGTH_LONG).show();
//                                                    }
//                                                    else
//                                                    {
//
//                                                        Calendar calendar = Calendar.getInstance();
//                                                        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy ");
//                                                        String strDate = mdformat.format(calendar.getTime());
//
//                                                        //status and paid on fields updated
//                                                        FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("SubReportMaster")
//                                                                .child(reportname).child(housename).child("status1").setValue("Done");
//                                                        FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("SubReportMaster")
//                                                                .child(reportname).child(housename).child("paidon1").setValue(strDate);
//
//                                                        //PDF with corresponding resident name sent...
//                                                        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
//                                                            if(ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                                                                    == PackageManager.PERMISSION_DENIED){
//                                                                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
//                                                                requestPermissions(permissions, STORAGE_CODE);
//                                                            }
//                                                            else{
//                                                                savePdf(resname1,reportname,housename,strDate);
//
//                                                            }
//
//                                                        }
//                                                        else {
//                                                            savePdf(resname1,reportname,housename,strDate);
//
//                                                        }
//
//                                                    }
//
//                                                }
//                                            });
//
//                                            //Enabling same functionality
////                                            holder.status.setOnClickListener(new View.OnClickListener() {
////                                                @Override
////                                                public void onClick(View v) {
////
////                                                    if(holder.status.getText().equals("Done"))
////                                                    {
////                                                        Toast.makeText(getContext(),"Receipt has already been sent",Toast.LENGTH_LONG).show();
////                                                    }
////                                                    else
////                                                    {
////
////                                                        Calendar calendar = Calendar.getInstance();
////                                                        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy ");
////                                                        String strDate = mdformat.format(calendar.getTime());
////
////                                                        //status and paid on updated
////                                                        FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("SubReportMaster").child(reportname).child(housename).child("status1").setValue("Done");
////                                                        FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("SubReportMaster").child(reportname).child(housename).child("paidon1").setValue(strDate);
////
////
////                                                        //PDF with corresponding resident name sent...
////                                                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
////                                                            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
////                                                                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
////                                                                requestPermissions(permissions, STORAGE_CODE);
////                                                            } else {
////                                                                savePdf(resname1, reportname, housename, strDate);
////                                                            }
////
////                                                        } else {
////                                                            savePdf(resname1, reportname, housename, strDate);
////                                                        }
////
////                                                    }
////
////                                                }
////                                            });
//
//                                        }
//
//                                        @Override
//                                        public void onCancelled(DatabaseError databaseError) {
//
//                                        }
//                                    });
//                                }
//
//                                @NonNull
//                                @Override
//                                public VRReportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//                                    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.resident_display_layout,viewGroup,false);
//                                    VRReportViewHolder VRviewHolder = new VRReportViewHolder(view);
//                                    return VRviewHolder;
//
//                                }
//
//                            };
//
//                            anyReport.setAdapter(adapter);
//                            adapter.startListening();
//
//                            //Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
//                      //  }
//                    //});
//
//
//                //}
//
//
//            }
//        });
//
//        fromdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDatePickerDialog();
//
//            }
//        });
//
//        todate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDatePickerDialog();
//
//            }
//        });
//
//        vfrom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                fromdate.setText(day1 + "-" + (month1+1) + "-" + year1);
//
//            }
//        });
//
//        vto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                todate.setText(day1 + "-" + (month1+1) + "-" + year1);
//
//            }
//        });
//
//        return view;
//    }
//
//    public class VRReportViewHolder extends RecyclerView.ViewHolder
//    {
//        TextView paidon,resname;
//        Button status;
//        public VRReportViewHolder(@NonNull final View itemView) {
//            super(itemView);
//
//            paidon = itemView.findViewById(R.id.paidon);
//            resname = itemView.findViewById(R.id.name);
//            status = itemView.findViewById(R.id.status);
//
//        }
//    }
//
//    private void showDatePickerDialog() {
//
//        DatePickerDialog datePickerDialog = new DatePickerDialog(
//                getContext(), this,
//                Calendar.getInstance().get(Calendar.YEAR),
//                Calendar.getInstance().get(Calendar.MONTH),
//                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
//        );
//
//        datePickerDialog.show();
//    }
//
//    @Override
//    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//        year1 = year;
//        month1 = month;
//        day1 = dayOfMonth;
//
//    }
//
//    //saving and sending PDF
//    public void savePdf(final String nameOfResident, String nameOfReport, String nameOfHouse, String strDate) {
//
//
//        Document mDoc = new Document(PageSize.A4);
//        String filename = nameOfResident + " " + nameOfReport;
//        String filepath = Environment.getExternalStorageDirectory() + "/" + filename+ ".pdf";
//
//        try {
//
//            PdfWriter.getInstance(mDoc, new FileOutputStream(filepath));
//            mDoc.open();
//
//            Font title = new Font(Font.FontFamily.HELVETICA, 20.0f,Font.BOLD, BaseColor.BLACK);
//            Font addr = new Font(Font.FontFamily.HELVETICA, 15.0f,Font.BOLD,BaseColor.DARK_GRAY);
//
//            mDoc.add(new LineSeparator());
//
//            Paragraph ReceiptTitle = new Paragraph(g.getGlobal_receipt_title().toUpperCase(),title);
//            ReceiptTitle.setAlignment(Element.ALIGN_CENTER);
//            mDoc.add(ReceiptTitle);
//
//            Paragraph AddressLine = new Paragraph(g.getGlobal_addline1().toUpperCase()+", "+g.getGlobal_addline2()+", "+g.getGlobal_addline3()+" - "+g.getPincode(),addr);
//            AddressLine.setAlignment(Element.ALIGN_CENTER);
//            mDoc.add(AddressLine);
//
//            /*mDoc.add(new Paragraph(g.getGlobal_receipt_title().toUpperCase(),title));
//            mDoc.add(new Paragraph(g.getGlobal_addline1().toUpperCase()+", "+g.getGlobal_addline1().toUpperCase()
//                    +", "+g.getGlobal_addline3().toUpperCase()+" - "+g.getPincode(),addr));*/
//            //mDoc.add(new Paragraph("VASUPUJYA REGENCY MEMBERS ASSOCIATION",title));
//            //mDoc.add(new Paragraph("OPP. LITTLE FLOWER SCHOOL, BHATTHA, PALDI -380007",addr));
//            mDoc.add(new Paragraph("\t\t\t\t\t\t Receipt No: " + ReceiptsFragment.receipt_count++));
//            mDoc.add(new Paragraph("Flat No : "+nameOfHouse+ " \t\t\t\t" + " Date: "+strDate));
//            mDoc.add(new Paragraph("\n\n\n\n\n"));
//
//
//            final PdfPTable table = new PdfPTable(new float[]{3,3});
//            table.addCell("Details");
//            table.addCell("Amount");
//
//            PdfPCell[] cells = table.getRow(0).getCells();
//            for(int j=0;j<cells.length; j++)
//            {
//                cells[j].setBackgroundColor(BaseColor.GRAY);
//            }
//
//            table.addCell("\nName of the Resident:\n"+nameOfResident+"\n");
//            table.addCell("\nRs. "+cl1.getAmtx()+"\n");
//            table.addCell("\nExtra Expense:\n");
//            table.addCell("\nRs. "+cl1.getExtraexpx()+"\n");
//            table.addCell("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nTotal");
//            table.addCell("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nRs. " + (Integer.parseInt(cl1.getAmtx())+Integer.parseInt(cl1.getExtraexpx())));
//
//            mDoc.add(table);
//
//            mDoc.close();
//
//            try{
//
//                Uri path = FileProvider.getUriForFile(getActivity(),"com.example.darshi.homescreen",
//                        new File(Environment.getExternalStorageDirectory() + "/" + filename + ".pdf"));
//
//                //Uri uri = Uri.parse("smsto:" + "919824693629");
//                //Intent shareIntent = new Intent(Intent.ACTION_SENDTO,uri);
//
//                //sharing PDF
//                Intent shareIntent = new Intent();
//                shareIntent.setAction(Intent.ACTION_SEND);
//                shareIntent.putExtra(Intent.EXTRA_STREAM,path);
//                shareIntent.setType("application/pdf");
//                shareIntent.setPackage("com.whatsapp");
//                startActivity(shareIntent);
//
//            }
//            catch(Exception ex){
//                ex.printStackTrace();
//                Toast.makeText(getContext(),"It may be that you don't have whatsapp",Toast.LENGTH_SHORT).show();
//            }
//
//        }
//        catch (Exception ex)
//        {
//            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode){
//            case STORAGE_CODE:{
//                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    Toast.makeText(getContext(),"Permissions obtained. You can now try again",Toast.LENGTH_LONG).show();
//                }
//                else{
//                    Toast.makeText(getContext(),"Permission has been Denied ",Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }
//
//}
//
//
//


package com.example.darshi.homescreen;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ViewReport extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    Button view_done, view_cancel, vfrom, vto;
    EditText fromdate, todate;
    DatePickerDialog.OnDateSetListener setListener;
    int year1, month1, day1;
    DatabaseReference Reportrefff;
    private static final int STORAGE_CODE = 1000;
    RecyclerView anyReport;
    public final ClassForReportName cl1 = new ClassForReportName();
    Globals g = Globals.getInstance();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_view_report, container, false);

        view_done = view.findViewById(R.id.view_done);
        view_cancel = view.findViewById(R.id.view_cancel);
        fromdate = view.findViewById(R.id.from_date);
        todate = view.findViewById(R.id.to_date);
        vfrom = view.findViewById(R.id.vfrom);
        vto = view.findViewById(R.id.vto);
        anyReport = view.findViewById(R.id.anyReport);
        anyReport.setLayoutManager(new LinearLayoutManager(getContext()));


        view_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "Closing", Toast.LENGTH_LONG).show();
                getDialog().dismiss();
            }
        });

        view_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String repname = fromdate.getText().toString()+ " To " + todate.getText().toString();
                Reportrefff = FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("SubReportMaster").child(repname);

                FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("ReportMaster").child(repname)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                String amt = dataSnapshot.child("amt").getValue().toString();
                                cl1.setAmtx(amt);

                                String extraexp = dataSnapshot.child("expense").getValue().toString();
                                cl1.setExtraexpx(extraexp);

                            }



                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                //Recycler View
                FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Report>()
                        .setQuery(Reportrefff,Report.class).build();

                //Populating Receipts Fragment
                FirebaseRecyclerAdapter<Report,ViewReport.VRReportViewHolder> adapter = new FirebaseRecyclerAdapter<Report, ViewReport.VRReportViewHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull final ViewReport.VRReportViewHolder holder, int position, @NonNull Report model) {

                        String residentID = getRef(position).getKey();
                        Reportrefff.child(residentID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                final String paidon1 = dataSnapshot.child("paidon1").getValue().toString();
                                final String resname1 = dataSnapshot.child("residentname1").getValue().toString();
                                String status1 = dataSnapshot.child("status1").getValue().toString();

                                final String housename = dataSnapshot.getRef().getKey();
                                final String reportname = dataSnapshot.getRef().getParent().getKey();

                                holder.paidon.setText(paidon1);
                                holder.resname.setText(resname1);
                                holder.status.setText(status1);

                                //Enabling same functionality
                                holder.status.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if(holder.status.getText().equals("Done"))
                                        {
                                            Toast.makeText(getContext(),"Receipt has already been sent",Toast.LENGTH_LONG).show();
                                        }
                                        else
                                        {

                                            Calendar calendar = Calendar.getInstance();
                                            SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy ");
                                            String strDate = mdformat.format(calendar.getTime());

                                            //status and paid on updated
                                            FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("SubReportMaster").child(reportname).child(housename).child("Status").setValue("Done");
                                            FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("SubReportMaster").child(reportname).child(housename).child("PaidOn").setValue(strDate);


                                            //PDF with corresponding resident name sent...
                                            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                                                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                                                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                                    requestPermissions(permissions, STORAGE_CODE);
                                                } else {
                                                    savePdf(resname1, reportname, housename, strDate);
                                                }

                                            } else {
                                                savePdf(resname1, reportname, housename, strDate);
                                            }

                                        }

                                    }
                                });

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public VRReportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.resident_display_layout,viewGroup,false);
                        VRReportViewHolder VRviewHolder = new VRReportViewHolder(view);
                        return VRviewHolder;

                    }

                };

                anyReport.setAdapter(adapter);
                adapter.startListening();

                //Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();

            }
        });

        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();

            }
        });

        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();

            }
        });

        vfrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fromdate.setText(day1 + "-" + (month1+1) + "-" + year1);

            }
        });

        vto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                todate.setText(day1 + "-" + (month1+1) + "-" + year1);

            }
        });

        return view;
    }

    public class VRReportViewHolder extends RecyclerView.ViewHolder
    {
        TextView paidon,resname;
        Button status;
        public VRReportViewHolder(@NonNull final View itemView) {
            super(itemView);

            paidon = itemView.findViewById(R.id.paidon);
            resname = itemView.findViewById(R.id.name);
            status = itemView.findViewById(R.id.status);

        }
    }

    private void showDatePickerDialog() {
        //Toast.makeText(getContext(),"1",Toast.LENGTH_SHORT).show();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(), this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //Toast.makeText(getContext(), "3", Toast.LENGTH_SHORT).show();
        year1 = year;
        month1 = month;
        day1 = dayOfMonth;

    }

    //saving and sending PDF
    public void savePdf(final String nameOfResident, String nameOfReport, String nameOfHouse, String strDate) {


        Document mDoc = new Document(PageSize.A4);
        String filename = nameOfResident + " " + nameOfReport;
        String filepath = Environment.getExternalStorageDirectory() + "/" + filename+ ".pdf";

        try {

            PdfWriter.getInstance(mDoc, new FileOutputStream(filepath));
            mDoc.open();

            Font title = new Font(Font.FontFamily.HELVETICA, 20.0f,Font.BOLD, BaseColor.BLACK);
            Font addr = new Font(Font.FontFamily.HELVETICA, 15.0f,Font.BOLD,BaseColor.DARK_GRAY);

            mDoc.add(new LineSeparator());
            mDoc.add(new Paragraph("VASUPUJYA REGENCY MEMBERS ASSOCIATION",title));
            mDoc.add(new Paragraph("OPP. LITTLE FLOWER SCHOOL, BHATTHA, PALDI -380007",addr));
            mDoc.add(new Paragraph("\t\t\t\t\t\t Receipt No: " + ReceiptsFragment.receipt_count++));
            mDoc.add(new Paragraph("Flat No : "+nameOfHouse+ " \t\t\t\t" + " Date: "+strDate));
            mDoc.add(new Paragraph("\n\n\n\n\n"));


            final PdfPTable table = new PdfPTable(new float[]{3,3});
            table.addCell("Details");
            table.addCell("Amount");

            PdfPCell[] cells = table.getRow(0).getCells();
            for(int j=0;j<cells.length; j++)
            {
                cells[j].setBackgroundColor(BaseColor.GRAY);
            }

            table.addCell("\nName of the Resident:\n"+nameOfResident+"\n");
            table.addCell("\nRs. "+cl1.getAmtx()+"\n");
            table.addCell("\nExtra Expense:\n");
            table.addCell("\nRs. "+cl1.getExtraexpx()+"\n");
            table.addCell("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nTotal");
            table.addCell("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nRs. " + (Integer.parseInt(cl1.getAmtx())+Integer.parseInt(cl1.getExtraexpx())));

            mDoc.add(table);

            mDoc.close();

            try{

                Uri path = FileProvider.getUriForFile(getActivity(),"com.example.darshi.homescreen",
                        new File(Environment.getExternalStorageDirectory() + "/" + filename + ".pdf"));

                //Uri uri = Uri.parse("smsto:" + "919824693629");
                //Intent shareIntent = new Intent(Intent.ACTION_SENDTO,uri);

                //sharing PDF
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM,path);
                shareIntent.setType("application/pdf");
                shareIntent.setPackage("com.whatsapp");
                startActivity(shareIntent);

            }
            catch(Exception ex){
                ex.printStackTrace();
                Toast.makeText(getContext(),"It may be that you don't have whatsapp",Toast.LENGTH_SHORT).show();
            }

        }
        catch (Exception ex)
        {
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case STORAGE_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getContext(),"Permissions obtained. You can now try again",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(),"Permission has been Denied ",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}

