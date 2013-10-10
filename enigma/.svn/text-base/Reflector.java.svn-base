
package enigma;

/** Class that represents a reflector in the enigma.
 *  @author Ian Fox
 */
class Reflector extends Rotor {
    /** Reflector constructor, uses same settings: TYPE, SETTING, PLACEMENT. */
    public Reflector(String type, String setting, int placement) {
        super(type, setting, placement);
    }
    /**the specific config check for Reflector. */
    static final int CONFIG_CHECK = 3;
    /** a test to see if something has an inverse.
    * returns BOOLEAN */
    @Override
    public boolean hasInverse() {
        return false;
    }
    /** returns BOOLEAN to see if the reflector advances. */
    @Override
    public boolean advances() {
        return false;
    }
    /** returns if the Reflector is at a notch, a BOOLEAN. */
    @Override
    public boolean atNotch() {
        return false;
    }
    /** does not change the setting. */
    @Override
    public void advance() {
        _setting = getSetting() + 0;
    }
    /** returns the INT of the flected input POSN. */
    int reflect(int posn) {
        String data = getPermut()[1];
        return toNum(Character.toString(data.charAt(posn)));
    }

    /** Returns a useless value; should never be called. */
    @Override
    public int convertBackward(int unused) {
        throw new UnsupportedOperationException();
    }
    /** the string type of the reflector. */
    private String _type;
    /** the placement of the reflector, an int. */
    private int _placement;
    /** return the setting of reflector and int. */
    private int _setting;
    /** the specific permutation of the reflector. */
    private String[] _permutation;
}
