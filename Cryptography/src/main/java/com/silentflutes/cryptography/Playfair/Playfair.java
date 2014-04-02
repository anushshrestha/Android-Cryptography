package com.silentflutes.cryptography.Playfair;

import com.silentflutes.cryptography.Ciphers;
import com.silentflutes.cryptography.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;


/**
 * Created by sillentflutes on 4/1/14.
 */
public class Playfair extends Ciphers implements View.OnClickListener{

    private String m_key = "";
    private char[][] m_keyMatrix;
    EditText msg;
    TextView tv;
    Button encrypt, decrypt;
    private ArrayList<Integer> noOfJ = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view);
        m_key = getIntent().getExtras().getString("playfair_key");

        msg = (EditText) findViewById(R.id.etmsg);
        tv = (TextView) findViewById(R.id.tvresult);

        encrypt = (Button) findViewById(R.id.bencrypt);
        decrypt = (Button) findViewById(R.id.bdecrypt);

        decrypt.setVisibility(View.INVISIBLE);
        encrypt.setOnClickListener(this);
    }

    public char[][] get_KeyMatrix() {
        return m_keyMatrix;
    }

    private int[] getRowCol(Character paramCharacter) {
        int[] arrayOfInt = {0, 0};
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (m_keyMatrix[i][j] != Character.toLowerCase
                        (paramCharacter.charValue()))
                    continue;
                arrayOfInt[0] = i;//row
                arrayOfInt[1] = j;//
                return arrayOfInt;
            }
        }
        return null;
    }

    public void savePositionOfJ(String msg) {

        int base_index = msg.indexOf("j");
        if(base_index==-1)
            noOfJ.add(0);
//if j does not exist then 0 at the first location
        else
            noOfJ.add(Integer.valueOf(base_index));
        while (base_index >= 0) {
            base_index = base_index + 1;
            int index = Integer.valueOf(msg.indexOf("j", base_index));
            if (index >= 0)
                noOfJ.add(index);
            base_index = index;
//base_index=index;
        }

    }

    public String restoreJ(String plainText) {

        char[] arrayOfPlainText = plainText.toCharArray();
        if(noOfJ.get(0)==0)
//if no j is added then the list is empty
            return null ;
        else {
//if the no of j is added in the list
// String restoredJPlainText = "";
            for (int i = 0; i < noOfJ.size(); i++) {
                Integer positionOfJ = noOfJ.get(Integer.valueOf(i));
                arrayOfPlainText[positionOfJ] = 'j';

            }
            plainText = String.valueOf(arrayOfPlainText);
// for(int k=0;k<=plainText.length();k++){
// restoredJPlainText=restoredJPlainText+arrayOfPlainText[k];
// }
            return plainText.trim();
        }
    }

    private String getEncryptionSubstitution(Character char1, Character char2, int paramInt) {

        char[] arrayOfChar = new char[2];
        int[] arrayOfInt2;
        if (char2 == '}' || char2 == '{') {
            arrayOfInt2 = getRowCol('x');

//use of x
        } else {
            arrayOfInt2 = getRowCol(char2);//row and column of char2
        }

// new int[2];
//new int[2];
        int[] arrayOfInt1 = getRowCol(char1);//row and column of char1

        if (arrayOfInt1[0] == arrayOfInt2[0]) {
//row same
            int i3 = arrayOfInt1[0];//row remains same
//change column for char1
            int i4 = (paramInt + (5 + arrayOfInt1[1])) % 5;
//get char of new position
            arrayOfChar[0] = m_keyMatrix[i3][i4];
//change column for char2
            int i5 = (paramInt + (5 + arrayOfInt2[1])) % 5;
//get column of new position
            arrayOfChar[1] = this.m_keyMatrix[i3][i5];
        }
//same column
        else if (arrayOfInt1[1] == arrayOfInt2[1]) {
//same column
//change row of char1
            int n = (paramInt + (5 + arrayOfInt1[0])) % 5;
            int i1 = arrayOfInt1[1] = arrayOfInt2[1];
            arrayOfChar[0] = this.m_keyMatrix[n][i1];

//change row for char2
            int i2 = (paramInt + (5 + arrayOfInt2[0])) % 5;
            arrayOfChar[1] = this.m_keyMatrix[i2][i1];

        } else {
//if no same row or column
            int i = arrayOfInt1[0];//row of first
            int j = arrayOfInt2[1];//column of second
            arrayOfChar[0] = this.m_keyMatrix[i][j];

            int k = arrayOfInt2[0];
            int m = arrayOfInt1[1];
            arrayOfChar[1] = this.m_keyMatrix[k][m];
        }

        if ((Character.isUpperCase(char1.charValue())))
            arrayOfChar[0] = Character.toLowerCase(arrayOfChar[0]);
        if ((Character.isUpperCase(char2.charValue())) && char2 != '{' && char2 != '}')
            arrayOfChar[1] = Character.toUpperCase(arrayOfChar[1]);
        String afterSubstiution = "";
        for (int z = 0; z < 2; z++) {
//for decryption only

            afterSubstiution += arrayOfChar[z];
        }

        return afterSubstiution.trim();

    }

    private String getDecryptionSubstitution(Character char1, Character char2, int paramInt) {

        char[] arrayOfChar = new char[2];
        int[] arrayOfInt2;
        if (char2 == '}' || char2 == '{') {
            arrayOfInt2 = getRowCol('x');

//use of x
        } else {
            arrayOfInt2 = getRowCol(char2);//row and column of char2
        }
// new int[2];
//new int[2];
        int[] arrayOfInt1 = getRowCol(char1);//row and column of char1

        if (arrayOfInt1[0] == arrayOfInt2[0]) {
//row same
            int i3 = arrayOfInt1[0];//row remains same
//change column for char1
            int i4 = ((5 + arrayOfInt1[1]) - paramInt) % 5;
//get char of new position
            arrayOfChar[0] = m_keyMatrix[i3][i4];
//change column for char2
            int i5 = ((5 + arrayOfInt2[1]) - paramInt) % 5;
//get column of new position
            arrayOfChar[1] = this.m_keyMatrix[i3][i5];
        }
//same column
        else if (arrayOfInt1[1] == arrayOfInt2[1]) {
//same column
//change row of char1
            int n = ((5 + arrayOfInt1[0]) - paramInt) % 5;
            int i1 = arrayOfInt1[1] = arrayOfInt2[1];
            arrayOfChar[0] = this.m_keyMatrix[n][i1];

//change row for char2
            int i2 = ((5 + arrayOfInt2[0]) - paramInt) % 5;
            arrayOfChar[1] = this.m_keyMatrix[i2][i1];

        } else {
//if no same row or column
            int i = arrayOfInt1[0];//row of first
            int j = arrayOfInt2[1];//column of second
            arrayOfChar[0] = this.m_keyMatrix[i][j];

            int k = arrayOfInt2[0];
            int m = arrayOfInt1[1];
            arrayOfChar[1] = this.m_keyMatrix[k][m];
        }

        if ((Character.isUpperCase(char1.charValue())))
            arrayOfChar[0] = Character.toLowerCase(arrayOfChar[0]);
        if ((Character.isUpperCase(char2.charValue())) && char2 != '{' && char2 != '}')
            arrayOfChar[1] = Character.toUpperCase(arrayOfChar[1]);
        String afterSubstiution = "";
        for (int z = 0; z < 2; z++) {
//for decryption only

            afterSubstiution += arrayOfChar[z];
        }

        return afterSubstiution.trim();

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
                String cipher=tv.getText().toString().trim();
                String plainText = decrypt(cipher);
                tv.setText(plainText);
                break;
        }
    }

