package kmqc.manager.controller.instruction;

import kpfu.terentyev.quantum.api.KazanModel.ProcessingAddress;
import kpfu.terentyev.quantum.api.KazanModel.QuantumMemoryAddress;

public class Load extends QInstruction {
    public Load(QuantumMemoryAddress qMemAddr, ProcessingAddress qReg) {
        this.qMemAddr = qMemAddr;
        this.qReg = qReg;
    }

    @Override
    public void Excecute() {
        QInstruction.emulator.load(qMemAddr, qReg);
    }

    private QuantumMemoryAddress qMemAddr;
    private ProcessingAddress qReg;
}