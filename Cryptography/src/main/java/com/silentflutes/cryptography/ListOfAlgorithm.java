package com.silentflutes.cryptography;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.silentflutes.cryptography.Caesar.Caesarconfigure;
import com.silentflutes.cryptography.DES.DES;
import com.silentflutes.cryptography.DES.DESconfigure;
import com.silentflutes.cryptography.Hill.Hillconfigure;
import com.silentflutes.cryptography.Monoalphabetic.Monoalphabeticconfigure;
import com.silentflutes.cryptography.Playfair.Playfairconfigure;
import com.silentflutes.cryptography.Vernam.Vernam;
import com.silentflutes.cryptography.Vernam.Vernamconfigure;
import com.silentflutes.cryptography.Vigenere.Vigenereconfigure;

import java.util.ArrayList;


public class ListOfAlgorithm extends ListActivity {

    //simply display all the algorithm implemented as a list
    // after all algorithm are implemented then, we will use fragment instead of list.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.listofalogrithm);

        //create a array list of string and add name of algorithm in specific position
        ArrayList<String> listOfAlgorithm = new ArrayList<String>();
        listOfAlgorithm.add(0, "Caesar");
        listOfAlgorithm.add(1, "Monoalphabetic ");
        listOfAlgorithm.add(2, "Vigenere ");
        listOfAlgorithm.add(3, "Playfair ");
        listOfAlgorithm.add(4, "Hill");
        listOfAlgorithm.add(5, "Vernam/One Time Pad ");
        listOfAlgorithm.add(6, "DES");

        //create a array adapter of type string that will bind array list created above and the encryptdecrypt of row of array list
        //here default encryptdecrypt for row is used.
        ArrayAdapter<String> listOfAlgorithmAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listOfAlgorithm);
        //bind the adapter to the entire list encryptdecrypt
        //this class is extending list activity so, it automatically finds view for the list
        setListAdapter(listOfAlgorithmAdapter);


    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
         switch(position){

             //use the series in with array list of string is created in onCreate ie listofAlgorithm
             case 0:
                 startActivity(new Intent(this, Caesarconfigure.class));
                    //goes to config class that takes all the require key for that algorithm
                 break;

             case 1:
                 startActivity(new Intent(this, Monoalphabeticconfigure.class));

                 break;

             case 2:
                 startActivity(new Intent(this, Vigenereconfigure.class));

                 break;

             case 3:
                 startActivity(new Intent(this, Playfairconfigure.class));

                 break;

             case 4:
                 startActivity(new Intent(this, Hillconfigure.class));

                 break;

             case 5:
                 startActivity(new Intent(this, Vernamconfigure.class));

                 break;

             case 6:
                 startActivity(new Intent(this, DES.class));

                 break;
         }

    }
}
