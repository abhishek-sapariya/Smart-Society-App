package com.example.darshi.homescreen;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;


//CUSTOM DIALOG
public class CreateReport extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    Button create_cancel,create_done,cfrom,cto;
    EditText fromdate,todate,amt,expense;
    DatePickerDialog.OnDateSetListener setListener;
    int year1,month1,day1;
    DatabaseReference reff,reff2;
    ReportMaster reportMaster;
    SubReportMaster subReportMaster;
    Globals g = Globals.getInstance();
    static  int c=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_create_report,container,false);

        create_done = view.findViewById(R.id.create_done);
        create_cancel = view.findViewById(R.id.create_cancel);
        fromdate = view.findViewById(R.id.from_date);
        todate = view.findViewById(R.id.to_date);
        cfrom = view.findViewById(R.id.cfrom);
        cto = view.findViewById(R.id.cto);
        amt = view.findViewById(R.id.amt);
        expense = view.findViewById(R.id.expense);
        reff = FirebaseDatabase.getInstance().getReference();
        reff2 = FirebaseDatabase.getInstance().getReference();
        create_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"Closing",Toast.LENGTH_LONG).show();
                getDialog().dismiss();
            }
        });

        create_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fromdate.getText().toString().equals(""))
                {
                    fromdate.setError("You must give the starting date");
                    fromdate.requestFocus();
                }
                else if(todate.getText().toString().equals(""))
                {
                    todate.setError("You must give the ending date");
                    todate.requestFocus();
                }
                else if(amt.getText().toString().equals(""))
                {
                    amt.setError("You must enter the maintenance fee amount");
                    amt.requestFocus();
                }
                else if(expense.getText().toString().equals(""))
                {
                    expense.setError("If there are no additional expenses enter 0");
                    expense.requestFocus();
                }
                else
                {
                    //Inserting in ReportMaster - done
                    reportMaster = new ReportMaster();
                    reportMaster.setAmt(amt.getText().toString());
                    reportMaster.setExpense(expense.getText().toString());
                    final String reportname = fromdate.getText().toString().concat(" To ").concat(todate.getText().toString()) ;
                    reff.child(g.getSname()).child("ReportMaster").child(reportname).setValue(reportMaster);
                    g.setLastcreatedreport(reportname);
                    g.setAmt(amt.getText().toString());
                    g.setExp(expense.getText().toString());

                    //FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("LastReportName").removeValue();
                    FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("LastReportName").setValue(g.getLastcreatedreport());
                    FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("LastReportAmt").setValue(g.getAmt());
                    FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("LastReportExpense").setValue(g.getExp());


                    //reff.child(g.getSname()).child("ReportMaster").child((c++)+reportname).setValue(reportMaster);


                    //Inserting in SubReportMaster - done

                    FirebaseDatabase.getInstance().getReference().child(g.getSname()).child("HouseMaster")
                            .addListenerForSingleValueEvent(new ValueEventListener() {

                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        for (DataSnapshot supportItem: dataSnapshot.getChildren()) {
                                            String houseno = supportItem.getKey();
                                            String residentName = supportItem.child("residentName").getValue().toString();
                                            subReportMaster = new SubReportMaster();
                                            subReportMaster.setPaidon1("Not Paid");
                                            subReportMaster.setStatus1("Pending");
                                            subReportMaster.setResidentname1(residentName);

                                            reff2.child(g.getSname()).child("SubReportMaster").child(reportname).child(houseno).setValue(subReportMaster);
                                        }
                                    }

                                }


                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                    /// creating a file that stores last report name, its amoutn and expense
//                    FileOutputStream fos = null;
//                    try {
//                        fos = getActivity().openFileOutput("data.txt",MODE_PRIVATE);
//                        fos.write(g.getLastcreatedreport().getBytes());
//                        fos = getActivity().openFileOutput("data1.txt",MODE_PRIVATE);
//                        fos.write(g.getAmt().getBytes());
//                        fos = getActivity().openFileOutput("data2.txt",MODE_PRIVATE);
//                        fos.write(g.getExp().getBytes());
//
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }finally {
//                        if (fos != null) {
//                            try {
//                                fos.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
                    ///

                    Toast.makeText(getContext(),"Saved",Toast.LENGTH_LONG).show();
                    getDialog().dismiss();

                    //Toast.makeText(getContext(),g.getLastcreatedreport(),Toast.LENGTH_LONG).show();

                }

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

        cfrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromdate.setText(day1+"-"+(month1+1)+"-"+year1);
            }
        });

        cto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todate.setText(day1+"-"+(month1+1)+"-"+year1);
            }
        });
        return view;
    }

    private void showDatePickerDialog()
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        year1 = year;
        month1 = month;
        day1 = dayOfMonth;

    }

}
