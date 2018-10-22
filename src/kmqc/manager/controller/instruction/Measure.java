package kmqc.manager.controller.instruction;

import kmqc.manager.controller.memory.QMemAddr;

public class Measure extends QInstruction {
    public Measure(QMemAddr qMemAddr, Integer result) {
        this.qMemAddr = qMemAddr;
        this.result = result;
    }

    public void execute() {
        result = QInstruction.emulator.measure(qMemAddr);
    }

    public Integer fetch() {
        return result;
    }

    private QMemAddr qMemAddr;
    private Integer result;
}