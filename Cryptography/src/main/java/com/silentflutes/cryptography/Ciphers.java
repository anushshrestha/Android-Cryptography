package com.silentflutes.cryptography;

import android.app.Activity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sillentflutes on 3/27/14.
 */
public abstract class Ciphers extends Activity {


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


    public abstract String encrypt(String plainText);

    public abstract String decrypt(String cipherText);

    public abstract void restoreFromFile(BufferedReader paramBufferedReader);

    public abstract void saveToFile(BufferedWriter paramBufferedWriter);

}
