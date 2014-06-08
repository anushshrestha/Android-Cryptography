package com.silentflutes.cryptography.Playfair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.silentflutes.cryptography.R;
import com.silentflutes.cryptography.Vigenere.Vigenere;

/**
 * Created by sillentflutes on 4/1/14.
 */
public class Playfairconfigure  extends Activity {

    EditText playfairKey;
    Button playfairDone;


    //get key for playfair and send it to the playfair class

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playfair_key);
       //milan

        playfairKey=(EditText)findViewById(R.id.etplayfairkey);
        playfairDone=(Button)findViewById(R.id.bplayfairdone);

        playfairDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(playfairKey.getText().toString().isEmpty())) {
                    Intent toPlayfair = new Intent();

                    //CODE TO REMOVE THE REPITION IN THE KEY IS MISSING

                    toPlayfair.putExtra("playfair_key", playfairKey.getText().toString().trim());
                    toPlayfair.setClass(Playfairconfigure.this, Playfair.class);
                    startActivity(toPlayfair);

                } else {
                    Toast.makeText(Playfairconfigure.this, "Enter the key", Toast.LENGTH_SHORT).show();
                }


            }
        });



    }

}
