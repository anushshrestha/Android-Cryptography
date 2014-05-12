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

/**
 * Created by sillentflutes on 4/3/14.
 */
public class DES extends Ciphers implements View.OnClickListener {

    static final int[] afterSboxPermutation = {
            16, 7, 20, 21,
            29, 12, 28, 17,
            1, 15, 23, 26,
            5, 18, 31, 10,
            2, 8, 24, 14,
            32, 27, 3, 9,
            19, 13, 30, 6,
            22, 11, 4, 25
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
    private static final int[][] sBoxArray = {{
            14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7,
            0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8,
            4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0,
            15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13
    }, {
            15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10,
            3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5,
            0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15,
            13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9
    }, {
            10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8,
            13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1,
            13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7,
            1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12
    }, {
            7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15,
            13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9,
            10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4,
            3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14
    }, {
            2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9,
            14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6,
            4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14,
            11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3
    }, {
            12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11,
            10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8,
            9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6,
            4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13
    }, {
            4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1,
            13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6,
            1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2,
            6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12
    }, {
            13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7,
            1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2,
            7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8,
            2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11
    }};
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
    static String[] hexToBinaryValues =
            {
                    "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111",
                    "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"
            };

    static String[] binaryToHexValues =
            {
                    "0", "1", "2", "3", "4", "5", "6", "7",
                    "8", "9", "A", "B", "C", "D", "E", "F"
            };
    static int changeListOfKey = 0;
    EditText hexInput;
    Button encrypt, decrypt;
    TextView tv;
    int[][] listOfKey = new int[16][48];

    public static String HexToBinary(String Hex) {  //conversion from HEX input to binary
        char[] arrayOfChar = Hex.toCharArray();
        String binaryInput = "";
        for (int i = 0; i < Hex.length(); i++) {
            binaryInput += hexToBinaryValues[Integer.parseInt(Character.toString(arrayOfChar[i]), 16)];
        }
        return binaryInput;
    }

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

    //Input the 64 bit binary input and return
    public int[] initialPermutation(String inputBeforeIp) {
        int[] afterIpInput = new int[64];
        char[] beforeIp = inputBeforeIp.toCharArray();
        for (int i = 0; i < 64; i++)
            afterIpInput[i] = Integer.parseInt(Character.toString(beforeIp[initialPermutation[i] - 1]));
        return afterIpInput;
    }


    //BY MILAN
    public void keyGeneration() {
        //VALUES NEEDED FOR KEY GENERATION
        int inputKeyValue[] =
                {
                        0, 0, 0, 1, 0, 0, 1, 1,
                        0, 0, 1, 1, 0, 1, 0, 0,
                        0, 1, 0, 1, 0, 1, 1, 1,
                        0, 1, 1, 1, 1, 0, 0, 1,
                        1, 0, 0, 1, 1, 0, 1, 1,
                        1, 0, 1, 1, 1, 1, 0, 0,
                        1, 1, 0, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 0, 0, 0, 1
                };
        int permutationChoice1[] =
                {
                        57, 49, 41, 33, 25, 17, 9,  1,
                        58, 50, 42, 34, 26, 18, 10, 2,
                        59, 51, 43, 35, 27, 19, 11, 3,
                        60, 52, 44, 36, 63, 55, 47, 39,
                        31, 23, 15,  7, 62, 54, 46, 38,
                        30, 22, 14,  6, 61, 53, 45, 37,
                        29, 21, 13,  5, 28, 20, 12, 4
                };
        int permutationChoice2[] =
                {
                        14, 17, 11, 24,  1, 5,  3, 28,
                        15,  6, 21, 10, 23, 19, 12, 4,
                        26,  8, 16,  7, 27, 20, 13, 2,
                        41, 52, 31, 37, 47, 55, 30, 40,
                        51, 45, 33, 48, 44, 49, 39, 56,
                        34, 53, 46, 42, 50, 36, 29, 32
                };

        int permutedKey[] = new int[56];
        int i;
        //Permutation of key using permuatation choice 1 box
        for (i = 0; i < 56; i++)
            permutedKey[i] = inputKeyValue[permutationChoice1[i] - 1];

        //Dividing the permuted key into two halves
        int cLeftHalf[] = new int[28], dRightHalf[] = new int[28];
        for (i = 0; i < 56; i++) {
            if (i < 28)
                cLeftHalf[i] = permutedKey[i];
            else
                dRightHalf[i - 28] = permutedKey[i];
        }

        //Rounds for generation of 16, 48 bits key
        for (int round = 0; round < 16; round++) {
            // round 1,2,9,16-1 bit shift; other-2 bits shift
            //after starting from 0 then 1 bit shift =0,1,8,15
            if (round == 0 || round == 1 || round == 8 || round == 15) {
                int firstLeftBit = cLeftHalf[0], firstRightBit = dRightHalf[0];
                for (i = 0; i < 28; i++) {
                    if (i + 1 != 28) {
                        cLeftHalf[i] = cLeftHalf[i + 1];
                        dRightHalf[i] = dRightHalf[i + 1];
                    }
                    else{ //restoring the first bit
                        cLeftHalf[i] = firstLeftBit;
                        dRightHalf[i] = firstRightBit;
                    }
                }
            } else {
                for (int shiftTwice = 0; shiftTwice < 2; shiftTwice++) { //shifting twice for rest of the bit
                    int firstLeftBit = cLeftHalf[0], firstRightBit = dRightHalf[0];
                    for (i = 0; i < 28; i++) {
                        if (i + 1 != 28) {
                            cLeftHalf[i] = cLeftHalf[i + 1];
                            dRightHalf[i] = dRightHalf[i + 1];
                        }
                        else { //restoring the first bit
                            cLeftHalf[i] = firstLeftBit;
                            dRightHalf[i] = firstRightBit;
                        }
                    }
                }
            }
            //MERGING THE SHIFTED LEFT AND RIGHT BITS
            int j, mergeShiftedBits[] = new int[56];
            for (i = 0; i < 56; i++) {
                if (i < 28)
                    mergeShiftedBits[i] = cLeftHalf[i];
                else
                    mergeShiftedBits[i] = dRightHalf[i - 28];
            }
            //PERMUTE MERGED BIT BY USING PERMUTATION CHOICE 2
            String keysGenerated = "";
            for (i = 0; i < 48; i++)
                keysGenerated += mergeShiftedBits[permutationChoice2[i] - 1];
            //ADD KEY INTO ARRAY FOR ROUND FUNCTION.
            //System.out.println(keysGenerated);
            for (j = 0; j < 48; j++)
                listOfKey[round][j] = Integer.parseInt(Character.toString(keysGenerated.charAt(j)));
        }
    }

    int[] leftArray = new int[32];
    int[] rightArray = new int[32];

    // BY ANUJ AND ANUSH
    @Override
    public String encrypt(String plainText) {
        int[] afterIp = initialPermutation(HexToBinary(plainText));
        keyGeneration();

        //START OF ROUND FUNCTION
        ///BREAKING 64 BIT MSG INTO 2 GROUP OF 32 BIT

        for (int i = 0; i < 64; i++) {
            if (i < 32)
                leftArray[i] = afterIp[i];
            else
                rightArray[i-32] = afterIp[i];
        }

        for (int round = 0; round < 16; round++) {
            //before replacing right array storing it in the temp
            int[] tempRightArray = new int[32];
            for (int i = 0; i < 32; i++)
                tempRightArray[i] = rightArray[i];

            //Permute or expand 32 bit right side bit to 48 bit
            int[] expandedRightArray = new int[48];
            for (int i = 0; i < 48; i++)
                expandedRightArray[i] = rightArray[expand32to48[i] - 1];

            //XOR EXPANDED 48 BIT RIGHT SIDE AND 48 BIT KEY
            for (int i = 0; i < 48; i++) {
                if (expandedRightArray[i] != listOfKey[round][i])
                    expandedRightArray[i] = 1;
                else
                    expandedRightArray[i] = 0; //48
            }
            //CREATING 8 GROUPS OF 6 BITS FOR SBOX
            int[][] groupOfSbox = new int[8][6];
            int k = 0;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 6; j++) {
                    groupOfSbox[i][j] = expandedRightArray[k];
                    k++;
                }
            }
            //SEPARATING GROUP OF 6 BITS INTO 2 BITS ROW POINTER AND 4 BITS COLUMN POINTER
            int[][] rowSbox = new int[8][2];
            int rowSboxCounter, columnSboxCounter;
            int[][] columnSbox = new int[8][4];
            for (int i = 0; i < 8; i++) {
                rowSboxCounter = columnSboxCounter = 0;
                for (int j = 0; j < 6; j++) {
                    if (j == 0 || j == 5) { //1st and last bit to point row
                        rowSbox[i][rowSboxCounter] = expandedRightArray[6 * i + j];//6*i to jump, all values in single array
                        rowSboxCounter++;
                    } else { //rest of bit to point column
                        columnSbox[i][columnSboxCounter] = expandedRightArray[6 * i + j];
                        columnSboxCounter++;
                    }
                }
            }
            //ACCESSING SBOX, GETTING VALUE AND CONVERTING TO BINARY
            String afterSbox = "";
            String sBoxValueBinary = "";
            for (int i = 0; i < 8; i++) {
                String rowValueString = "";
                String columnValueString = "";

                for (int j = 0; j < 2; j++) //gathering 2 bits for row
                    rowValueString += rowSbox[i][j];
                int row = Integer.parseInt(rowValueString, 2); //row in decimal

                for (int j = 0; j < 4; j++) //gathering 4 bits for column
                    columnValueString += columnSbox[i][j];
                int column = Integer.parseInt(columnValueString, 2);//column in decimal

                int sBoxValue = sBoxArray[i][16 * row + column];//getting value from sbox
                sBoxValueBinary = hexToBinaryValues[sBoxValue]; //change sbox value to binary
                afterSbox = afterSbox + sBoxValueBinary;
            }

            String permuteSboxResult = "";
            for (int i = 0; i < 32; i++) //permutation of result of sbox  32 to 32 bit
                permuteSboxResult += afterSbox.charAt(afterSboxPermutation[i] - 1);

            int[] afterRoundFunction = new int[32];
            for (int i = 0; i < 32; i++) //create array of result after permutation of sbox result
                afterRoundFunction[i] = Integer.parseInt(Character.toString(permuteSboxResult.charAt(i)));

            //END OF ROUND FUNCTION

            for (int i = 0; i < 32; i++) {
                //creation of right array left xor fxn result
                if (leftArray[i] != afterRoundFunction[i])
                    rightArray[i] = 1;
                else
                    rightArray[i] = 0; //48
            }

            for (int i = 0; i < 32; i++)
                leftArray[i] = tempRightArray[i];//left array created



        }
        String swapAfterRound = "";
        //swapping
        for (int i = 0; i < 64; i++) {
            if (i < 32)
                swapAfterRound += Integer.toString(rightArray[i]);
            else {
                swapAfterRound += Integer.toString(leftArray[i - 32]);
            }
        }
        //final permuatation
        String cipherTextBinary = "";
        for (int i = 0; i < 64; i++)
            cipherTextBinary += swapAfterRound.charAt(inverseInitialPermutation[i] - 1);

