package com.silentflutes.cryptography.Caesar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.silentflutes.cryptography.R;

import java.util.ArrayList;

/**
 * Created by sillentflutes on 3/29/14.
 */
public class Caesarconfigure extends Activity {

    Button done;
    private Spinner m_rotateBy;

    //get shift value...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caesar_rotate);

        //create view with spinner

        m_rotateBy = (Spinner) findViewById(R.id.spincaesar);
        done = (Button) findViewById(R.id.bspinnerdone);

        //create array list  of number and add all possible shift values 1-26

        ArrayList arrayOfSpinnerNumber = new ArrayList();
        for (int i = 0; i < 26; i++)
            arrayOfSpinnerNumber.add(i, i+1);

        //adapter to bind spinner view and array list

        ArrayAdapter listOfRoatate = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayOfSpinnerNumber);

        //setting adapter to spinner
        m_rotateBy.setAdapter(listOfRoatate);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when done is cliked the rotate value is send to caesar class.
                int rotate = m_rotateBy.getSelectedItemPosition();
                Intent toCaesar = new Intent();
                toCaesar.putExtra("rotation", rotate + 1);
                toCaesar.setClass(Caesarconfigure.this, Caesar.class);
                startActivity(toCaesar);

            }
        });


    }
}
