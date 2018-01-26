package com.example.mmatejam.drugikolokvij;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick1(View view) {
        Intent i = new Intent(this, Main2Activity.class);
        EditText et = (EditText) findViewById(R.id.tekst_box);
        String message = et.getText().toString();
        i.putExtra("slovo", message);
        i.putExtra("sTimSlovom", 1);
        startActivity(i);
    }

    public void onClick2(View view) {
        Intent i = new Intent(this, Main2Activity.class);
        EditText et = (EditText) findViewById(R.id.tekst_box);
        String message = et.getText().toString();
        i.putExtra("slovo", message);
        i.putExtra("sTimSlovom", 0);
        startActivity(i);
    }

    public void onClick3(View view) {

        DBAdapter db = new DBAdapter(this);

        db.open();
        long id = db.insertPodatak("CudoOdCokolade", "torta", "cokolada");
        id = db.insertPodatak("CudoOdSira", "torta", "sir");
        id = db.insertPodatak("Burek", "pita", "sir");
        id = db.insertPodatak("Madjarica", "pita", "badem");
        id = db.insertPodatak("TortaOdBadema", "torta", "badem");

        long id2 = db.insertPodatak2(15, "cokolada");
        id2 = db.insertPodatak2(30, "sir");
        id2 = db.insertPodatak2(80, "badem");
        db.close();


        db.open();
        Cursor c = db.getKolaci();
        if (c.moveToFirst())
        {
            do {
                DisplayKolac(c);
            } while (c.moveToNext());
        }

        Cursor c2 = db.getSastojci();
        if (c2.moveToFirst())
        {
            do {
                DisplaySastojci(c2);
            } while (c2.moveToNext());
        }

        db.close();


       /* db.open();
        Cursor cu = db.getKolac("badem");
        if (cu.moveToFirst())
            DisplayKolac(cu);
        else
            Toast.makeText(this, "No contact found", Toast.LENGTH_LONG).show();
        db.close();*/

        //---delete a contact---
        db.open();
        if (db.delete1(1))
            Toast.makeText(this, "Delete successful.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Delete failed.", Toast.LENGTH_LONG).show();
        if (db.delete2(1))
            Toast.makeText(this, "Delete successful.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Delete failed.", Toast.LENGTH_LONG).show();
        db.close();
    }

    public void DisplayKolac(Cursor c)
    {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LL);
        TextView textview  = (TextView) findViewById(R.id.textView);
        String str = "id: " + c.getString(0) + "\n" +
                "Naziv: " + c.getString(1) + "\n" +
                "Vrsta:  " + c.getString(2) + "\n" +
                "Sastojak:  " + c.getString(3);
        textview.setText(str);
        linearLayout.addView(textview);
 /*       Toast.makeText(this,
                "id: " + c.getString(0) + "\n" +
                        "Naziv: " + c.getString(1) + "\n" +
                        "Vrsta:  " + c.getString(2) + "\n" +
                    "Sastojak:  " + c.getString(3),
                    Toast.LENGTH_LONG).show();*/
    }
    public void DisplaySastojci(Cursor c)
    {
        Toast.makeText(this,
                "id: " + c.getString(0) + "\n" +
                        "Cijena: " + c.getString(1) + "\n" +
                        "Sastojak:  " + c.getString(2),
                Toast.LENGTH_LONG).show();
    }
}
