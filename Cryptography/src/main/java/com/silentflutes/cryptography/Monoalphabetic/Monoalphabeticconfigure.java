package com.silentflutes.cryptography.Monoalphabetic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.silentflutes.cryptography.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by sillentflutes on 4/1/14.
 */
public class Monoalphabeticconfigure extends Activity implements View.OnClickListener {

    public static final List<Character> ALPHABET;
    static {
        Character[] arrayOfCharacter1 = new Character[26];
        arrayOfCharacter1[0] = Character.valueOf('a');
        arrayOfCharacter1[1] = Character.valueOf('b');
        arrayOfCharacter1[2] = Character.valueOf('c');
        arrayOfCharacter1[3] = Character.valueOf('d');
        arrayOfCharacter1[4] = Character.valueOf('e');
        arrayOfCharacter1[5] = Character.valueOf('f');
        arrayOfCharacter1[6] = Character.valueOf('g');
        arrayOfCharacter1[7] = Character.valueOf('h');
        arrayOfCharacter1[8] = Character.valueOf('i');
        arrayOfCharacter1[9] = Character.valueOf('j');
        arrayOfCharacter1[10] = Character.valueOf('k');
        arrayOfCharacter1[11] = Character.valueOf('l');
        arrayOfCharacter1[12] = Character.valueOf('m');
        arrayOfCharacter1[13] = Character.valueOf('n');
        arrayOfCharacter1[14] = Character.valueOf('o');
        arrayOfCharacter1[15] = Character.valueOf('p');
        arrayOfCharacter1[16] = Character.valueOf('q');
        arrayOfCharacter1[17] = Character.valueOf('r');
        arrayOfCharacter1[18] = Character.valueOf('s');
        arrayOfCharacter1[19] = Character.valueOf('t');
        arrayOfCharacter1[20] = Character.valueOf('u');
        arrayOfCharacter1[21] = Character.valueOf('v');
        arrayOfCharacter1[22] = Character.valueOf('w');
        arrayOfCharacter1[23] = Character.valueOf('x');
        arrayOfCharacter1[24] = Character.valueOf('y');
        arrayOfCharacter1[25] = Character.valueOf('z');
        ALPHABET = Arrays.asList(arrayOfCharacter1);
    }
    Button bmonogenerate,bmonodone;
    TextView tvmonokey;


    public String randomGenerator() {
        String randomString = "";
        ArrayList localArrayList = new ArrayList(ALPHABET);

        Random localRandom = new Random();//generate random time based on time of day
        while (true) {
            if (localArrayList.isEmpty())
                break;
            int i = localRandom.nextInt() % localArrayList.size();
            if (i < 0) i = -i;
            randomString += ((Character) localArrayList.remove(i)).charValue();
        }
        return randomString;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monoalphabet_key);

        bmonogenerate = (Button) findViewById(R.id.bmonogenerate);
        tvmonokey=(TextView)findViewById(R.id.tvmonokey);
        bmonodone =(Button)findViewById(R.id.bmonodone);

        bmonogenerate.setOnClickListener(this);
        bmonodone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String randomKey=randomGenerator();
        switch(v.getId()){

            case R.id.bmonogenerate:

                tvmonokey.setText(randomKey);
                break;


            case R.id.bmonodone:
                Intent toMono = new Intent();
                toMono.putExtra("monokey", randomKey);
                toMono.setClass(Monoalphabeticconfigure.this, Monoalphabetic.class);
                startActivity(toMono);
                break;

        }
    }
}
