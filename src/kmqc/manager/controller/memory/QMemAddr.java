package kmqc.manager.controller.memory;

import kpfu.terentyev.quantum.KazanModel.MemoryHalf;
import kpfu.terentyev.quantum.KazanModel.QuantumMemoryAddress;

public class QMemAddr extends QuantumMemoryAddress {
    public QMemAddr(double frequency, double timeDelay, MemoryHalf memHalf) {
        super(frequency, timeDelay, memHalf);
    }
}