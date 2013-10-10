package enigma;

/** Class that represents a rotor that has no ratchet and does not advance.
 *  @author Ian Fox
 */
class FixedRotor extends Rotor {
    /** the permanent rotor check for fixed rotors. */
    static final int CONFIG_CHECK = 2;
    /**Constructor that produces a new fixed rotor.
    * with TYPE, SETTING and PLACEMENT. */
    public FixedRotor(String type, String setting, int placement) {
        super(type, setting, placement);
    }
    /** returns the BOOLEAN false for if fixedrotors advance. */
    @Override
    public boolean advances() {
        return false;
    }
    /** returns the BOOLEAN false, if a fixedrotor is at a notch. */
    @Override
    public boolean atNotch() {
        return false;
    }

    /** Fixed rotors do not advance. */
    @Override
    public void advance() {
    }
}
