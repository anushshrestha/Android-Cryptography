package com.silentflutes.cryptography.DES;

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
 * Created by sillentflutes on 4/3/14.
 */
public class DES extends Ciphers implements View.OnClickListener {

    //values
    //initial permutation
    static int[] initialPermutation =
            {58, 50, 42, 34, 26, 18, 10, 2,
                    60, 52, 44, 36, 28, 20, 12, 4,
                    62, 54, 46, 38, 30, 22, 14, 6,
                    64, 56, 48, 40, 32, 24, 16, 8,
                    57, 49, 41, 33, 25, 17, 9, 1,
                    59, 51, 43, 35, 27, 19, 11, 3,
                    61, 53, 45, 37, 29, 21, 13, 5,
                    63, 55, 47, 39, 31, 23, 15, 7
            };
//final permuatation after round function and swap
    private static final int[] inverseInitialPermutation = {
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25
    };

    //expand left(32 bit to 48)
    static int[] expand32to48 =
            {
                    32, 1, 2, 3, 4, 5,
                    4, 5, 6, 7, 8, 9,
                    8, 9, 10, 11, 12, 13,
                    12, 13, 14, 15, 16, 17,
                    16, 17, 18, 19, 20, 21,
                    20, 21, 22, 23, 24, 25,
                    24, 25, 26, 27, 28, 29,
                    28, 29, 30, 31, 32, 1
            };

    private static final int[][]sBoxArray  = { {
            14, 4,  13, 1,  2,  15, 11, 8,  3,  10, 6,  12, 5,  9,  0,  7,
            0,  15, 7,  4,  14, 2,  13, 1,  10, 6,  12, 11, 9,  5,  3,  8,
            4,  1,  14, 8,  13, 6,  2,  11, 15, 12, 9,  7,  3,  10, 5,  0,
            15, 12, 8,  2,  4,  9,  1,  7,  5,  11, 3,  14, 10, 0,  6,  13
    }, {
            15, 1,  8,  14, 6,  11, 3,  4,  9,  7,  2,  13, 12, 0,  5,  10,
            3,  13, 4,  7,  15, 2,  8,  14, 12, 0,  1,  10, 6,  9,  11, 5,
            0,  14, 7,  11, 10, 4,  13, 1,  5,  8,  12, 6,  9,  3,  2,  15,
            13, 8,  10, 1,  3,  15, 4,  2,  11, 6,  7,  12, 0,  5,  14, 9
    }, {
            10, 0,  9,  14, 6,  3,  15, 5,  1,  13, 12, 7,  11, 4,  2,  8,
            13, 7,  0,  9,  3,  4,  6,  10, 2,  8,  5,  14, 12, 11, 15, 1,
            13, 6,  4,  9,  8,  15, 3,  0,  11, 1,  2,  12, 5,  10, 14, 7,
            1,  10, 13, 0,  6,  9,  8,  7,  4,  15, 14, 3,  11, 5,  2,  12
    }, {
            7,  13, 14, 3,  0,  6,  9,  10, 1,  2,  8,  5,  11, 12, 4,  15,
            13, 8,  11, 5,  6,  15, 0,  3,  4,  7,  2,  12, 1,  10, 14, 9,
            10, 6,  9,  0,  12, 11, 7,  13, 15, 1,  3,  14, 5,  2,  8,  4,
            3,  15, 0,  6,  10, 1,  13, 8,  9,  4,  5,  11, 12, 7,  2,  14
    }, {
            2,  12, 4,  1,  7,  10, 11, 6,  8,  5,  3,  15, 13, 0,  14, 9,
            14, 11, 2,  12, 4,  7,  13, 1,  5,  0,  15, 10, 3,  9,  8,  6,
            4,  2,  1,  11, 10, 13, 7,  8,  15, 9,  12, 5,  6,  3,  0,  14,
            11, 8,  12, 7,  1,  14, 2,  13, 6,  15, 0,  9,  10, 4,  5,  3
    }, {
            12, 1,  10, 15, 9,  2,  6,  8,  0,  13, 3,  4,  14, 7,  5,  11,
            10, 15, 4,  2,  7,  12, 9,  5,  6,  1,  13, 14, 0,  11, 3,  8,
            9,  14, 15, 5,  2,  8,  12, 3,  7,  0,  4,  10, 1,  13, 11, 6,
            4,  3,  2,  12, 9,  5,  15, 10, 11, 14, 1,  7,  6,  0,  8,  13
    }, {
            4,  11, 2,  14, 15, 0,  8,  13, 3,  12, 9,  7,  5,  10, 6,  1,
            13, 0,  11, 7,  4,  9,  1,  10, 14, 3,  5,  12, 2,  15, 8,  6,
            1,  4,  11, 13, 12, 3,  7,  14, 10, 15, 6,  8,  0,  5,  9,  2,
            6,  11, 13, 8,  1,  4,  10, 7,  9,  5,  0,  15, 14, 2,  3,  12
    }, {
            13, 2,  8,  4,  6,  15, 11, 1,  10, 9,  3,  14, 5,  0,  12, 7,
            1,  15, 13, 8,  10, 3,  7,  4,  12, 5,  6,  11, 0,  14, 9,  2,
            7,  11, 4,  1,  9,  12, 14, 2,  0,  6,  10, 13, 15, 3,  5,  8,
            2,  1,  14, 7,  4,  10, 8,  13, 15, 12, 9,  0,  3,  5,  6,  11
    } };

