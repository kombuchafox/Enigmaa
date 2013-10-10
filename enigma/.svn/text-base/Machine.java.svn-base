package enigma;
import java.util.Hashtable;

/** Class that represents a complete enigma machine.
 *  @author Ian Fox
 */
class Machine {

    /** a new Machine with rotors configured to the elements within ROTORS.
    * and a CONFIG */
    public Machine(Rotor[] rotors, int config) {
        replaceRotors(rotors);
        _config = config;
    }
    /** default rotors for Machine DEF. */
    private static Rotor[] _def = {
        new Reflector("B", "A", 1),
        new FixedRotor("BETA", "A", 2),
        new Rotor("III", "A", 3),
        new Rotor("II", "A", 4),
        new Rotor("IV", "A", 5)
    };
    /** alternative constructor for machine. */
    public Machine() {
        this(getDef(), 0);
    }
    /** returns ROTOR array for default config. */
    public static Rotor[] getDef() {
        return _def;
    }
    /** Set my rotors to (from left to right) ROTORS. */
    void replaceRotors(Rotor[] rotors) {
        _rotor1 = rotors[0];
        _rotor2 = rotors[1];
        _rotor3 = rotors[2];
        _rotor4 = rotors[3];
        _rotor5 = rotors[4];
    }
    /** change the config to NEEW. */
    void changeConfig(int neew) {
        _config = neew;
    }
    /** returns the INT if the machine has been configured. */
    int getConfig() {
        return _config;
    }
    /** returns a HASHTABLE which maps rotors to numbers. */
    private Hashtable<Integer, Rotor> map() {
        Hashtable<Integer, Rotor> mapped = new Hashtable<Integer, Rotor>();
        mapped.put(1, _rotor1); mapped.put(2, _rotor2);
        mapped.put(3, _rotor3); mapped.put(4, _rotor4);
        mapped.put(5, _rotor5);
        return mapped;
    }
    /** takes in a ROTOR5 and updates it . */
    void updateRotors(Rotor rotor5) {
        Rotor rotor4 = get(4), rotor3 = get(3);
        Boolean n4 = rotor4.atNotch(), n3 = rotor3.atNotch();
        Boolean n5 = rotor5.atNotch();
        rotor5.advance();
        if (n5 && !n4) {
            rotor4.advance();
        } else {
            if (n4) {
                rotor3.advance();
                rotor4.advance();
            }
        }
    }

    /** get methot which returns the ROTOR from the NUM you inputed. */
    public Rotor get(int num) {
        Hashtable<Integer, Rotor> mapped = this.map();
        return mapped.get(num);
    }
    /** the five ROTORS the machine has. */
    private Rotor _rotor1, _rotor2, _rotor3, _rotor4, _rotor5;
    /** the int value to see if the machine has been config. */
    private int _config;
    /** Returns the encoding/decoding of MSG, updating the state of,
     *  the rotors accordingly. */
    String convert(String msg) {
        if (this.getConfig() == 0) {
            System.exit(1);
        }
        if (msg.equals("")) {
            return "";
        } else {
            String[] result = new String[msg.length()];

            for (int i = 0; i < msg.length(); i++) {
                int posn0 = Rotor.toNum(Character.toString(msg.charAt(i)));

                updateRotors(this.get(5));
                int posn9 = this.get(5).convertForward(posn0);
                int posn8 = this.get(4).convertForward(posn9);
                int posn7 = this.get(3).convertForward(posn8);
                int posn6 = this.get(2).convertForward(posn7);
                int posn5 = ((Reflector) this.get(1)).reflect(posn6);
                int posn4 = this.get(2).convertBackward(posn5);
                int posn3 = this.get(3).convertBackward(posn4);
                int posn2 = this.get(4).convertBackward(posn3);
                int posn1 = this.get(5).convertBackward(posn2);
                result[i] = Rotor.toLetter(posn1);
            }
            return Main.toStr(result);
        }
    }
}
