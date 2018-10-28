package kmqc.manager.controller.qpu;

import kpfu.terentyev.quantum.emulator.api.Helper;

public class Transistor {

    public Transistor(Helper helper) {
        this.helper = helper;
        this.devL = new Device(Placing.Left);
        this.devC = new Device(Placing.Center);
        this.devR = new Device(Placing.Right);
    }

    public void setLeftState(Helper.QubitInfo qubitInfo) {
        devL.setState(qubitInfo);
    }

    public void setCenterState(Helper.QubitInfo qubitInfo) {
        devC.setState(qubitInfo);
    }

    public void setRightState(Helper.QubitInfo qubitInfo) {
        devR.setState(qubitInfo);
    }

    public Helper.QubitInfo getRidLeftState() {
        return devL.getRidState();
    }

    public Helper.QubitInfo getRidCenterState() {
        return devC.getRidState();
    }

    public Helper.QubitInfo getRidRightState() {
        return devR.getRidState();
    }

    public void opQET(double theta) throws Exception {
        helper.opQET(devL.getState(), devR.getState(), theta);
    }

    public void opPHASE(double theta) throws Exception {
        helper.opPHASE(devL.getState(), devR.getState(), theta);
    }

    public void opCQET(double theta) throws Exception {
        helper.opCQET(devL.getState(), devC.getState(), devR.getState(), theta);
    }

    private Helper helper;

    private Device devL;
    private Device devC;
    private Device devR;
}