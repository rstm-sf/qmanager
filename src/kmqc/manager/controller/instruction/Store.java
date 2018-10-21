package kmqc.manager.controller.instruction;

import kpfu.terentyev.quantum.api.KazanModel.ProcessingAddress;
import kpfu.terentyev.quantum.api.KazanModel.QuantumMemoryAddress;

public class Store extends QInstruction {
    public Store(ProcessingAddress qReg, QuantumMemoryAddress qMemAddr) {
        this.qReg = qReg;
        this.qMemAddr = qMemAddr;
    }

    @Override
    public void Excecute() {
        QInstruction.emulator.save(qMemAddr, qReg);
    }

    private QuantumMemoryAddress qMemAddr;
    private ProcessingAddress qReg;
}