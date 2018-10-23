package kmqc.manager.instruction;

import java.util.ArrayList;

import kmqc.manager.controller.memory.QMemAddr;

public class Measure extends QInstruction {
    public Measure(QMemAddr qMemAddr) {
        this.qMemAddr = qMemAddr;
    }

    public void execute() {
        cResults.add(QInstruction.qController.measure(qMemAddr));
    }

    private QMemAddr qMemAddr;
}