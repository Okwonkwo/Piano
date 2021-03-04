package com.example.progetto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    public Button btnGo;
    public EditText txtCapitale;
    public EditText txtTasso;
    public EditText txtRate;
    public EditText txtTassoA;
    public RadioButton rdbItaliano;
    public RadioButton rdbFrancese;
    public Button btnVisualizza;
    private static final String filename="PianoAmmortamenti.txt";
    String inizio="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGo = findViewById(R.id.btnGo);
        txtCapitale = findViewById(R.id.txtCapitale);
        txtTasso = findViewById(R.id.txtTasso);
        txtRate = findViewById(R.id.txtRate);
        txtTassoA = findViewById(R.id.txtTassoA);
        rdbItaliano = findViewById(R.id.rdbItaliano);
        rdbFrancese = findViewById(R.id.rdbFrancese);
        btnVisualizza=findViewById(R.id.btnVisualizza);

        rdbItaliano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdbFrancese.setChecked(false);
            }
        });

        rdbFrancese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdbItaliano.setChecked(false);
            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this,activity2.class);
                intent.putExtra("rdbItaliano",rdbItaliano.isChecked());
                intent.putExtra("rdbFrancese",rdbFrancese.isChecked());
                intent.putExtra("txtCapitale", Float.parseFloat(txtCapitale.getText().toString()));
                intent.putExtra("txtTasso", Float.parseFloat(txtTasso.getText().toString()));
                intent.putExtra("txtRate", Integer.parseInt(txtRate.getText().toString()));
                intent.putExtra("txtTassoA", Float.parseFloat(txtTassoA.getText().toString()));
                startActivity(intent);
            }
        });

        btnVisualizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileInputStream fis = null;
                try {
                    fis = openFileInput(filename);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(isr);
                    ArrayList<String> sb=new ArrayList<String>();
                    sb.add("Numero rata");
                    sb.add("Quota Capitale");
                    sb.add("Quota Interessi");
                    sb.add("Capitale Residuo");
                    sb.add("Importo Rata");
                    int q =5;

                    while((inizio=br.readLine())!= null)
                    {
                        sb.add(inizio+"\n");
                        q++;
                    }
                    Intent intent = new Intent(MainActivity.this,activity3.class);
                    intent.putStringArrayListExtra("fine",sb);
                    startActivity(intent);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally
                {
                    if(fis!=null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}