package com.silentflutes.cryptography.Monoalphabetic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.silentflutes.cryptography.Ciphers;
import com.silentflutes.cryptography.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by sillentflutes on 4/1/14.
 */
public class Monoalphabeticconfigure extends Ciphers implements View.OnClickListener {

    Button bmonogenerate,bmonodone;
    TextView tvmonokey;
    String randomKey;

    //generate random key and display to user and proceede to encryption

    public String randomGenerator() {
        String randomString = "";
        ArrayList localArrayList = new ArrayList(ALPHABET);

        Random localRandom = new Random();//generate random time based on time of day
        while (true) {
            if (localArrayList.isEmpty())
                break;
            int i = localRandom.nextInt() % localArrayList.size();
            //next int gives negative value so make it positive
            if (i < 0) i = -i;
            randomString += ((Character) localArrayList.remove(i)).charValue();
            //remove character using random value i and adding it to string randomString
        }
        return randomString;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monoalphabetic_key);

        bmonogenerate = (Button) findViewById(R.id.bmonogenerate);
        tvmonokey=(TextView)findViewById(R.id.tvmonokey);
        bmonodone =(Button)findViewById(R.id.bmonodone);

        bmonogenerate.setOnClickListener(this);
        bmonodone.setOnClickListener(this);

        //THIS LINE SHOULD BE HERE. EPIC ERROR .
        randomKey=randomGenerator();
    }

    @Override
    public void onClick(View v) {


        //=randomGenerator();
        switch(v.getId()){

            case R.id.bmonogenerate:

                tvmonokey.setText(randomKey);
                break;

            case R.id.bmonodone:
                Intent toMono = new Intent();
                toMono.putExtra("monokey", randomKey);
                toMono.setClass(Monoalphabeticconfigure.this, Monoalphabetic.class);
                startActivity(toMono);
                //pass random key for encrypt
                break;
        }
    }

    @Override
    public String encrypt(String plainText) {
        return null;
    }

    @Override
    public String decrypt(String cipherText) {
        return null;
    }

    @Override
    public void restoreFromFile(BufferedReader paramBufferedReader) {

    }

    @Override
    public void saveToFile(BufferedWriter paramBufferedWriter) {

    }
}