        String cipherTextHex = "";
        int multiplier = 0;
        for (int i = 0; i < 16; i++) {
            String groupOfCipherTextBinary = cipherTextBinary.substring(4 * multiplier, (4 * multiplier) + 4);
            cipherTextHex += Integer.toHexString(Integer.parseInt(groupOfCipherTextBinary, 2));
            multiplier++;
        }
        return cipherTextHex;
    }

    @Override
    public String decrypt(String cipherText) {
        //int[] afterIp = initialPermutation(HexToBinary(cipherText));
        //keyGeneration();

        for (int round = 15; round >=0; round--) {
            //Rn-1=Ln and Ln-1=Rn XOR f(Ln,Kn)
            int[] tempRightArray = new int[32];
            for (int i = 0; i < 32; i++) {
                tempRightArray[i] = rightArray[i];
                rightArray[i]=leftArray[i];
            }

            //Permute or expand 32 bit right side bit to 48 bit
            int[] expandedLeftArray = new int[48];
            for (int i = 0; i < 48; i++)
                expandedLeftArray[i] = leftArray[expand32to48[i] - 1];

            //XOR EXPANDED 48 BIT RIGHT SIDE AND 48 BIT KEY
            for (int i = 0; i < 48; i++) {
                if (expandedLeftArray[i] != listOfKey[round][i])
                    expandedLeftArray[i] = 1;
                else
                    expandedLeftArray[i] = 0; //48
            }
            //CREATING 8 GROUPS OF 6 BITS FOR SBOX
            int[][] groupOfSbox = new int[8][6];
            int k = 0;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 6; j++) {
                    groupOfSbox[i][j] = expandedLeftArray[k];
                    k++;
                }
            }
            //SEPARATING GROUP OF 6 BITS INTO 2 BITS ROW POINTER AND 4 BITS COLUMN POINTER
            int[][] rowSbox = new int[8][2];
            int rowSboxCounter, columnSboxCounter;
            int[][] columnSbox = new int[8][4];
            for (int i = 0; i < 8; i++) {
                rowSboxCounter = columnSboxCounter = 0;
                for (int j = 0; j < 6; j++) {
                    if (j == 0 || j == 5) { //1st and last bit to point row
                        rowSbox[i][rowSboxCounter] = expandedLeftArray[6 * i + j];//6*i to jump, all values in single array
                        rowSboxCounter++;
                    } else { //rest of bit to point column
                        columnSbox[i][columnSboxCounter] = expandedLeftArray[6 * i + j];
                        columnSboxCounter++;
                    }
                }
            }
            //ACCESSING SBOX, GETTING VALUE AND CONVERTING TO BINARY
            String afterSbox = "";
            String sBoxValueBinary = "";
            for (int i = 0; i < 8; i++) {
                String rowValueString = "";
                String columnValueString = "";

                for (int j = 0; j < 2; j++) //gathering 2 bits for row
                    rowValueString += rowSbox[i][j];
                int row = Integer.parseInt(rowValueString, 2); //row in decimal

                for (int j = 0; j < 4; j++) //gathering 4 bits for column
                    columnValueString += columnSbox[i][j];
                int column = Integer.parseInt(columnValueString, 2);//column in decimal

                int sBoxValue = sBoxArray[i][16 * row + column];//getting value from sbox
                sBoxValueBinary = hexToBinaryValues[sBoxValue]; //change sbox value to binary
                afterSbox = afterSbox + sBoxValueBinary;
            }

            String permuteSboxResult = "";
            for (int i = 0; i < 32; i++) //permutation of result of sbox  32 to 32 bit
                permuteSboxResult += afterSbox.charAt(afterSboxPermutation[i] - 1);

            int[] afterRoundFunction = new int[32];
            for (int i = 0; i < 32; i++) //create array of result after permutation of sbox result
                afterRoundFunction[i] = Integer.parseInt(Character.toString(permuteSboxResult.charAt(i)));

            //END OF ROUND FUNCTION

            for (int i = 0; i < 32; i++) {
                //creation of right array left xor fxn result
                if (tempRightArray[i] != afterRoundFunction[i])
                    leftArray[i] = 1;
                else
                    leftArray[i] = 0; //48
            }

        }
        String swapAfterRound = "";
        //swapping
        for (int i = 0; i < 64; i++) {
            if (i < 32)
                swapAfterRound += Integer.toString(leftArray[i]);
            else {
                swapAfterRound += Integer.toString(rightArray[i - 32]);
            }
        }
        //final permuatation
        String cipherTextBinary = "";
        for (int i = 0; i < 64; i++)
            cipherTextBinary += swapAfterRound.charAt(inverseInitialPermutation[i] - 1);

        String plainTextHex = "";
        int multiplier = 0;
        for (int i = 0; i < 16; i++) {
            String groupOfCipherTextBinary = cipherTextBinary.substring(4 * multiplier, (4 * multiplier) + 4);
            plainTextHex += Integer.toHexString(Integer.parseInt(groupOfCipherTextBinary, 2));
            multiplier++;
        }
        return plainTextHex;
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
