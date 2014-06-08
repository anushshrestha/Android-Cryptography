package com.silentflutes.cryptography.Vernam;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.silentflutes.cryptography.Ciphers;
import com.silentflutes.cryptography.R;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;

/**
 * Created by sillentflutes on 4/1/14.
 */

public class Vernam extends Ciphers implements View.OnClickListener {
    EditText msg;
    TextView tv;
    Button encrypt, decrypt;
    String s = "",binaryKey="";
    String vernamKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle fromVernamConfig = getIntent().getExtras();
        if (fromVernamConfig != null) {
            vernamKey = fromVernamConfig.getString("vernamKey");
            setContentView(R.layout.encryptdecrypt);

            msg = (EditText) findViewById(R.id.etmsg);
            tv = (TextView) findViewById(R.id.tvresult);
            encrypt = (Button) findViewById(R.id.bencrypt);
            decrypt = (Button) findViewById(R.id.bdecrypt);

            decrypt.setVisibility(View.INVISIBLE);
            encrypt.setOnClickListener(this);
        } else {
            //no default key
            Toast.makeText(this, "Intent extra not found.", Toast.LENGTH_SHORT).show();
        }
    }

    public String xorOperation(String firstString, String secondString) {
        String temp = "";
        for (int i = 0; i < firstString.length(); i++) {
            if (firstString.charAt(i) == secondString.charAt(i))
                temp += 0;
            else
                temp += 1;
        }
        return temp;
    }

    public String binaryToChar(String temp){
            String result = "";
            int j=0;
            String[] arrayOfBinaryString=new String[10];
            for(int i=0;i<temp.length()/7;i++) {
                arrayOfBinaryString[i] = temp.substring(j, j + 7);
                result += (char) Integer.parseInt(arrayOfBinaryString[i], 2);
                j+=7;
            }
                return result;

        }



    String toBinary(int l,char[] arrayOfChar){
        String binary="";
        for(int i=0;i<l;i++){
             binary += Integer.toBinaryString(arrayOfChar[i]);
        }

        return binary;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.bencrypt:
                if (msg.getText().toString().isEmpty())
                    Toast.makeText(this, "Msg empty.", Toast.LENGTH_SHORT).show();

                tv.setText(encrypt(msg.getText().toString().trim()));
                decrypt.setVisibility(View.VISIBLE);
                decrypt.setOnClickListener(this);
                break;

            case R.id.bdecrypt:
                if (tv.getText().toString().isEmpty())
                    Toast.makeText(this, "Cipher text lost.", Toast.LENGTH_SHORT).show();

                tv.setText(decrypt(tv.getText().toString().trim()));
                decrypt.setVisibility(View.INVISIBLE);


                break;
        }

    }


    @Override
    public String encrypt(String plainText) {
        int l=plainText.length();
        String key = vernamKey.substring(0,l);
        char[] arrayOfKey = key.toCharArray();
        char[] arrayOfPlainText = plainText.toCharArray();

        binaryKey=toBinary(l,arrayOfKey);
        String binaryPlainText=toBinary(l,arrayOfPlainText);
        String xoredString = "";
        //String cipherText="";
        xoredString= xorOperation(binaryKey, binaryPlainText);


        return xoredString;
    }

    @Override
    public String decrypt(String cipherText) {
        String xoredDecryptString= xorOperation(binaryKey,cipherText);
        return binaryToChar(xoredDecryptString);
    }

    @Override
    public void restoreFromFile(BufferedReader paramBufferedReader) {

    }

    @Override
    public void saveToFile(BufferedWriter paramBufferedWriter) {

    }
}

