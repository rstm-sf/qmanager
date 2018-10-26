package kmqc.manager.controller;

import kmqc.manager.controller.memory.QMem;
import kmqc.manager.controller.qpu.Placing;
import kmqc.manager.controller.qpu.ProcessingUnit;

import kpfu.terentyev.quantum.emulator.api.QManager;
import kpfu.terentyev.quantum.util.ComplexDouble;

public class QController {

    public QController() {
        this.helper = new QManager();
        this.qpu = new ProcessingUnit(this.helper, 1);
        this.qmem = new QMem(this.helper, 2);
    }

    public void init(int idxCell, ComplexDouble alpha, ComplexDouble beta) {
        qmem.initState(idxCell, alpha, beta);
    }

    public int measure(int idxCell) {
        return qmem.measure(idxCell);
    }

    public void load(int idxMem, int idxTransistor, Placing placing) {
        switch(placing) {
        case Placing.Left:
            qpu.setLeftState(idxTransistor, qmem.getRidState(idxMem));
            break;
        case Placing.Center:
            qpu.setCenterState(idxTransistor, qmem.getRidState(idxMem));
            break;
        case Placing.Right:
            qpu.setRightState(idxTransistor, qmem.getRidState(idxMem));
            break;
        }
    }

    public void store(int idxTransistor, Placing placing, int idxMem) {
        switch(placing) {
        case Placing.Left:
            qmem.setState(idxMem, qpu.getRidLeftState(idxTransistor));
            break;
        case Placing.Center:
            qmem.setState(idxMem, qpu.getRidCenterState(idxTransistor));
            break;
        case Placing.Right:
            qmem.setState(idxMem, qpu.getRidCenterState(idxTransistor));
            break;
        }
    }

    public void opCQET(int transistorIdx, double theta) throws Exception {
        qpu.opCQET(transistorIdx, theta);
    }

    public void opQET(int transistorIdx, double theta) throws Exception {
        qpu.opQET(transistorIdx, theta);
    }

    public void opPHASE(int transistorIdx, double theta) throws Exception {
        qpu.opPHASE(transistorIdx, theta);
    }

    private QManager helper
    private ProcessingUnit qpu;
    private QMem qmem;
}