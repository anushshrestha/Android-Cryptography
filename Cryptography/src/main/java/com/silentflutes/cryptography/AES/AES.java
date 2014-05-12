package com.silentflutes.cryptography.AES;

import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.silentflutes.cryptography.Ciphers;
import com.silentflutes.cryptography.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by sillentflutes on 5/11/2014.
 */
public class AES extends Ciphers implements View.OnClickListener {

    EditText hexInput;
    Button encrypt, decrypt;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encryptdecrypt);

        hexInput = (EditText) findViewById(R.id.etmsg);
        hexInput.setHint("Hex Input");
        hexInput.setText("0123456789ABCDEF");
        encrypt = (Button) findViewById(R.id.bencrypt);
        decrypt = (Button) findViewById(R.id.bdecrypt);
        tv = (TextView) findViewById(R.id.tvresult);

        encrypt.setOnClickListener(this);
        decrypt.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bencrypt:

                if (hexInput.getText().toString().isEmpty())
                    Toast.makeText(this, "Msg empty.", Toast.LENGTH_SHORT).show();

                tv.setText(encrypt(hexInput.getText().toString().toLowerCase().trim()));
                decrypt.setVisibility(View.VISIBLE);
                decrypt.setOnClickListener(this);
                break;

            case R.id.bdecrypt:
                if (tv.getText().toString().isEmpty())
                    Toast.makeText(this, "Cipher text lost.", Toast.LENGTH_SHORT).show();

                tv.setText(decrypt(tv.getText().toString().toLowerCase().trim()));
                decrypt.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public static SecretKey generateKey(char[]password, byte[] salt) throws Exception{
        int iterations=1000;
        int outputKeyLength=256;

            SecretKeyFactory secretKeyFactory=SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec keySpec =new PBEKeySpec(password,salt,iterations,outputKeyLength);
               byte [] keyBytes = secretKeyFactory.generateSecret(keySpec).getEncoded();

        return new SecretKeySpec(keyBytes,"AES");



    }

    @Override
    public String encrypt(String plainText){
/*
        SecureRandom secureRandom= new SecureRandom();
        int saltLength=8;
        byte[]salt = new byte[saltLength];
        secureRandom.nextBytes(salt);
        SecretKey secretKey=generateKey(password,salt);

        Cipher cipher =Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] initVector = new byte[cipher.getBlockSize()];
        secureRandom.nextBytes(initVector);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initVector);
        cipher.init(Cipher.ENCRYPT_MODE,secretKey,ivParameterSpec);
        byte [] cipherData = cipher.doFinal(plainText.getBytes("UTF-8"));

        return Base64.encodeToString(cipherData,Base64.NO_WRAP|Base64.NO_PADDING)
                +"]"+ Base64.encodeToString(initVector,Base64.NO_WRAP|Base64.NO_PADDING)
                +"]"+ Base64.encodeToString(salt,Base64.NO_WRAP|Base64.NO_PADDING);

  */
        return  null;
    }

    @Override
    public String decrypt(String cipherText)  {
    /*
        String [] parts = encodedData.split("]");
        byte[]cipherData= Base64.decode(parts[0],Base64.DEFAULT);
        byte [] initVector = Base64.decode(parts[1],Base64.DEFAULT);
        byte [] salt =Base64.decode(parts[2],Base64.DEFAULT);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivParameterSpec= new IvParameterSpec(initVector);
        SecretKey secretKey= generateKey(password,salt);
        cipher.init(Cipher.DECRYPT_MODE,secretKey,ivParameterSpec);
        return new String (cipher.doFinal(cipherData),"UTF-8");
    */
    return null;
    }

    @Override
    public void restoreFromFile(BufferedReader paramBufferedReader) {
    }

    @Override
    public void saveToFile(BufferedWriter paramBufferedWriter) {
    }
}
