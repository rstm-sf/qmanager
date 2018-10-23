package kmqc.manager.instruction;

import kmqc.manager.controller.qpu.QRegAddr;
import kmqc.manager.controller.memory.QMemAddr;

public class Load extends QInstruction {
    public Load(QMemAddr qMemAddr, QRegAddr qRegAddr) {
        this.qMemAddr = qMemAddr;
        this.qRegAddr = qRegAddr;
    }

    public void execute() {
        QInstruction.qController.load(qMemAddr, qRegAddr);
    }

    private QMemAddr qMemAddr;
    private QRegAddr qRegAddr;
}