     static final int[] afterSboxPermutation = {
            16, 7,  20, 21,
            29, 12, 28, 17,
            1,  15, 23, 26,
            5,  18, 31, 10,
            2,  8,  24, 14,
            32, 27, 3,  9,
            19, 13, 30, 6,
            22, 11, 4,  25
    };


    //permutation to give f (for XOR operation with left)
    static int[] P = {
            16, 7, 20, 21,
            29, 12, 28, 17,
            1, 15, 23, 26,
            5, 18, 31, 10,
            2, 8, 24, 14,
            32, 27, 3, 9,
            19, 13, 30, 6,
            22, 11, 4, 25
    };



    static String[] hexValues =
            {
                    "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111",
                    "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"
            };


    EditText hexInput;
    Button encrypt, decrypt;
    TextView tv;
    ArrayList<String> listOfKey = new ArrayList<String>();




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

    //conversion from HEX input to binary
    public static String HexToBinary(String Hex) {
        char[] arrayOfChar = Hex.toCharArray();
        byte[] arrayOfByte= Hex.getBytes();
        String binaryInput = "";
        for (int i = 0; i < Hex.length(); i++) {
            binaryInput += hexValues[Integer.parseInt(Character.toString(arrayOfChar[i]), 16)];
        }
        return binaryInput;
    }

    //input the 64bit binary input and return the permuted values
    public int [] initialPermutation(String inputBeforeIp) {
        //initial Permutation array is accessed by default.
        //String afterIpInput = "";
        int[] afterIpInput = new int[64];
        char[] beforeIp = inputBeforeIp.toCharArray();
        //byte[] per_plainText = new byte[8];
        for (int i = 0; i < 64; i++) {
            //x=IP[i]-1;
           afterIpInput[i] = Integer.parseInt(Character.toString(beforeIp[initialPermutation[i] - 1]));//, 10);
            //afterIpInput += Character.toString(beforeIp[initialPermutation[i] - 1]);

        }
        return afterIpInput;
    }


    //milan

    // 64 bit key ...use p1 for key  // function and make it into 56..
    // break into 28
    // shift ...left 1 or 2 …for 16 key
    // compress from 56 to 48 using p2
    // 48 key is send to round function

