package kmqc.manager.controller.qpu;

import kpfu.terentyev.quantum.emulator.api.QManager;

public class Transistor {

    public Transistor(QManager helper) {
        this.helper = helper;
        this.devL = new Device(Placing.Left);
        this.devC = new Device(Placing.Center);
        this.devR = new Device(Placing.Right);
    }

    public void setLeftState(QManager.QubitInfo qubitInfo) {
        devL.setState(qubitInfo);
    }

    public void setCenterState(QManager.QubitInfo qubitInfo) {
        devC.setState(qubitInfo);
    }

    public void setRightState(QManager.QubitInfo qubitInfo) {
        devR.setState(qubitInfo);
    }

    public QManager.QubitInfo getRidLeftState() {
        return devL.getRidState();
    }

    public QManager.QubitInfo getRidCenterState() {
        return devC.getRidState();
    }

    public QManager.QubitInfo getRidRightState() {
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

    private QManager helper;

    private Device devL;
    private Device devC;
    private Device devR;
}