package tech.khash.passwordgenerator;

import java.util.ArrayList;

/**
 *      This is the class that actually does the work. It gets in password parameters,
 *      and then generates a random password based on the arguments.
 */

public class PassGenerator {

    private int length;
    private boolean uppercase, lowercase, numbers, symbols;
    private ArrayList<String> sourceList;
    private final static String[] LOWERCASE_ARRAY = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private final static String[] UPPERCASE_ARRAY = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private final static String[] NUMBERS_ARRAY = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private final static String[] CHARACTER_ARRAY = {"!", "@", "#", "$", "%", "^", "&", "*", "-", "_", "+", "=", "?", "~", ":"};



    //default constructor
    public PassGenerator(){}

    //main constructor
    public PassGenerator(int length, boolean uppercase, boolean lowercase, boolean numbers,
                         boolean symbols) {
        this.length = length;
        this.uppercase = uppercase;
        this.lowercase = lowercase;
        this.numbers = numbers;
        this.symbols = symbols;
    }//constructor

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
        return CHARACTER_ARRAY.length;
    }


}//PassGenerator
