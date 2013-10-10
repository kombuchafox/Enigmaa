package enigma;

import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Set;
import java.util.HashSet;

/** Enigma simulator.
 *  @author Ian F
 */
public final class Main {

    /** amount of unwanted ascii characters. */
    static final int ASCII_TARGETS = 42;
    /** next index for ascii array. */
    static final int ASCII_IND = 32;
    /** next index for ascii array. */
    static final int ASCII_INDII = 38;
    /** where to begin looking for ascii. */
    static final int TARGS_B = 33;
    /** where to end looking for ascii. */
    static final int TARGS_E = 65;
    /** where to begin looking for ascii, 2nd set. */
    static final int TARGSII_B = 91;
    /** where to end looking for ascii, 2nd set. */
    static final int TARGSII_E = 97;
    /** where to begin looking for ascii, 3rd set. */
    static final int TARGSIII_B = 123;
    /** where to end looking for ascii, 3rd set. */
    static final int TARGSIII_E = 127;
    /** Process a sequence of encryptions and decryptions, as.
     *  specified in the input from the standard input.  Print the
     *  results on the standard output. Exits normally if there are
     *  no errors in the input; otherwise with code 1. */
    public static void main(String[] unused) {

        BufferedReader input =
            new BufferedReader(new InputStreamReader(System.in));
        Machine M = new Machine();

        try {
            while (true) {
                String line = input.readLine();
                if (line == null) {
                    break;
                }
                if (isConfigurationLine(line)) {
                    configure(M, line);

                } else {
                    printMessageLine(M.convert(standardize(line)));
                }
            }
        } catch (IOException excp) {
            System.err.printf("Input error: %s%n", excp.getMessage());
            System.exit(1);
        }
    }
    /** Returns HASHTABLE, to check config.*/
    private static Hashtable<String, Integer> countcheck() {
        int movingR = 8, fix0 = 7, fix1 = 10, reflectors = 9;

        Hashtable<String, Integer> rotorCount =
            new Hashtable<String, Integer>(Rotor.DATA_LENGTH);
        String[] rotors = {"I", "II", "III", "IV", "V", "VI", "VII",
            "VIII", "BETA", "GAMMA", "B", "C"};
        for (int count = 0; count < Rotor.DATA_LENGTH; count++) {
            if (count < movingR) {
                rotorCount.put(rotors[count], Rotor.CONFIG_CHECK);
            }
            if ((count > fix0) && (count < fix1)) {
                rotorCount.put(rotors[count], FixedRotor.CONFIG_CHECK);
            }
            if (count > reflectors) {
                rotorCount.put(rotors[count], Reflector.CONFIG_CHECK);
            }
        }
        return rotorCount;
    }
    /** returns STRING[] of tokens contain the tokenized elements of LINE.*/
    static String[] tokenized(String line) {
        String[] newLine = line.split("\t");
        String newLine2 = toStr(newLine);
        StringTokenizer tokenizer = new StringTokenizer(newLine2, " ");
        int length = tokenizer.countTokens();
        String[] tokens = new String[length];
        for (int elem = 0; elem < length; elem++) {
            tokens[elem] = tokenizer.nextToken();
        }

        return tokens;

    }
    /** returns STRING array of the lower case alphabet. */
    private static String[] tolittleAlpha() {
        String[] alphabet = new String[Rotor.ALPHABET_SIZE];
        for (int ind = 0, x = TARGSII_E; x < TARGSIII_B; x++, ind++) {
            alphabet[ind] = Character.toString((char) x);
        }
        return alphabet;
    }
    /** returns a HASHTABLE of lowercase letters to Capitals. */
    private static Hashtable<String, String> caps() {
        Hashtable<String, String> cap =
            new Hashtable<String, String>(Rotor.ALPHABET_SIZE);
        String[] alphabetCAPS = Rotor.toAlpha();
        String[] alpha = tolittleAlpha();

        for (int i = 0; i < Rotor.ALPHABET_SIZE; i++) {
            cap.put(alpha[i], alphabetCAPS[i]);
        }
        return cap;
    }
    /** returns STRING of String STR array. */
    public static String toStr(String[] str) {
        String result = "";
        for (int i = 0; i < str.length; i++) {
            result = result + str[i];
        }
        return result;

    }
    /** Return true iff L is an Enigma configuration line. */
    private static boolean isConfigurationLine(String l) {
        if (l.equals("")) {
            return false;
        }
        String[] tokens = tokenized(l);
        Hashtable<String, Integer> check = countcheck();
        int fixedCount = 0, movCount = 0, reflectCount = 0;
        Set<String> movCheck = new HashSet<String>();


        if (tokens[0].equals("*")) {
            for (int index = 1; index < 7; index++) {
                if (tokens.length < 7) {
                    System.exit(1);
                }
                if (!(movCheck.add(tokens[index]))) {
                    System.exit(1);
                }
                if (index == 6) {
                    if (tokens[index].length() != 4) {
                        System.exit(1);
                    }
                    String[] asciiFlag = asciiFlags();
                    for (int i = 0; i < asciiFlag.length; i++) {
                        if (tokens[index].contains(asciiFlag[i])) {
                            System.exit(1);
                        }
                    }
                    return true;
                }
                if (check.get(tokens[index]) == Reflector.CONFIG_CHECK) {
                    reflectCount += Reflector.CONFIG_CHECK;
                    if (reflectCount > Reflector.CONFIG_CHECK) {
                        System.exit(1);
                    }
                    if ((movCount > 0) && (fixedCount > 0)) {
                        System.exit(1);
                    }
                }
                if (check.get(tokens[index]) == FixedRotor.CONFIG_CHECK) {
                    fixedCount += 2;
                    if (fixedCount > FixedRotor.CONFIG_CHECK) {
                        System.exit(1);
                    }
                    if (movCount > 0) {
                        System.exit(1);
                    }
                }
                if (check.get(tokens[index]) == Rotor.CONFIG_CHECK) {
                    movCount += 1;
                    if (movCount > 3) {
                        System.exit(1);
                    }
                }
            }
        }
        return false;
    }
    /** Configure M according to the specification given on CONFIG,
     *  which must have the format specified in the assignment. */
    private static void configure(Machine M, String config) {

        String[] conArray = tokenized(config);
        Rotor[] rotors = new Rotor[5];
        String setting = conArray[6];
        Hashtable<String, String> newSett = new Hashtable<String, String>(4);
        newSett.put(conArray[1], "A");
        newSett.put(conArray[2], Character.toString(setting.charAt(0)));
        newSett.put(conArray[3], Character.toString(setting.charAt(1)));
        newSett.put(conArray[4], Character.toString(setting.charAt(2)));
        newSett.put(conArray[5], Character.toString(setting.charAt(3)));
        for (int elem = 1; elem < 6; elem++) {
            if (elem == 1) {
                rotors[elem - 1] = new Reflector(
                    conArray[elem],
                    newSett.get(conArray[elem]),
                    elem);
                continue;
            }
            if (elem == 2) {
                rotors[elem - 1] = new FixedRotor(
                    conArray[elem],
                    newSett.get(conArray[elem]),
                    elem);
                continue;
            }
            rotors[elem - 1] = new Rotor(
                conArray[elem],
                newSett.get(conArray[elem]),
                elem);
        }
        M.changeConfig(1);
        M.replaceRotors(rotors);
    }
    /** returns a STRING array of unwanted ascii vals. */
    private static String[] asciiFlags() {
        String[] asciiElems = new String[ASCII_TARGETS];
        for (int i = 0, x = TARGS_B; x < TARGS_E; x++, i++) {
            asciiElems[i] = Character.toString((char) x);
        }
        for (int i = ASCII_IND, x = TARGSII_B; x < TARGSII_E; x++, i++) {
            asciiElems[i] = Character.toString((char) x);
        }
        for (int i = ASCII_INDII, x = TARGSIII_B; x < TARGSIII_E; x++, i++) {
            asciiElems[i] = Character.toString((char) x);
        }

        return asciiElems;
    }
    /** Return the result of converting LINE to all upper case,
     *  removing all blanks and tabs.  It is an error if LINE contains
     *  Strings other than letters and blanks. */
    private static String standardize(String line) {
        String[] linemod = tokenized(line);
        String newLine = toStr(linemod);
        String[] asciiFlag = asciiFlags();
        Hashtable<String, String> capitals = caps();
        for (int i = 0; i < asciiFlag.length; i++) {
            if (newLine.contains(asciiFlag[i])) {
                System.exit(1);
            }
        }
        for (String key : capitals.keySet()) {
            newLine = newLine.replaceAll(key, capitals.get(key));
        }
        return newLine;
    }
    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private static void printMessageLine(String msg) {
        String[] newMsg = new String[5];
        while (msg.length() > 5) {
            System.out.print(msg.substring(0, 5));
            System.out.print(" ");
            msg = msg.substring(5);
        }
        System.out.print(msg);
        System.out.println("");

    }
}

