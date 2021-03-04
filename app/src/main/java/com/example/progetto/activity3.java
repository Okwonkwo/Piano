package com.example.progetto;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;
import static com.example.progetto.R.id.txt;
import static java.sql.Types.NULL;

public class activity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3);
        GridView gridView = findViewById(R.id.gridview);

        ArrayList <String> s= getIntent().getStringArrayListExtra("fine");
        ArrayAdapter<String> risultato=new ArrayAdapter<String>(activity3.this, R.layout.row, txt, s);
        if(risultato.isEmpty())
        {
            Toast.makeText(this, "Non ci sono piani salvati",LENGTH_LONG).show();
        }
        else
        {
            gridView.setAdapter(risultato);
        }
    }
}
