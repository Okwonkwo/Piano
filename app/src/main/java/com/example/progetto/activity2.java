package com.example.progetto;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.widget.Toast.LENGTH_LONG;
import static com.example.progetto.R.id.txt;
import java.util.Calendar;

public class activity2 extends AppCompatActivity {

    private static DecimalFormat df = new DecimalFormat("0.00");
    private static final String filename="PianoAmmortamenti.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        StringBuilder sb=new StringBuilder();

        Bundle extras = getIntent().getExtras();
        EditText txtNote= findViewById(R.id.txtNote);
        boolean Ita = extras.getBoolean("rdbItaliano");
        boolean Fra = extras.getBoolean("rdbFrancese");
        float capitale = extras.getFloat("txtCapitale");
        int rate = extras.getInt("txtRate");
        float tasso = extras.getFloat("txtTasso");
        float tassoA = extras.getFloat("txtTassoA");
        int i = 0;
        int a;
        int z = 0;
        Button btnsalva = findViewById(R.id.btnSalva);
        String fine="";

        GridView gridView = (GridView) findViewById(R.id.gridview);

        if(Ita==true) {

            float quotaCap;
            float capRes[] = new float[rate];
            float quotaInt[] = new float[rate];
            float impRat[] = new float[rate];
            quotaCap = capitale / rate;
            String ris[] = new String[rate*5+5];

            for (int x = 0; x < rate; x++)
            {
                if(i==0)
                {
                    capRes[i] = capitale - quotaCap;
                }
                else
                {
                    capRes[i]=capRes[i-1]-quotaCap;
                }
                if (i == 0) {
                    quotaInt[i] = capitale * tasso;
                } else {
                    quotaInt[i] = capRes[i - 1] * tasso;
                }
                impRat[i] = quotaCap + quotaInt[i];
                i++;
            }
            i=0;
            ris[0]="NRata";
            ris[1]="QuotaCap";
            ris[2]="Interessi";
            ris[3]="CapResiduo";
            ris[4]="ImportoRata";
            a=5;
            String b = "";
            sb.append("NRata\n");
            sb.append("QuotaCap\n");
            sb.append("Interessi\n");
            sb.append("CapResiduo\n");
            sb.append("ImportoRata\n");
            while(z<rate)
            {
                    b = String.valueOf(z + 1);
                    ris[a] = b;
                    sb.append(b).append("\n");

                    b = String.valueOf(df.format(quotaCap));
                    ris[a + 1] = b;
                    sb.append(b).append("\n");

                    b = String.valueOf(df.format(quotaInt[z]));
                    ris[a + 2] = b;
                    sb.append(b).append("\n");

                    b = String.valueOf(df.format(capRes[z]));
                    ris[a + 3] = b;
                    sb.append(b).append("\n");

                    b = String.valueOf(df.format(impRat[z]));
                    ris[a + 4] = b;
                    sb.append(b).append("\n");

                a=a+5;
                z++;
            }
            ArrayAdapter<String> risultato=new ArrayAdapter<String>(this, R.layout.row, txt, ris);
            gridView.setAdapter(risultato);
        }


        if(Fra==true) {

            i = 0;
            float quotaCap[] = new float[rate];
            float capRes[] = new float[rate];
            float quotaInt[] = new float[rate];
            float impRat;
            String ris[] = new String[rate*5+5];
            float aus= (1+tasso);
            impRat= (float) (capitale*(tasso/(1-(1/(Math.pow(aus,rate))))));

            for (int x = 0; x < rate; x++)
            {
                if(i==0)
                {
                    quotaInt[i]=capitale*tasso;
                    quotaCap[i]=impRat-quotaInt[i];
                    capRes[i]=capitale-quotaCap[i];
                }
                else
                {
                    quotaInt[i]=capRes[i-1]*tasso;
                    quotaCap[i]=impRat-quotaInt[i];
                    capRes[i]=capRes[i-1]-quotaCap[i];
                }
                i++;
            }
            i=0;
            ris[0]="NRata";
            ris[1]="QuotaCap";
            ris[2]="Interessi";
            ris[3]="CapResiduo";
            ris[4]="ImportoRata";
            sb.append("NRata\n");
            sb.append("QuotaCap\n");
            sb.append("Interessi\n");
            sb.append("CapResiduo\n");
            sb.append("ImportoRata\n");
            a=5;
            String b = "";
            while(z<rate)
            {
                    b = String.valueOf(z + 1);
                    ris[a] = b;
                    sb.append(b).append("\n");

                    b = String.valueOf(df.format(quotaCap[z]));
                    ris[a + 1] = b;
                    sb.append(b).append("\n");

                    b = String.valueOf(df.format(quotaInt[z]));
                    ris[a + 2] = b;
                    sb.append(b).append("\n");

                    b = String.valueOf(df.format(capRes[z]));
                    ris[a + 3] = b;
                    sb.append(b).append("\n");

                    b = String.valueOf(df.format(impRat));
                    ris[a + 4] = b;
                    sb.append(b).append("\n");


                a=a+5;
                z++;
            }
            ArrayAdapter<String> risultato=new ArrayAdapter<String>(this, R.layout.row, txt, ris);
            gridView.setAdapter(risultato);
        }

        btnsalva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.append(txtNote.getText().toString()+"\n\n\n");
                if(Ita==true)
                {
                    sb.append("Ammortamento all'italiana\n");
                }
                else
                {
                    sb.append("Ammortamento alla francese\n");
                }
                sb.append(Calendar.getInstance().getTime().toString()+"\n");
                sb.append("\n\n\n\n\n");
                String h="";
                FileOutputStream fos = null;
                try {
                    /*fos = openFileOutput(filename,MODE_PRIVATE);
                    fos.write(h.getBytes());*/
                    fos = openFileOutput(filename,MODE_APPEND);
                    fos.write(sb.toString().getBytes());

                    Toast.makeText(activity2.this, "Salvato in: "+getFilesDir(), LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(fos!= null)
                    {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
