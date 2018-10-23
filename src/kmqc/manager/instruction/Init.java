package kmqc.manager.instruction;

import kmqc.manager.controller.memory.QMemAddr;

import kpfu.terentyev.quantum.emulator.core.cuDoubleComplex;

public class Init extends QInstruction {
    public Init(QMemAddr qMemAddr0, QMemAddr qMemAddr1) {
        this.qMemAddr0 = qMemAddr0;
        this.qMemAddr1 = qMemAddr1;
    }

    public void execute() {
        QInstruction.qController.init(qMemAddr0, qMemAddr1);
    }

    private QMemAddr qMemAddr0;
    private QMemAddr qMemAddr1;
}