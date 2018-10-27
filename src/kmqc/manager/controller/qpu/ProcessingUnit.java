package kmqc.manager.controller.qpu;

import java.util.ArrayList;
import java.util.List;

import kpfu.terentyev.quantum.emulator.api.QManager;

public class ProcessingUnit {
    
    public ProcessingUnit(QManager helper, int countOfTransistors) {
        this.helper = helper;
        this.countOfTransistors = countOfTransistors;
        unit = new ArrayList<Transistor>(countOfTransistors);
        for (int i = 0; i < countOfTransistors; i++) {
            unit.add(new Transistor(this.helper));
        }
    }

    public void setLeftState(
        int idxTransistor, QManager.QubitInfo qubitInfo) {
        unit.get(idxTransistor).setLeftState(qubitInfo);
    }

    public void setCenterState(
        int idxTransistor, QManager.QubitInfo qubitInfo) {
        unit.get(idxTransistor).setCenterState(qubitInfo);
    }

    public void setRightState(
        int idxTransistor, QManager.QubitInfo qubitInfo) {
        unit.get(idxTransistor).setRightState(qubitInfo);
    }

    public QManager.QubitInfo getRidLeftState(int idxTransistor) {
        return unit.get(idxTransistor).getRidLeftState();
    }

    public QManager.QubitInfo getRidCenterState(int idxTransistor) {
        return unit.get(idxTransistor).getRidCenterState();
    }

    public QManager.QubitInfo getRidRightState(int idxTransistor) {
        return unit.get(idxTransistor).getRidRightState();
    }

    public void opQET(int idxTransistor, double theta) throws Exception {
        unit.get(idxTransistor).opQET(theta);
    }

    public void opPHASE(int idxTransistor, double theta) throws Exception {
        unit.get(idxTransistor).opPHASE(theta);
    }

    public void opCQET(int idxTransistor, double theta) throws Exception {
        unit.get(idxTransistor).opCQET(theta);
    }

    private QManager helper;

    private int countOfTransistors;
    private List<Transistor> unit;
}