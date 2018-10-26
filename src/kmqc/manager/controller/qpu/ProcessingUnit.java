package kmqc.manager.controller.qpu;

import java.util.ArrayList;

import kpfu.terentyev.quantum.emulator.api.QManager;

public class ProcessingUnit {
    
    public ProcessingUnit(QManager helper, int countOfTransistors) {
        this.helper = helper;
        this.countOfTransistors = countOfTransistors;
        chip = new ArrayList<Transistor>(countOfTransistors);
        for (int i = 0; i < countTransistor; i++) {
            unit.add(new Transistor(this.helper));
        }
    }

    public void setLeftState(
        int idxTransistor, QManager.QubitInfo qubitInfo) {
        checkIdx(idxTransistor);
        unit[idxTransistor].setLeftState(qubitInfo);
    }

    public void setCenterState(
        int idxTransistor, QManager.QubitInfo qubitInfo) {
        checkIdx(idxTransistor);
        unit[idxTransistor].setCenterState(qubitInfo);
    }

    public void setRightState(
        int idxTransistor, QManager.QubitInfo qubitInfo) {
        checkIdx(idxTransistor);
        unit[idxTransistor].setRightState(qubitInfo);
    }

    public QManager.QubitInfo getRidLeftState(int idxTransistor) {
        checkIdx(idxTransistor);
        return unit[idxTransistor].getRidLeftState();
    }

    public QManager.QubitInfo getRidCenterState(int idxTransistor) {
        checkIdx(idxTransistor);
        return unit[idxTransistor].getRidCenterState();
    }

    public QManager.QubitInfo getRidCenterState(int idxTransistor) {
        checkIdx(idxTransistor);
        return unit[idxTransistor].getRidRightState();
    }

    public void opQET(int idxTransistor, double theta) throws Exception {
        checkIdx(idxTransistor);
        unit[idxTransistor].opQET(theta);
    }

    public void opPHASE(int idxTransistor, double theta) throws Exception {
        checkIdx(idxTransistor);
        unit[idxTransistor].opPHASE(theta);
    }

    public void opCQET(int idxTransistor, double theta) throws Exception {
        checkIdx(idxTransistor);
        unit[idxTransistor].opCQET(theta);
    }

    private void checkIdx(int idx) {
        if (idx < 0 || idx >= countOfTransistors) {
            throw new Exception("Address is out of available range");
        }
    }

    private QManager helper;

    private int countOfTransistors;
    private ArrayList<Transistor> unit;
}