package com.silentflutes.cryptography.Hill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.silentflutes.cryptography.Ciphers;
import com.silentflutes.cryptography.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;

import javax.crypto.Cipher;

/**
 * Created by sillentflutes on 4/1/14.
 */
public class Hillconfigure extends Activity implements View.OnClickListener{

    EditText hillKey11,hillKey12,hillKey13,hillKey21,hillKey22,hillKey23,hillKey31,hillKey32,hillKey33;
    int k11,k12,k13,k21,k22,k23,k31,k32,k33;
    int keyMat[][];
    Button hillKeyDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hill_key);

        hillKey11=(EditText)findViewById(R.id.et11);
        hillKey12=(EditText)findViewById(R.id.et12);
        hillKey13=(EditText)findViewById(R.id.et13);

        hillKey21=(EditText)findViewById(R.id.et21);
        hillKey22=(EditText)findViewById(R.id.et22);
        hillKey23=(EditText)findViewById(R.id.et23);

        hillKey31=(EditText)findViewById(R.id.et31);
        hillKey32=(EditText)findViewById(R.id.et32);
        hillKey33=(EditText)findViewById(R.id.et33);

        hillKeyDone=(Button)findViewById(R.id.bhilldone);

        hillKeyDone.setOnClickListener(this);

    }

    public int determinant(int a[][]) {


        return (a[0][0] * (a[1][1] * a[2][2] - a[2][1] * a[1][2])
                - a[1][0] * (a[0][1] * a[2][2] - a[2][1] * a[0][2])
                + a[2][0] * (a[0][1] * a[1][2] - a[1][1] * a[0][2]));
    }



    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.bhilldone:

                k11= Integer.parseInt(hillKey11.getText().toString().trim());
                k12= Integer.parseInt(hillKey12.getText().toString().trim());
                k13= Integer.parseInt(hillKey13.getText().toString().trim());

                k21= Integer.parseInt(hillKey21.getText().toString().trim());
                k22= Integer.parseInt(hillKey22.getText().toString().trim());
                k23= Integer.parseInt(hillKey23.getText().toString().trim());

                k31= Integer.parseInt(hillKey31.getText().toString().trim());
                k32= Integer.parseInt(hillKey32.getText().toString().trim());
                k33= Integer.parseInt(hillKey33.getText().toString().trim());


                keyMat[0][1]=k11;
                keyMat[0][2]=k12;
                keyMat[0][3]=k13;

                keyMat[1][1]=k21;
                keyMat[1][2]=k22;
                keyMat[1][3]=k23;

                keyMat[2][1]=k31;
                keyMat[2][2]=k32;
                keyMat[2][3]=k33;


                if(determinant(keyMat)==0)
                    Toast.makeText(this,"Inverse doesn't exist!",Toast.LENGTH_SHORT).show();
                   else {
                    ArrayList<Integer> hillKey = new ArrayList();
                /*
                    hillKey.add(0, k11);
                    hillKey.add(1, k12);
                    hillKey.add(2, k13);

                    hillKey.add(3, k21);
                    hillKey.add(4, k22);
                    hillKey.add(5, k23);

                    hillKey.add(6, k31);
                    hillKey.add(7, k32);
                    hillKey.add(8, k33);

                    //for testing...keeping static values from book

                    hillKey.add(0, 17);
                    hillKey.add(1, 17);
                    hillKey.add(2, 5);

                    hillKey.add(3, 21);
                    hillKey.add(4, 18);
                    hillKey.add(5, 21);

                    hillKey.add(6, 2);
                    hillKey.add(7, 2);
                    hillKey.add(8, 19);

                */
                    Intent toHill = new Intent();

                    //CODE TO CHECK IF DETERMINANT EXIST IS MISSING


                    toHill.putIntegerArrayListExtra("hill_key", hillKey);
                    toHill.setClass(Hillconfigure.this, Hill.class);
                    startActivity(toHill);
                }
                break;
        }

    }
}
