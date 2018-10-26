package kmqc.manager.instruction;

import kmqc.manager.controller.qpu.QRegAddr;
import kmqc.manager.controller.memory.QMemAddr;

public class Store extends QInstruction {
    public Store(QRegAddr qRegAddr, QMemAddr qMemAddr) {
        this.qRegAddr = qRegAddr;
        this.qMemAddr = qMemAddr;
    }

    public void execute() {
        QInstruction.qController.store(qRegAddr, qMemAddr);
    }

    private QRegAddr qRegAddr;
    private QMemAddr qMemAddr;
}