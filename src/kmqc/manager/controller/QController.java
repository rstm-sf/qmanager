package kmqc.manager.controller;

import kmqc.manager.controller.memory.QMemAddr;
import kmqc.manager.controller.qpu.QRegAddr;

import kpfu.terentyev.quantum.emulator.core.cuDoubleComplex;
import kpfu.terentyev.quantum.KazanModel.Emulator;

public class QController {
    public QController() {
        emulator = new Emulator(200.0, 50.0, 50.0, 1);
    }

    public void init(QMemAddr qMemAddr0, QMemAddr qMemAddr1) {
        emulator.initLogicalQubit(
            qMemAddr0,
            cuDoubleComplex.cuCmplx(1, 0),
            cuDoubleComplex.cuCmplx(0, 0),
            qMemAddr1,
            cuDoubleComplex.cuCmplx(0, 0),
            cuDoubleComplex.cuCmplx(1, 0));
    }

    public int measure(QMemAddr qMemAddr) {
        return emulator.measure(qMemAddr);
    }

    public void load(QMemAddr qMemAddr, QRegAddr qRegAddr) {
        emulator.load(qMemAddr, qRegAddr);
    }

    public void store(QRegAddr qRegAddr, QMemAddr qMemAddr) {
        emulator.save(qRegAddr, qMemAddr);
    }

    public void opCQET(int transistorIdx, double theta) {
        emulator.cQET(transistorIdx, theta);
    }

    public void opQET(int transistorIdx, double theta) {
        emulator.QET(transistorIdx, theta);
    }

    public void opPHASE(int transistorIdx, double theta) {
        emulator.PHASE(transistorIdx, theta);
    }

    private Emulator emulator;
}