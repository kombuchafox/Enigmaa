package enigma;
import java.util.Hashtable;


/** Class that represents a rotor in the enigma machine.
 *  @author Ian Fox
 */
class Rotor {

    /** Size of alphabet used for plaintext and ciphertext. */
    static final int ALPHABET_SIZE = 26;

    /** setting in char for Rotor. */
    static final int CONFIG_CHECK = 1;
    /** length of types of rotors. */
    static final int DATA_LENGTH = 12;
    /** where to begin to cast integers. */
    static final int ASCII_I = 65;
    /** where to end casting ascii integers. */
    static final int ASCII_E = 91;

    /** a string containging NONMOVING variable. */
    static final String[] NONMOVING = {"GAMMA, BETA, B, C"};
    /** returns a STRING array of ascii values.*/
    public static String[] toAlpha() {
        String[] alphabet = new String[ALPHABET_SIZE];
        for (int x = ASCII_I, index = 0; x < ASCII_E; x++, index++) {
            alphabet[index] = Character.toString((char) x);
        }
        return alphabet;
    }

    /** Assuming that P is an integer in the range 0..25, returns the STRING.
     *  corresponding upper-case letter in the range A..Z. */
    public static String toLetter(int p) {
        String[] alphabet = toAlpha();
        return alphabet[p];
    }

    /** Returns HASHTABLE, containing the ALPHA (bet) as keys.
    * ints as values. */
    public static Hashtable<String, Integer> fromAlpha() {
        String[] alphabet = toAlpha();
        Hashtable<String, Integer> index =
                    new Hashtable<String, Integer>(ALPHABET_SIZE);
        for (int count = 0; count < ALPHABET_SIZE; count++) {
            index.put(alphabet[count], count);
        }
        return index;
    }

    /** Assuming that C is an upper-case letter in the range A-Z.
    *  returns an uses a HASHTABLE  to returns INT Inverse of toLetter.*/
    public static int toNum(String c) {
        Hashtable<String, Integer> index = fromAlpha();
        return index.get(c);
    }

    /** Returns true BOOLEAN iff this rotor has a ratchet and can advance. */
    public boolean advances() {
        return true;
    }

    /** Returns true BOOLEAN iff this rotor has a left-to-right inverse. */
    public boolean hasInverse() {
        return true;
    }

    /** Return my current rotational setting as an integer between 0.
     *  and 25 (corresponding to letters 'A' to 'Z').  */
    public int getSetting() {
        return _setting;
    }

    /** update setting by one.  */
    public void advance() {
        _setting = this.getSetting() + 1;
        if (_setting < 0) {
            _setting = _setting + ALPHABET_SIZE;
        }
        if (_setting > (ALPHABET_SIZE - 1)) {
            _setting = _setting % ALPHABET_SIZE;
        }
    }

    /** Return the conversion of P (an integer in the range 0..25).
     *  according to my permutation. */
    public int convertForward(int p) {
        int numPosn = this.getSetting();
        int contact = (p + numPosn) % ALPHABET_SIZE;
        String data = getPermut()[1];
        String tempChar = Character.toString(data.charAt(contact));
        int outPut = toNum(tempChar) - numPosn;
        if (outPut < 0) {
            outPut = outPut + ALPHABET_SIZE;
        }
        return outPut;
    }

    /** Return the conversion of E (an integer in the range 0..25).
     *  according to the inverse of my permutation. */
    public int convertBackward(int e) {
        int numPosn = this.getSetting();
        int contact = (e + numPosn) % ALPHABET_SIZE;
        String data = getPermut()[2];
        String tempChar = Character.toString(data.charAt(contact));
        int outPut = toNum(tempChar) - numPosn;
        if (outPut < 0) {
            outPut = outPut + ALPHABET_SIZE;
        }
        return outPut;
    }

    /** Returns true iff I am positioned to allow the rotor to my left.
     *  to advance. */
    public boolean atNotch() {

        for (int elem = 0; elem < getPermut()[3].length(); elem++) {
            String letter = toLetter(getSetting());

            String question = Character.toString(getPermut()[3].charAt(elem));

            if (letter.equals(question)) {
                return true;
            }
        }
        return false;
    }

    /** creates a new ROTOR with TYPE SETTING and PLACEMENT ROTOR.*/
    public Rotor(String type, String setting, int placement) {
        int posn = toNum(setting);
        _setting = posn;
        _type = type;
        _placement = placement;
        setPermutation();
    }
    /** returns STRING of type. */
    public String getType() {
        return _type;
    }
    /** returns STRING  array of permutation data. */
    public String[] getPermut() {
        return _permutation;
    }
    /** return the INT placement of a rotor. */
    public int getPlace() {
        return _placement;
    }
    /** takes in a type and permutation data and sets the instance,
    ** variable _permutation to it specific type.
    ** i.e. for type "I" will set {"I", "data", "inverse", "notch"} */
    public void setPermutation() {
        String type = this.getType();
        String[][] data = PermutationData.ROTOR_SPECS;
        for (int index = 0; index < DATA_LENGTH; index++) {
            if (type.equals(data[index][0])) {
                _permutation = data[index];
                break;
            }
        }
    }

    /** My current setting (index 0..25, with 0 indicating that 'A',
     *  is showing). */
    private int _setting;

    /** rotor type {"I", "II"... "VIII"} of new Rotor. */
    private String _type;

    /**rotor placement within the machine. */
    private int _placement;

    /** permutation set for this rotor. */
    private String[] _permutation;

}
