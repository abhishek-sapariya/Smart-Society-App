//Manage Receipts Fragment-Darshi-Shows current maintenance Report and provides buttond for creating and viewing any report
package com.example.darshi.homescreen;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
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
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import com.example.darshi.homescreen.ClassForReportName;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;
import com.example.darshi.homescreen.CreateReport;
public class ReceiptsFragment extends Fragment {
    Dialog myDialog;
    Button create,view,currentReportbtn;
    public RecyclerView currentreport;
    private DatabaseReference Reportreff, ResidentsRef;
    TextView report_name;
    private static final int STORAGE_CODE = 1000;
    static int receipt_count=1;
    public final ClassForReportName cl = new ClassForReportName();
    Globals g = Globals.getInstance();
    SharedPreferences sharedPreferences;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_receipts, container, false);


        create = v.findViewById(R.id.create);
        view = v.findViewById(R.id.view);
        currentReportbtn = v.findViewById(R.id.currentReportbtn);
        report_name = v.findViewById(R.id.report_name);
        currentreport = v.findViewById(R.id.currentReport);
        currentreport.setLayoutManager(new LinearLayoutManager(getContext()));

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, STORAGE_CODE);
            }
            else{

            }

        }


        currentReportbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase.getInstance().getReference().child(g.getSname()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("LastReportName").exists()) {
                            g.setLastcreatedreport(dataSnapshot.child("LastReportName").getValue().toString());
                            g.setAmt(dataSnapshot.child("LastReportAmt").getValue().toString());
                            g.setExp(dataSnapshot.child("LastReportExpense").getValue().toString());

                            //Toast.makeText(getContext(),g.getAmt().toString(),Toast.LENGTH_SHORT).show();
                            Reportreff = FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("SubReportMaster").child(g.getLastcreatedreport());
                            ResidentsRef = FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("SubReportMaster").child(g.getLastcreatedreport());

                            report_name.setText(g.getLastcreatedreport());

                            // Filling the recycler view
                            FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Report>()
                                    .setQuery(Reportreff,Report.class).build();

                            FirebaseRecyclerAdapter<Report,ReportViewHolder> adapter = new FirebaseRecyclerAdapter<Report, ReportViewHolder>(options) {

                                @Override
                                protected void onBindViewHolder(@NonNull final ReportViewHolder holder, int position, @NonNull Report model) {

                                    String residentID = getRef(position).getKey();

                                    ResidentsRef.child(residentID).addValueEventListener(new ValueEventListener() {
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

                                                        //status and paid on fields updated
                                                        FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("SubReportMaster")
                                                                .child(reportname).child(housename).child("status1").setValue("Done");
                                                        FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("SubReportMaster")
                                                                .child(reportname).child(housename).child("paidon1").setValue(strDate);

                                                        //PDF with corresponding resident name sent...
                                                        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                                                            if(ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                                                    == PackageManager.PERMISSION_DENIED){
                                                                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                                                requestPermissions(permissions, STORAGE_CODE);
                                                            }
                                                            else{
                                                                savePdf(resname1,reportname,housename,strDate);

                                                            }

                                                        }
                                                        else {
                                                            savePdf(resname1,reportname,housename,strDate);

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

                                //mentioning the holder (xml layout) to use
                                @NonNull
                                @Override
                                public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                                    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.resident_display_layout,viewGroup,false);
                                    ReportViewHolder viewHolder = new ReportViewHolder(view);
                                    return viewHolder;

                                }
                            };

                            currentreport.setAdapter(adapter);
                            adapter.startListening();



                        } else {
                            Toast.makeText(getActivity(), "No Reports Exist", Toast.LENGTH_LONG).show();

                        }
                    }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


            }
        });


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),cl.getReportnamex(),Toast.LENGTH_LONG).show();
                CreateReport dialog = new CreateReport();

                dialog.show(getFragmentManager(),"MyCreateReportDialog");
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getContext(), "Opening ", Toast.LENGTH_LONG).show();
                ViewReport dialog = new ViewReport();
                dialog.show(getFragmentManager(),"MyViewReportDialog");

            }
        });

        return v;
    }

    //View Holder class
    // Actions after changing status
    public class ReportViewHolder extends RecyclerView.ViewHolder
    {
        TextView paidon,resname;
        Button status;
        public ReportViewHolder(@NonNull final View itemView) {
            super(itemView);

            paidon = itemView.findViewById(R.id.paidon);
            resname = itemView.findViewById(R.id.name);
            status = itemView.findViewById(R.id.status);

        }
    }

    //saving and sending PDF
    public void savePdf(final String nameOfResident, String nameOfReport, String nameOfHouse, String strDate) {

        Document mDoc = new Document(PageSize.A4);
        String filename = nameOfResident + " " + nameOfReport;
        String filepath = Environment.getExternalStorageDirectory() + "/" + filename+ ".pdf";
        //Toast.makeText(getContext(),"Here",Toast.LENGTH_LONG).show();

        try {

            PdfWriter.getInstance(mDoc, new FileOutputStream(filepath));
            mDoc.open();

            Font title = new Font(Font.FontFamily.HELVETICA, 20.0f,Font.BOLD, BaseColor.BLACK);
            Font addr = new Font(Font.FontFamily.HELVETICA, 15.0f,Font.BOLD,BaseColor.DARK_GRAY);

            mDoc.add(new LineSeparator());

            Paragraph ReceiptTitle = new Paragraph(g.getGlobal_receipt_title().toUpperCase(),title);
            ReceiptTitle.setAlignment(Element.ALIGN_CENTER);
            mDoc.add(ReceiptTitle);

            Paragraph AddressLine = new Paragraph(g.getGlobal_addline1().toUpperCase()+", "+g.getGlobal_addline2()+", "+g.getGlobal_addline3()+" - "+g.getPincode(),addr);
            AddressLine.setAlignment(Element.ALIGN_CENTER);
            mDoc.add(AddressLine);

            mDoc.add(new Paragraph("\t\t\t\t\t\t Receipt No: "+receipt_count++));
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
            table.addCell("\nRs. "+g.getAmt()+"\n");
            table.addCell("\nExtra Expense:\n");
            table.addCell("\nRs. "+g.getExp()+"\n");

            table.addCell("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nTotal");
            table.addCell("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nRs. " + (Integer.parseInt(g.getAmt())+Integer.parseInt(g.getExp())));

            mDoc.add(table);
            mDoc.close();

            try{

                Uri path = FileProvider.getUriForFile(getActivity(),"com.example.darshi.homescreen",
                        new File(Environment.getExternalStorageDirectory() + "/" + filename + ".pdf"));

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
                    Toast.makeText(getContext(),"You can now share receipts",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(),"Permission necessary to share receipts ",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
