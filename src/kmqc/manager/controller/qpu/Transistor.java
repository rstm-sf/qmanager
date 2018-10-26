package kmqc.manager.controller.qpu;

import kpfu.terentyev.quantum.emulator.api.QManager;

public class Transistor {

    public Transistor(QManager helper) {
        this.helper = helper;
        this.cellL = new CellTransistor(Placing.Left);
        this.cellC = new CellTransistor(Placing.Center);
        this.cellR = new CellTransistor(Placing.Right);
    }

    public setLeftState(QManager.QubitInfo qubitInfo) {
        cellL.setState(qubitInfo);
    }

    public setCenterState(QManager.QubitInfo qubitInfo) {
        cellC.setState(qubitInfo);
    }

    public setRightState(QManager.QubitInfo qubitInfo) {
        cellR.setState(qubitInfo);
    }

    public QManager.QubitInfo getRidLeftState() {
        return cellL.getRidState();
    }

    public QManager.QubitInfo getRidCenterState() {
        return cellC.getRidState();
    }

    public QManager.QubitInfo getRidRightState() {
        return cellR.getRidState();
    }

    public void opQET(double theta) throws Exception {
        helper.opQET(cellL.getState(), cellR.getState());
    }

    public void opPHASE(double theta) throws Exception {
        helper.opPHASE(cellL.getState(), cellR.getState());
    }

    public void opCQET(double theta) throws Exception {
        helper.opCQET(cellL.getState(), cellC.getState(), cellR.getState());
    }

    private QManager helper;

    private CellTransistor cellL;
    private CellTransistor cellC;
    private CellTransistor cellR;
}