package com.silentflutes.cryptography.Hill;

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
import java.util.ArrayList;

/**
 * Created by sillentflutes on 4/1/14.
 */


public class Hill extends Ciphers implements View.OnClickListener {

    EditText msg;
    TextView tv;
    Button encrypt, decrypt;
    ArrayList<Integer> hillKey;
    int[] row1OfKey = {0, 0, 0};
    int[] row2OfKey = {0, 0, 0};
    int[] row3OfKey = {0, 0, 0};


    public int determinant(int a[][]) {


        return (a[0][0] * (a[1][1] * a[2][2] - a[2][1] * a[1][2])
                - a[1][0] * (a[0][1] * a[2][2] - a[2][1] * a[0][2])
                + a[2][0] * (a[0][1] * a[1][2] - a[1][1] * a[0][2]));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //matrix is not created until the user clicks the encrypt button

        super.onCreate(savedInstanceState);
        setContentView(R.layout.encryptdecrypt);
        hillKey = getIntent().getExtras().getIntegerArrayList("hill_key");

        row1OfKey[0] = hillKey.get(0);
        row1OfKey[1] = hillKey.get(1);
        row1OfKey[2] = hillKey.get(2);

        row2OfKey[0] = hillKey.get(3);
        row2OfKey[1] = hillKey.get(4);
        row2OfKey[2] = hillKey.get(5);

        row3OfKey[0] = hillKey.get(6);
        row3OfKey[1] = hillKey.get(7);
        row3OfKey[2] = hillKey.get(8);


        msg = (EditText) findViewById(R.id.etmsg);
        tv = (TextView) findViewById(R.id.tvresult);

        encrypt = (Button) findViewById(R.id.bencrypt);
        decrypt = (Button) findViewById(R.id.bdecrypt);

        decrypt.setVisibility(View.INVISIBLE);
        encrypt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.bencrypt:

                decrypt.setVisibility(View.VISIBLE);
                decrypt.setOnClickListener(this);

                String cipherText = encrypt(msg.getText().toString().trim());
                if (cipherText != null)
                    tv.setText(cipherText);
                else
                    Toast.makeText(this, "Message contains invalid character.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.bdecrypt:
                String cipher = tv.getText().toString().trim();
                String plainText = decrypt(cipher);
                tv.setText(plainText);
                decrypt.setVisibility(View.INVISIBLE);
                break;
        }
    }


    @Override
    public String encrypt(String plainText) {
        String cipherText = "";
        int counter = 0;
        for (int i = 0; i < plainText.length() / 3; i = i++) {
            if ((counter + 3) > plainText.length()) break;

            int shiftedPosition = 0;
            int position=0;
            String groupOfThree = plainText.substring(counter, counter + 3);
            // int row1,row2,row3;

            for (int j = 0; j < row1OfKey.length; j++) {
                shiftedPosition += row1OfKey[position] * ALPHABET.indexOf(groupOfThree.charAt(position++));
            }
            cipherText += ALPHABET.get(shiftedPosition % ALPHABET.size());

            shiftedPosition = 0;position=0;
            for (int j = 0; j < row2OfKey.length; j++) {
                shiftedPosition += row2OfKey[position] * ALPHABET.indexOf(groupOfThree.charAt(position++));
            }
            cipherText += ALPHABET.get(shiftedPosition % ALPHABET.size());

            shiftedPosition = 0;position=0;
            for (int j = 0; j < row3OfKey.length; j++) {
                shiftedPosition += row3OfKey[position] * ALPHABET.indexOf(groupOfThree.charAt(position++));
            }
            cipherText += ALPHABET.get(shiftedPosition % ALPHABET.size());

            counter = counter + 3;
        }
    return cipherText;

}

    @Override
    public String decrypt(String cipherText) {

        row1OfKey[0] = 4;
        row1OfKey[1] = 9;
        row1OfKey[2] = 15;

        row2OfKey[0] = 15;
        row2OfKey[1] = 17;
        row2OfKey[2] = 6;

        row3OfKey[0] = 24;
        row3OfKey[1] = 0;
        row3OfKey[2] = 17;

        String plainText = "";
        int counter = 0;
        for (int i = 0; i < cipherText.length() / 3; i = i++) {
            if ((counter + 3) > cipherText.length()) break;

            int shiftedPosition = 0;
            int position=0;
            String groupOfThree = cipherText.substring(counter, counter + 3);
            // int row1,row2,row3;

            for (int j = 0; j < row1OfKey.length; j++) {
                shiftedPosition += row1OfKey[position] * ALPHABET.indexOf(groupOfThree.charAt(position++));
            }
            plainText += ALPHABET.get(shiftedPosition % ALPHABET.size());

            shiftedPosition = 0;position=0;
            for (int j = 0; j < row2OfKey.length; j++) {
                shiftedPosition += row2OfKey[position] * ALPHABET.indexOf(groupOfThree.charAt(position++));
            }
            plainText += ALPHABET.get(shiftedPosition % ALPHABET.size());

            shiftedPosition = 0;position=0;
            for (int j = 0; j < row3OfKey.length; j++) {
                shiftedPosition += row3OfKey[position] * ALPHABET.indexOf(groupOfThree.charAt(position++));
            }
            plainText += ALPHABET.get(shiftedPosition % ALPHABET.size());

            counter = counter + 3;
        }
        return plainText;

    }

    @Override
    public void restoreFromFile(BufferedReader paramBufferedReader) {

    }

    @Override
    public void saveToFile(BufferedWriter paramBufferedWriter) {

    }
}
