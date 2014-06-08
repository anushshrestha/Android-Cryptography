package com.silentflutes.cryptography.Caesar;

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
 * Created by sillentflutes on 3/29/14.
 */
public class Caesar extends Ciphers implements View.OnClickListener {

    EditText msg;
    TextView tv;
    Button encrypt, decrypt;
    String s = "";
    int rotate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle fromCaesarConfig = getIntent().getExtras();
        if (fromCaesarConfig != null) {
            //if rotate value sent from config found the here
            rotate = fromCaesarConfig.getInt("rotation");
            setContentView(R.layout.encryptdecrypt);
            msg = (EditText) findViewById(R.id.etmsg);
            tv = (TextView) findViewById(R.id.tvresult);
            encrypt = (Button) findViewById(R.id.bencrypt);
            decrypt = (Button) findViewById(R.id.bdecrypt);

            decrypt.setVisibility(View.INVISIBLE);
            encrypt.setOnClickListener(this);

        } else {
            //else by default shift by 3
            rotate = 3;
            setContentView(R.layout.encryptdecrypt);
            msg = (EditText) findViewById(R.id.etmsg);
            tv = (TextView) findViewById(R.id.tvresult);
            encrypt = (Button) findViewById(R.id.bencrypt);
            decrypt = (Button) findViewById(R.id.bdecrypt);

            decrypt.setVisibility(View.INVISIBLE);
            encrypt.setOnClickListener(this);

        }
    }

    @Override
    public String encrypt(String plainText) {
        //take plain text, create array of char
        String cipherText = "";
        char[] arrayOfPlainText = plainText.toCharArray();
        for (int i = 0; i < plainText.length(); i++) {
            char singleCharacter = arrayOfPlainText[i];
            //find index of every character in alphabet array and increment by rotate...
            int shiftedPosition = (rotate + ALPHABET.indexOf(Character.valueOf(singleCharacter))) % ALPHABET.size();
            //add every new character to cipherText and return
            cipherText += ALPHABET.get(shiftedPosition);
        }
        return cipherText;
    }

    @Override
    public String decrypt(String cipherText) {
        String plainText = "";
        char[] arrayOfPlainText = cipherText.toCharArray();
        for (int i = 0; i < cipherText.length(); i++) {
            char singleCharacter = arrayOfPlainText[i];
            //reverse of encrypt but minus rotate might give negative value so, add 26 alphabet.size
            int shiftedPosition = (ALPHABET.size() + (ALPHABET.indexOf(Character.valueOf(singleCharacter))-rotate)) % ALPHABET.size();
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
                //take value fron edit text and pass to encrypt fxn

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
