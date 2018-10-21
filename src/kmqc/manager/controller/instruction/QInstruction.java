package kmqc.manager.controller.instruction;

import kpfu.terentyev.quantum.api.KazanModel.Emulator;
import kpfu.terentyev.quantum.api.KazanModel.QuantumProccessorHelper;

public class QInstruction implements Instruction {
    protected static Emulator emulator = new Emulator(200.0, 50.0, 50.0, 1);
}