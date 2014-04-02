package com.silentflutes.cryptography;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.silentflutes.cryptography.Caesar.Caesar;
import com.silentflutes.cryptography.Caesar.Caesarconfig;
import com.silentflutes.cryptography.Monoalphabetic.Monoalphabeticconfigure;
import com.silentflutes.cryptography.Vigenere.Vigenere;
import com.silentflutes.cryptography.Vigenere.Vigenereconfigure;

import java.util.ArrayList;


public class ListOfAlgorithm extends ListActivity {


    int rotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.listofalogrithm);


        ArrayList<String> listOfAlgorithm = new ArrayList<String>();
        listOfAlgorithm.add(0, "Caesar");
        listOfAlgorithm.add(1, "Monoalphabetic ");
        listOfAlgorithm.add(2, "Vigenere ");
        listOfAlgorithm.add(3, "Vernam ");

        listOfAlgorithm.add(4, "Playfair ");

        ArrayAdapter<String> listOfAlgorithmAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listOfAlgorithm);
        setListAdapter(listOfAlgorithmAdapter);


    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
         switch(position){
             case 0:
                 startActivity(new Intent(this, Caesarconfig.class));
                 break;

             case 1:
                 startActivity(new Intent(this, Monoalphabeticconfigure.class));
                 break;

             case 2:
                 startActivity(new Intent(this, Vigenereconfigure.class));
                 break;
         }

    }
}