    // For key processing
    public ArrayList keyGeneration()// k for initial key of 64 bbit
    {
        int key[] = new int[64], i;
        int k[] = {0, 0, 0, 1, 0, 0, 1, 1,
                0, 0, 1, 1, 0, 1, 0, 0,
                0, 1, 0, 1, 0, 1, 1, 1,
                0, 1, 1, 1, 1, 0, 0, 1,
                1, 0, 0, 1, 1, 0, 1, 1,
                1, 0, 1, 1, 1, 1, 0, 0,
                1, 1, 0, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 0, 0, 0, 1};
        int pc1[] = {57, 49, 41, 33, 25, 17, 9, 1,
                58, 50, 42, 34, 26, 18, 10, 2,
                59, 51, 43, 35, 27, 19, 11, 3,
                60, 52, 44, 36, 63, 55, 47, 39,
                31, 23, 15, 7, 62, 54, 46, 38,
                30, 22, 14, 6, 61, 53, 45, 37,
                29, 21, 13, 5, 28, 20, 12, 4};

        for (i = 0; i < 56; i++)
            key[i] = k[pc1[i] - 1];

        int c[] = new int[64], d[] = new int[64];
        int i1 = 0;
        for (i = 0; i < 56; i++) {
            if (i < 28)
                c[i] = key[i];
            else {
                d[i1] = key[i];
                i1++;
            }
        }

        //After dividing key into two blocks

// for shifting
        int round;
        int key1[] = new int[64];

        for (round = 1; round <= 16; round++) {
            // round 1,2,9,16-1 bit shift; other-2 bits shift
            String keys = "";
            if (round == 1 || round == 2 || round == 9 || round == 16) {
                int temp1 = c[0], temp2 = d[0];
                for (i = 0; i < 28; i++) {
                    c[i] = c[i + 1];
                    d[i] = d[i + 1];
                    if (i == 27) {
                        c[i] = temp1;
                        d[i] = temp2;
                    }
                }
            } else {
                for (int kl = 1; kl <= 2; kl++) {
                    int temp1 = c[0], temp2 = d[0];
                    for (i = 0; i < 28; i++) {
                        c[i] = c[i + 1];
                        d[i] = d[i + 1];
                        if (i == 27) {
                            c[i] = temp1;
                            d[i] = temp2;
                        }
                    }
                }
            }

            int pc2[] = {14, 17, 11, 24, 1, 5, 3, 28,
                    15, 6, 21, 10, 23, 19, 12, 4,
                    26, 8, 16, 7, 27, 20, 13, 2,
                    41, 52, 31, 37, 47, 55, 30, 40,
                    51, 45, 33, 48, 44, 49, 39, 56,
                    34, 53, 46, 42, 50, 36, 29, 32 };//56 t0 48 bits

            int j, temp[] = new int[64];
            for (i = 0; i < 28; i++)
                temp[i] = c[i];

            for (i = 28, j = 0; i < 56; i++) {
                temp[i] = d[j];
                j++;
            }


            for (i = 0; i < 48; i++) {
                key1[i] = temp[pc2[i] - 1];
                keys += key1[i];
            }
            System.out.println(keys);
            listOfKey.add(keys);
        }
        return listOfKey;

    }


    //andrush

//64 bit msg ...48 bit 16 key
//32 bit msg into 48 bit msg….xor with 48 bit key
//resut pass to s box and get 32 bit result
//permute 32 and get final 32 bit
//xor with left value
//get final value
//left value is same as right value ..

