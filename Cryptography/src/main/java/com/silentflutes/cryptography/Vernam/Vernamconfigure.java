package com.silentflutes.cryptography.Vernam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.silentflutes.cryptography.Ciphers;
import com.silentflutes.cryptography.Monoalphabetic.Monoalphabetic;
import com.silentflutes.cryptography.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Random;

/**
 * Created by sillentflutes on 4/1/14.
 */
public class Vernamconfigure  extends Ciphers implements View.OnClickListener{

    Button bVernamGenerate,bVernamDone;
    TextView tvVernamKey;
    String randomKey;


    public String randomGenerator() {
        StringBuilder sb = new StringBuilder("abcdefghijklmnopqrstuvwxyz");
        Random RANDOM = new Random();
        for (int i = 25; i > 1; i--) {
            int swapWith = RANDOM.nextInt(i);
            char temp = sb.charAt(swapWith);
            sb.setCharAt(swapWith, sb.charAt(i));
            sb.setCharAt(i, temp);
        }
        return sb.toString();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vernam_key);

        bVernamGenerate = (Button) findViewById(R.id.bVernamGenerate);
        tvVernamKey=(TextView)findViewById(R.id.tvVernamKey);
        bVernamDone =(Button)findViewById(R.id.bVernamDone);

        bVernamGenerate.setOnClickListener(this);
        bVernamDone.setOnClickListener(this);

        randomKey=randomGenerator();


    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.bVernamGenerate:
                tvVernamKey.setText(randomKey);
                break;

            case R.id.bVernamDone:
                Intent toVernam = new Intent();
                toVernam.putExtra("vernamKey", randomKey);
                toVernam.setClass(Vernamconfigure.this,Vernam.class);
                startActivity(toVernam);
                //pass random key for encrypt
                break;
        }

    }

    @Override
    public String encrypt(String plainText) throws Exception {
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
