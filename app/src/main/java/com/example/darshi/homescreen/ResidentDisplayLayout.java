package com.example.darshi.homescreen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

public class ResidentDisplayLayout extends AppCompatActivity {

    Button statusbtn;
    private static final int STORAGE_CODE = 1000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resident_display_layout);

        statusbtn = findViewById(R.id.status);


        statusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, STORAGE_CODE);
                    }
                    else {
                        savePdf();
                    }
                }
                else {
                    savePdf();
                }
//                String statustxt = statusbtn.getText().toString();
//                if(statustxt.equals("Done"))
//                {
//                    statusbtn.setText("Pending");
//                }
            }
        });
    }

    public void savePdf() {
        Document mDoc = new Document(PageSize.A4);


        //String filename = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        String filename = "Trial1";
        String filepath = Environment.getExternalStorageDirectory() + "/" + filename+ ".pdf";

        try {
            PdfWriter.getInstance(mDoc, new FileOutputStream(filepath));
            mDoc.open();

            Font title = new Font(Font.FontFamily.HELVETICA, 20.0f,Font.BOLD, BaseColor.BLACK);
            Font addr = new Font(Font.FontFamily.HELVETICA, 15.0f,Font.BOLD,BaseColor.DARK_GRAY);
            //String text = pdfEdittext.getText().toString();
            mDoc.addAuthor("Darshi Shah");

//            Image imageRight = Image.getInstance("i2.jpg");
//            imageRight.setAlignment(Image.RIGHT);
//
//            mDoc.add(imageRight);
            mDoc.add(new LineSeparator());
            mDoc.add(new Paragraph("VASUPUJYA REGENCY MEMBERS ASSOCIATION",title));
            mDoc.add(new Paragraph("OPP. LITTLE FLOWER SCHOOL, BHATTHA, PALDI -380007",addr));
            mDoc.add(new Paragraph("\t\t\t\t\t\t Receipt No: 53"));
            mDoc.add(new Paragraph("Flat No : 202 " + "\t\t\t\t" + " Date:26-08-2019"));
            mDoc.add(new Paragraph("\n\n\n\n\n"));


            PdfPTable table = new PdfPTable(new float[]{3,3});


            table.addCell("Details");
            table.addCell("Amount");

            PdfPCell[] cells = table.getRow(0).getCells();
            for(int j=0;j<cells.length; j++)
            {
                cells[j].setBackgroundColor(BaseColor.GRAY);
            }


            table.addCell("\nName of the Resident:\nDarshi Shah\n");
            table.addCell("\nRs. 5000\n");
            table.addCell("\nExtra Expense:\n");
            table.addCell("\nRs. 500\n");
            table.addCell("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nTotal");
            table.addCell("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nRs. 5500");
            mDoc.add(table);

            mDoc.close();

            //Toast.makeText(this,filename+".pdf\nis saved to"+filepath,Toast.LENGTH_SHORT).show();

            try{
                Uri path = FileProvider.getUriForFile(this,"com.example.darshi.pdftrial",
                        new File(Environment.getExternalStorageDirectory() + "/" + filename+ ".pdf"));
                //Uri uri = Uri.parse("smsto:" + "919824693629");

                //Intent shareIntent = new Intent(Intent.ACTION_SENDTO,uri);
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT,"YOU HAVE PAID THE MAINTENANCE");
                shareIntent.putExtra(Intent.EXTRA_STREAM,path);
                shareIntent.setType("application/pdf");
                shareIntent.setPackage("com.whatsapp");
                startActivity(shareIntent);
            }
            catch(Exception ex){
                ex.printStackTrace();
                Toast.makeText(getApplicationContext(),"It may be that you don't have whatsapp",Toast.LENGTH_SHORT).show();
            }

        }
        catch (Exception ex)
        {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case STORAGE_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                    savePdf();
                }
                else{
                    Toast.makeText(this,"Permission has been Denied ",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
