package kpfu.terentyev.quantum.emulator.core;

/**
 * Created by alexandrterentyev on 19.05.15.
 */
public class QSchemeStepQubitAttributes {
    public String gateID;

    /*
    * This equals to qubit is "upper than other"
    * */
    boolean control;
    public static String IdentityGateID = "IdentityGateID";

    public QSchemeStepQubitAttributes(String gateID, boolean control) {
        this.gateID = gateID;
        this.control = control;
    }

    public QSchemeStepQubitAttributes () {
        this.gateID = IdentityGateID;
        this.control =false;
    }
}
