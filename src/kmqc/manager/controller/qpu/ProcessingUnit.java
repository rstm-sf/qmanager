package kmqc.manager.controller.qpu;

import java.util.ArrayList;
import java.util.List;

import kpfu.terentyev.quantum.emulator.api.Helper;

public class ProcessingUnit {
    
    public ProcessingUnit(Helper helper, int countOfTransistors) {
        this.helper = helper;
        this.countOfTransistors = countOfTransistors;
        unit = new ArrayList<Transistor>(countOfTransistors);
        for (int i = 0; i < countOfTransistors; i++) {
            unit.add(new Transistor(this.helper));
        }
    }

    public void setLeftState(
        int idxTransistor, Helper.QubitInfo qubitInfo) {
        unit.get(idxTransistor).setLeftState(qubitInfo);
    }

    public void setCenterState(
        int idxTransistor, Helper.QubitInfo qubitInfo) {
        unit.get(idxTransistor).setCenterState(qubitInfo);
    }

    public void setRightState(
        int idxTransistor, Helper.QubitInfo qubitInfo) {
        unit.get(idxTransistor).setRightState(qubitInfo);
    }

    public Helper.QubitInfo getRidLeftState(int idxTransistor) {
        return unit.get(idxTransistor).getRidLeftState();
    }

    public Helper.QubitInfo getRidCenterState(int idxTransistor) {
        return unit.get(idxTransistor).getRidCenterState();
    }

    public Helper.QubitInfo getRidRightState(int idxTransistor) {
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

    private Helper helper;

    private int countOfTransistors;
    private List<Transistor> unit;
}