    static int changeListOfKey=0;
    @Override
    public String encrypt(String plainText) {
        int [] afterIp= initialPermutation(HexToBinary(plainText));
        int [][] listOfKey= new int [16][48];
        ArrayList<String>Key =keyGeneration();
        //char [] char

        for(int i=0;i<16;i++)
            for(int j=0;j<48;j++)
            listOfKey[i][j]=Integer.parseInt(Character.toString(Key.get(i).charAt(j)));


        //make group of two
       // String left=afterIp.substring(0,31);
       // String right=afterIp.substring(32,63);
        int []leftArray = new int [32];
        int []rightArray =new int [32];

        int[] expandedRightArray = new int[48];


        //START OF ROUND FUNCTION

        ///group of two for first initiation...
        for (int i = 0, j = 0; i < 64; i++) {
            if (i < 32)
                leftArray[i] = afterIp[i];
            else {
                rightArray[j] = afterIp[i];
                j++;
            }
        }

        for(int round =0;round<16;round++) {

            //before replacing right array storing it in the temp
            int []tempRightArray= new int [32];
            for(int i =0;i<32;i++)
                tempRightArray[i]=rightArray[i];

            //round function
            //expand 32 bit to 48 bit

            for (int i = 0; i < 48; i++)
                expandedRightArray[i] = rightArray[expand32to48[i] - 1];
            //xor with list of key

            //48 bit to S boxes and get 32 bit
            //permutes 32 bit into 32 bit
            //xor 32 bit with left temp

            for (int i = 0; i < 48; i++) {
                if (expandedRightArray[i] != listOfKey[round][i])
                    expandedRightArray[i] = 1;
                else
                    expandedRightArray[i] = 0; //48
            }

            //8 group of 6
            int [][]groupOfSbox= new int [8][6];
            int k=0;

           for(int i=0;i<8;i++){
               for(int j=0;j<6;j++){
                   //accesing the 6 bit pair


                   groupOfSbox[i][j]=expandedRightArray[k];
                   k++;
               }
           }

           //creating decimal value to point to s box

            int [][]rowSbox= new int [8][2];
            int rowSboxCounter,columnSboxCounter;
            int [][]columnSbox= new int [8][4];
            for(int i=0;i<8;i++){
                for(int j =0;j<6;j++){
        //creating row and column pointer of the s box

                    rowSboxCounter=columnSboxCounter=0;
                    if(j==0 || j==5){
                        rowSbox[i][rowSboxCounter]=expandedRightArray[j];
                    }else{
                        columnSbox[i][columnSboxCounter]=expandedRightArray[j];
                    }
                }
            }
            String afterSbox="";
            String sBoxValueBinary="";
        for(int i=0;i<8;i++){
            for(int j=0;j<2;j++){
                int  row= Integer.parseInt(Integer.toString(rowSbox[i][j]),2);
                int column = Integer.parseInt(Integer.toString(columnSbox[i][j]),2);

              int sBoxValue= sBoxArray[i][16*(row-1)+column];
              sBoxValueBinary=hexValues[sBoxValue];

            }
            afterSbox=afterSbox +sBoxValueBinary;
        }

        //permutation after sbox value
            //changing sbox value to array
          /*  for(int i=0;i<16;i++)
                for(int j=0;j<48;j++)
                    listOfKey[i][j]=Integer.parseInt(Character.toString(Key.get(i).charAt(j)));*/
            String  permuteSboxResult="";
            for (int i = 0; i < 32; i++)
                permuteSboxResult+= afterSbox.charAt(afterSboxPermutation[i]-1);

            int []newRightArray= new int [32];

            //xor with left
            //create array of result after permutation of sbox result
             for(int i=0;i<32;i++)
                    newRightArray[i]=Integer.parseInt(Character.toString(permuteSboxResult.charAt(i)));



            for (int i = 0; i < 32; i++) {
                if (leftArray[i] != newRightArray[i])
                    rightArray[i] = 1;
                else
                    rightArray[i] = 0; //48
            }

            //right array create
            for(int i =0;i<32;i++)
                leftArray[i]=tempRightArray[i];
            //left array created



        }
        String swapAfterRound="";
        //swapping
        for (int i = 0;i < 64; i++) {
            if (i < 32)
                swapAfterRound+= Integer.toString(rightArray[i]);
            else {
                swapAfterRound+= Integer.toString(leftArray[i-32]);
            }
        }
        //final permuatation
        String cipherText="";
        for (int i = 0; i < 64; i++)
            cipherText+= swapAfterRound.charAt(inverseInitialPermutation[i]-1);

       // changeListOfKey++;
        return cipherText;
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
}
