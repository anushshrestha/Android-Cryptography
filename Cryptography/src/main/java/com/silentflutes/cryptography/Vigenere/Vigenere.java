package com.silentflutes.cryptography.Vigenere;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.silentflutes.cryptography.Ciphers;
import com.silentflutes.cryptography.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * Created by sillentflutes on 4/1/14.
 */
public class Vigenere extends Ciphers implements View.OnClickListener{
    EditText msg;
    TextView tv;
    Button encrypt, decrypt;
    String vigKey;
    String s = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle fromCaesarConfig = getIntent().getExtras();
        if (fromCaesarConfig != null) {
            vigKey = fromCaesarConfig.getString("vigkey");
            setContentView(R.layout.encryptdecrypt);
            msg = (EditText) findViewById(R.id.etmsg);
            tv = (TextView) findViewById(R.id.tvresult);
            encrypt = (Button) findViewById(R.id.bencrypt);
            decrypt = (Button) findViewById(R.id.bdecrypt);

            decrypt.setVisibility(View.INVISIBLE);
            encrypt.setOnClickListener(this);

        } else {
            Toast.makeText(this, "Intent extra not found.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public String encrypt(String plainText) {
        String cipherText = "";
        char[] arrayOfPlainText = plainText.toCharArray();
        for (int i = 0; i < plainText.length(); i++) {
            char charOfKey = vigKey.charAt(i % vigKey.length());
            char charOfPlainText = arrayOfPlainText[i];

            //find index of character in planText using ALPHABET
            //find index of character of key using ALPHABET
            //msg+Key=cipher
            //add them and get char of that index from alphabet
            //add that char to string cipherText and return
            int shiftedPosition = ((ALPHABET.indexOf(Character.valueOf(charOfPlainText)))+
                                    ALPHABET.indexOf(Character.valueOf(charOfKey)))% ALPHABET.size();

            cipherText += ALPHABET.get(shiftedPosition);
        }
        return cipherText;


    }

    @Override
    public String decrypt(String cipherText) {

        String plainText = "";
        char[] arrayOfcipherText = cipherText.toCharArray();
        for (int i = 0; i < cipherText.length(); i++) {
            char charOfKey = vigKey.charAt(i % vigKey.length());
            char charOfcipherText = arrayOfcipherText[i];

            //msg=cipher -key
            //kist add alphabet size to prevent from negative value

            int shiftedPosition = (ALPHABET.size()+
                    ((ALPHABET.indexOf(Character.valueOf(charOfcipherText)))-
                    ALPHABET.indexOf(Character.valueOf(charOfKey))))% ALPHABET.size();

            plainText += ALPHABET.get(shiftedPosition);
        }
        return plainText;


    }

    @Override
    public void restoreFromFile(BufferedReader paramBufferedReader) {

    }

    @Override
    public void saveToFile(BufferedWriter paramBufferedWriter) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.bencrypt:

                if (msg.getText().toString().isEmpty())
                    Toast.makeText(this, "Msg empty.", Toast.LENGTH_SHORT).show();

                tv.setText(encrypt(msg.getText().toString().toLowerCase().trim()));
                decrypt.setVisibility(View.VISIBLE);
                decrypt.setOnClickListener(this);
                break;

            case R.id.bdecrypt:
                if (tv.getText().toString().isEmpty())
                    Toast.makeText(this, "Cipher text lost.", Toast.LENGTH_SHORT).show();

                tv.setText(decrypt(tv.getText().toString().toLowerCase().trim()));


                break;
        }
    }
}
