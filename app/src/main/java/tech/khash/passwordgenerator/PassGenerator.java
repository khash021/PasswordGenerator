package tech.khash.passwordgenerator;

import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *      This is the class that actually does the work. It gets in password parameters,
 *      and then generates a random password based on the arguments.
 */

public class PassGenerator {

    private String TAG = getClass().getSimpleName();

    private int length;
    private boolean uppercase, lowercase, numbers, symbol;
    private ArrayList<String> sourceList;
    private final static String[] LOWERCASE_ARRAY = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private final static String[] UPPERCASE_ARRAY = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private final static String[] NUMBERS_ARRAY = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private final static String[] SYMBOL_ARRAY = {"!", "@", "#", "$", "%", "^", "&", "*", "-", "_", "+", "=", "?", "~", ":"};



    //default constructor
    public PassGenerator(){}

    //main constructor
    public PassGenerator(int length, boolean lowercase, boolean uppercase, boolean numbers,
                         boolean symbol) {
        this.length = length;
        this.uppercase = uppercase;
        this.lowercase = lowercase;
        this.numbers = numbers;
        this.symbol = symbol;
    }//constructor

    @Nullable public String generate() {
        //get the ArrayList
        sourceList = generateSourceArray();

        //check for null (This should never happen, since we do this test in MainActivity before creating PassGenerator object
        if (sourceList == null || sourceList.isEmpty()) {
            return null;
        }

        return generatePassword();
    }//generate

    //Helper method for creating the password
    private String generatePassword() {

        String result ="";
        int arraySize = sourceList.size();

        //use a loop based on the length
        for (int i = 0; i < length; i++) {
            int randomIndex = generateRandomDigit(arraySize);
            String passChar = sourceList.get(randomIndex);
            Log.v(TAG, "Randome digit is: " + passChar + " / " + arraySize);
            result += passChar;
        }//for

        return result;
    }//generatePassword

    //Helper method for populating the password source ArrayList based on the user's criteria
    private ArrayList<String> generateSourceArray() {
        //initialize the list
        ArrayList<String> resultArrayList = new ArrayList<String>();

        //populate the list based on criteria
        if (lowercase) {
            resultArrayList.addAll(Arrays.asList(LOWERCASE_ARRAY));
//            Log.d(TAG, convertArrayToString(resultArrayList));
        }
        if (uppercase) {
            resultArrayList.addAll(Arrays.asList(UPPERCASE_ARRAY));
//            Log.d(TAG, convertArrayToString(resultArrayList));
        }
        if (numbers) {
            resultArrayList.addAll(Arrays.asList(NUMBERS_ARRAY));
//            Log.d(TAG, convertArrayToString(resultArrayList));
        }
        if (symbol) {
            resultArrayList.addAll(Arrays.asList(SYMBOL_ARRAY));
//            Log.d(TAG, convertArrayToString(resultArrayList));
        }

        return resultArrayList;
    }//generateSourceArray

    //for testing only
    private String convertArrayToString(ArrayList<String> inputList) {
        String output = "";
        for (String item : inputList) {
            output += output + "    " + item;
        }
        return output;
    }//convertArrayToString

    //helper method for creating a random digit in the given range (exclusive, so this should be the array's size)
    private int generateRandomDigit(int range) {
        Random random = new Random();
        int result = random.nextInt(range);
        return result;
    }//generateRandomDigit

    /*---------------------------- UNIT TESTING ------------------------------------------  */

    public static int getLowercaseArraySize() {
        return LOWERCASE_ARRAY.length;
    }

    public static int getUppercaseArraySize() {
        return UPPERCASE_ARRAY.length;
    }

    public static int getNumbersArraySize() {
        return NUMBERS_ARRAY.length;
    }

    public static int getCharacterArraySize() {
        return SYMBOL_ARRAY.length;
    }


}//PassGenerator
