package kmqc.manager.controller.instruction;

import kpfu.terentyev.quantum.KazanModel.Emulator;

public abstract class QInstruction implements Instruction {
    protected static Emulator emulator = new Emulator(200.0, 50.0, 50.0, 1);

    public QInstruction() {}

    public abstract void execute();
}