package kmqc.manager.controller.instruction;

import kpfu.terentyev.quantum.api.KazanModel.QuantumMemoryAddress;

public class Measure extends QInstruction {
    public Measure(QuantumMemoryAddress qMemAddr, int result) {
        this.qMemAddr = qMemAddr;
        this.result = result;
    }

    @Override
    public void Excecute() {
        result = QInstruction.emulator.measure(qMemAddr);
    }

    private QuantumMemoryAddress qMemAddr;
    private int result;
}