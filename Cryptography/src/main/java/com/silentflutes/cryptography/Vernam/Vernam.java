package com.silentflutes.cryptography.Vernam;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    int MAX = 50;
    EditText plainText;
    TextView displayRandomKey, tvResult,tvCompleteKey;
    Button btnRandomize, btnEncrypt, btnDecrypt;
    Button btnCompleteKey;
    String binaryKey="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vernam);

        plainText = (EditText) findViewById(R.id.etPlainText);
        btnRandomize = (Button) findViewById(R.id.btnRandomValues);
        displayRandomKey = (TextView) findViewById(R.id.tvRandomValues);
        btnEncrypt = (Button) findViewById(R.id.btnEncrypt);
        btnDecrypt = (Button) findViewById(R.id.btnDecrypt);
        tvResult = (TextView) findViewById(R.id.tvResult);
        btnCompleteKey = (Button) findViewById(R.id.btnCompleteKey);
        tvCompleteKey=(TextView) findViewById(R.id.tvCompleteKey);



        btnRandomize.setOnClickListener(this);
        btnCompleteKey.setOnClickListener(this);
        btnEncrypt.setOnClickListener(this);
        btnDecrypt.setOnClickListener(this);
    }

    public static void shuffle(StringBuilder sb) {
        Random RANDOM = new Random();
        for (int i = 25; i > 1; i--) {
            int swapWith = RANDOM.nextInt(i);
            char temp = sb.charAt(swapWith);
            sb.setCharAt(swapWith, sb.charAt(i));
            sb.setCharAt(i, temp);
        }
    }

    public void completeKey(String randomKey, String plainText) {
        int len_r, len_p;
        String completeKey = "";

        len_r = randomKey.length();
        len_p = plainText.length();

        for (int i = 0; i < len_p; i++) {
            completeKey += randomKey.charAt(i % len_r);

        }
        tvCompleteKey.setText(completeKey);
    }



/*
    public static byte[] stringToBytesASCII(String str) {
        char[] buffer = str.toCharArray();
        byte[] b = new byte[buffer.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) buffer[i];
        }
        return b;
    }
*/

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

/*
        for (int i = 0; i < temp.length()- 7; i += 7) {
            String temp1=temp.substring(i,i+7);
            result += (char) Integer.parseInt(temp.substring(i, i + 7), 2);
        }

/*
        String[] arrayOfString = temp.split(temp,8);

        StringBuilder result = new StringBuilder();
        for ( int i = 0; i < temp.length()/7; i++ ) {
            result+=( (char)Integer.parseInt( arrayOfString[i], 2 ) );
        }*/

        /*
        int charCode = Integer.parseInt(temp, 2);
        String str = new Character((char) charCode).toString();
*/



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
        //BigInteger(s, 16).toString(2);
        for(int i=0;i<l;i++){
             binary += Integer.toBinaryString(arrayOfChar[i]);
        }

        return binary;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRandomValues:
                StringBuilder sb = new StringBuilder("abcdefghijklmnopqrstuvwxyz");
                shuffle(sb);
                displayRandomKey.setText(sb);
                break;


            case R.id.btnCompleteKey:
                completeKey(displayRandomKey.getText().toString().trim(),plainText.getText().toString().trim());
                break;

            case R.id.btnEncrypt:

                String key = tvCompleteKey.getText().toString().trim();
                char[] arrayOfKey = key.toUpperCase().toCharArray();
                char[] arrayOfPlainText = plainText.getText().toString().trim().toUpperCase().toCharArray();
                int l = plainText.length();
                binaryKey=toBinary(l,arrayOfKey);
                String binaryPlainText=toBinary(l,arrayOfPlainText);
                String xoredString = "";
                 //String cipherText="";
                xoredString= xorOperation(binaryKey, binaryPlainText);
                tvResult.setText(xoredString);

                //cipherText=binaryToChar(xoredString);
               // int charCode = Integer.parseInt(cipherText, 2);
                //String str = new Character((char)charCode).toString();

//                 tvResult.setText(cipherText);


                //tv.setText(encrypt(hexInput.getText().toString().toLowerCase().trim()));

/*
                //String message=plainText.getText().toString().trim();
                //byte[] messageByte=stringToBytesASCII(message);

//                char[] arrayOfKey=displayRandomKey.getText().toString().trim().toUpperCase().toCharArray();
  //              char[] arrayOfPlainText=plainText.getText().toString().trim().toUpperCase().toCharArray();


                //tv.setText(encrypt(hexInput.getText().toString().toLowerCase().trim()));
                //String binaryinput=plainText.getText().toString();


               String binaryPlaintext=stringToBinary(l,arrayOfPlainText);
                String binaryKey=stringToBinary(l,arrayOfKey);
                String cipherText = "";
                int l = plainText.length();
                String key=tvCompleteKey.getText().toString().trim();
                String message=plainText.getText().toString().trim();

                cipherText=xorOperation(l,key,message);
                tvResult.setText(cipherText);
                cipherText = "";
                key="";
*/
                break;

            case R.id.btnDecrypt:
                //key = tvCompleteKey.getText().toString().trim();
                //char[] arrayOfkey = tvCompleteKey.getText().toString().trim().toUpperCase().toCharArray();
                //char[] arrayOfCipherText = tvResult.getText().toString().trim().toCharArray();
                //int m = tvResult.length();
                //String binarykey=toBinary(m,arrayOfkey);
                //String binaryCipherText=toBinary(m,arrayOfCipherText);
                //String xoredDecryptString= xorOperation(binarykey, binaryCipherText);
                String cipherText=tvResult.getText().toString().trim();
                String xoredDecryptString= xorOperation(binaryKey,cipherText);
                tvResult.setText(binaryToChar(xoredDecryptString));

//                tvResult.setText(decryptedText);
/*
//                String de_string = tvResult.getText().toString().trim();
//                String de_key = displayRandomKey.getText().toString().trim();
                ///char[] arrayOfCipherText=tvResult.getText().toString().trim().toCharArray();
                //char[] arrayOfKey=displayRandomKey.getText().toString().trim().toCharArray();

                cipherText = "";
                key="";

                key=tvCompleteKey.getText().toString().trim();
                cipherText = tvResult.getText().toString().trim();
                l = tvResult.getText().length();
                String decryptedText="";
                decryptedText=xorOperation(l,key,cipherText);
               // decryptedText=xorOperation(m,arrayOfCipherText,key.toCharArray());
                tvResult.setText("");
                tvResult.setText(decryptedText);
                btnDecrypt.setVisibility(View.INVISIBLE);
  */
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

