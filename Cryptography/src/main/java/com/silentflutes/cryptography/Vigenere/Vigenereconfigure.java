package com.silentflutes.cryptography.Vigenere;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.silentflutes.cryptography.Caesar.Caesar;
import com.silentflutes.cryptography.R;

/**
 * Created by sillentflutes on 4/1/14.
 */
public class Vigenereconfigure extends Activity{
    EditText vigenereKey;
    Button vignereDone;


    //same idea as monoalphabetic, getting key and passing to vignere class
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vigenere_key);

        vigenereKey=(EditText)findViewById(R.id.etvigkey);
        vignereDone=(Button)findViewById(R.id.bvigdone);

        vignereDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(vigenereKey.getText().toString().isEmpty())){
                    Intent toVignere = new Intent();
                    toVignere.putExtra("vigkey",vigenereKey.getText().toString().trim() );
                    toVignere.setClass(Vigenereconfigure.this, Vigenere.class);
                    startActivity(toVignere);

                }else{
                    Toast.makeText(Vigenereconfigure.this, "Enter the key", Toast.LENGTH_SHORT).show();

                }


            }
        });



    }
}
