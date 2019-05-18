package kmqc.simulator.core;

/**
 * Created by alexandrterentyev on 19.05.15.
 */
public class QSchemeStepQubitAttr {

    public String gateID;

    /*
    * This equals to qubit is "upper than other"
    * */
    boolean control;

    public static final String IdentityGateID = "IdentityGateID";

    public QSchemeStepQubitAttr(String gateID, boolean control) {
        this.gateID  = gateID;
        this.control = control;
    }

    public QSchemeStepQubitAttr() {
        this.gateID  = IdentityGateID;
        this.control = false;
    }
}