/* public String addPadding(String msg) {
if (((msg.length()) % 2) != 0)
msg += '}';

return msg;
}*/

    public String removePadding(String msg) {
        int size = msg.length();
/*
String withoutPadding = "";
int counter = 0;
for (int i = 0; i < size / 2; i++) {
String groupOfTwo = msg.substring(counter, counter + 2);
char[] arrayOfTwo = groupOfTwo.toCharArray();
if (arrayOfTwo[1] == '}')
withoutPadding = withoutPadding + arrayOfTwo[0];
else
withoutPadding = withoutPadding + groupOfTwo;
}
*/
        if (size % 2 != 0)
            return msg + "}";
        else
            return msg;
    }

    public String addFillers(String plainText) {

        String original = plainText.trim();
        char[] arrayOfOriginal = original.toCharArray();
        String withFiller = "";
        int size = msg.length();
//int virtual_size=size;
        int counter = 0;

        for (int i = 0; i < size; i++) {
            if (counter >= size)
                break;
            if ((size - counter) == 1) {
//add last character also
                withFiller = withFiller + arrayOfOriginal[counter] + '}';
                break;
            }
            String groupOfTwo = original.substring(counter, counter + 2);
            char[] arrayOfTwo = groupOfTwo.toCharArray();
            if (arrayOfTwo[0] == arrayOfTwo[1]) {
                withFiller = withFiller + arrayOfOriginal[counter] + '{';
                counter++;
//size++;
//withFiller = withFiller + arrayOfOriginal[counter + 2] + '{';
            } else {
                withFiller = withFiller + groupOfTwo;
                counter = counter + 2;
            }

        }

        return withFiller;
    }

    public String removeFillers(String msg) {
        int size = msg.length();
        char []charOfMsg=msg.toCharArray();

        String withoutFiller = "";
        int counter = 0;
        for (int i = 0; i < size/2; i++) {
            String groupOfTwo = msg.substring(counter, counter + 2);
            char[] arrayOfTwo = groupOfTwo.toCharArray();
//2nd must be x to be removed

            if (arrayOfTwo[1] == 'x') {
                if ((size - counter) == 2) {
                    withoutFiller = withoutFiller + arrayOfTwo[0];
                } else {
//after ward character exists
                    int afterPosition=counter+2;
                    Character c =charOfMsg[afterPosition];
                    if (arrayOfTwo[0] == c)
//2nd and two side same, take
                        withoutFiller = withoutFiller + arrayOfTwo[0];

                }
                counter=counter+2;
            } else {
                withoutFiller = withoutFiller + groupOfTwo;
                counter = counter + 2;
            }
        }
        return withoutFiller;
    }

    public String random(ArrayList matrixFiller) {
        String str = "";
        ArrayList matrixFillerArrayList = new ArrayList(matrixFiller);
        Random localRandom = new Random();//generate random time based on time of day
        while (true) {
            if (matrixFillerArrayList.isEmpty()) {
                return str;
            }
            int i = localRandom.nextInt() % matrixFillerArrayList.size();
            if (i < 0) i = -i;
// Character c
            str = str + ((Character) matrixFillerArrayList.remove(i)).charValue();
//if(c!='j')
// +c;
//skips j.
        }
    }

    public String removeFromAlphabet(String key) {
        ArrayList allArray = new ArrayList(ALPHABET);
        char[] charOfKey = key.toCharArray();
        String str = "";
        int i = 0;
        for (i = 0; i < key.length(); i++) {
            if (allArray.contains(Character.valueOf(charOfKey[i])))
                allArray.remove(allArray.indexOf(charOfKey[i]));
        }
//remove j...
        allArray.remove(allArray.indexOf('j'));
        for (i = 0; i < allArray.size(); i++) {
            str = str + allArray.get(i);
        }
        return str;
    }

    public void createAndFillKeyMatrix(String key) {
//creating key matrix
        int[] arrayOfInt = {5, 5};
        m_keyMatrix = (char[][]) Array.newInstance(Character.TYPE, arrayOfInt);

//Iterator localIterator;
        char[] charOfKey = key.toCharArray();
//finding filler ie filler = alphabet-key
        String fillers = removeFromAlphabet(key);
        ArrayList matrixFillers = new ArrayList();
//creating array list of filler
        char[] charOfFillers = fillers.toCharArray();
        for (int i = 0; i < fillers.length(); i++) {
            matrixFillers.add(charOfFillers[i]);
//adding char of fillers to array of filler
        }
//now randomize the filler array
        String randomizedFillers = random(matrixFillers);
        char[] charOfrandomizedFillers = randomizedFillers.toCharArray();
//char of randomized filler
        int noOfRandomizedFiller = 0;
        int noOfKeyadded = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (noOfKeyadded < key.length()) {
                    m_keyMatrix[i][j] = charOfKey[noOfKeyadded];
                    System.out.println(m_keyMatrix[i][j]);
                    noOfKeyadded++;
                } else {
                    m_keyMatrix[i][j] = charOfrandomizedFillers[noOfRandomizedFiller];
                    System.out.println(m_keyMatrix[i][j]);
                    noOfRandomizedFiller++;
                }
            }
        }
        return;
    }




    @Override
    public String encrypt(String plainText) {
// if (m_keyMatrix == null)
// throw new Exception("There was an error. Key was null.");
        String cipherText = "";
        savePositionOfJ(plainText);
        plainText = plainText.replace('j', 'i');//always for u
        createAndFillKeyMatrix(m_key);//key matrix ready

        String paddedAndFilled = addFillers(plainText);
        int size = paddedAndFilled.length();
        int counter = 0;
        for (int i = 0; i < (paddedAndFilled.length()) / 2; i++) {
            String groupOfTwo = paddedAndFilled.substring(counter, counter + 2);
            char[] arrayOfTwo = groupOfTwo.toCharArray();
            String partOfCipherText = getEncryptionSubstitution(arrayOfTwo[0], arrayOfTwo[1], 1);
            cipherText = cipherText + partOfCipherText;
            counter = counter + 2;
        }
        return cipherText;
//first replace j with i as a substitution character
//


    }

    @Override
    public String decrypt(String cipherText) {
        // if (m_keyMatrix == null)
// throw new Exception("There was an error. Key was null. " +
// "Please delete the cipher text and try again.");
        String plainText = "";
// StringParser local

        int size = cipherText.length();
        int counter = 0;
        for (int i = 0; i < size / 2; i++) {
            String groupOfTwo = cipherText.substring(counter, counter + 2);
            char[] arrayOfTwo = groupOfTwo.toCharArray();
            String partOfPlainText = getDecryptionSubstitution(arrayOfTwo[0], arrayOfTwo[1], 1);
            plainText = plainText + partOfPlainText;//with x
            counter = counter + 2;
        }
// System.out.print(plainText);
        plainText = removeFillers(plainText);//no need to remove padders
// plainText = plainText.replace('i', 'j');
        if (restoreJ(plainText) != null)
            plainText = restoreJ(plainText);
        return plainText;


    }

    @Override
    public void restoreFromFile(BufferedReader paramBufferedReader) {

    }

    @Override
    public void saveToFile(BufferedWriter paramBufferedWriter) {

    }
}
