package kmqc.manager.controller.instruction;

import kpfu.terentyev.quantum.api.KazanModel.QuantumMemoryAddress;

public class Init extends QInstruction {
    public Init(
        QuantumMemoryAddress qMemAddr0,
        QuantumMemoryAddress qMemAddr1) {
        this.qMemAddr0 = qMemAddr0;
        this.qMemAddr1 = qMemAddr1;
    }

    @Override
    public void Excecute() {
        QInstruction.emulator.initLogicalQubit(
            qMemAddr0, new Complex(1, 0), new Complex(0, 0),
            qMemAddr1, new Complex(0, 0), new Complex(1, 0));
    }

    private QuantumMemoryAddress qMemAddr0;
    private QuantumMemoryAddress qMemAddr1;
}