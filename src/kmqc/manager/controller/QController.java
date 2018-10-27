package kmqc.manager.controller;

import kmqc.manager.controller.memory.CMem;
import kmqc.manager.controller.memory.QMem;
import kmqc.manager.controller.qpu.AddrDevice;
import kmqc.manager.controller.qpu.Placing;
import kmqc.manager.controller.qpu.ProcessingUnit;

import kpfu.terentyev.quantum.emulator.api.QManager;
import kpfu.terentyev.quantum.util.ComplexDouble;

public class QController {

    public QController() {
        this.helper = new QManager();
        this.qpu = new ProcessingUnit(this.helper, 1);
        this.qmem = new QMem(this.helper, 4);
        this.cmem = new CMem(2);
    }

    public void init(int idxCMem, Integer state) {
        qmem.initState(idxCMem, state);
    }

    public void init(int idxQMem, ComplexDouble alpha, ComplexDouble beta) {
        qmem.initState(idxQMem, alpha, beta);
    }

    public void measure(int idxQMem, int idxCMem) {
        cmem.setState(idxCMem, qmem.measure(idxQMem));
    }

    public void load(int idxQMem, AddrDevice addr) {
        switch(addr.placing) {
        case Placing.Left:
            qpu.setLeftState(addr.idxTransistor, qmem.getRidState(idxQMem));
            break;
        case Placing.Center:
            qpu.setCenterState(addr.idxTransistor, qmem.getRidState(idxQMem));
            break;
        case Placing.Right:
            qpu.setRightState(addr.idxTransistor, qmem.getRidState(idxQMem));
            break;
        }
    }

    public void store(AddrDevice addr, int idxQMem) {
        switch(addr.placing) {
        case Placing.Left:
            qmem.setState(idxQMem, qpu.getRidLeftState(addr.idxTransistor));
            break;
        case Placing.Center:
            qmem.setState(idxQMem, qpu.getRidCenterState(addr.idxTransistor));
            break;
        case Placing.Right:
            qmem.setState(idxQMem, qpu.getRidCenterState(addr.idxTransistor));
            break;
        }
    }

    public void opCQET(int idxTransistor, double theta) throws Exception {
        qpu.opCQET(idxTransistor, theta);
    }

    public void opQET(int idxTransistor, double theta) throws Exception {
        qpu.opQET(idxTransistor, theta);
    }

    public void opPHASE(int idxTransistor, double theta) throws Exception {
        qpu.opPHASE(idxTransistor, theta);
    }

    public Integer getIdxCMem(int idx) {
        return cmem.getState(int idx);
    }

    private QManager helper
    private ProcessingUnit qpu;
    private QMem qmem;
    private CMem cmem;
}