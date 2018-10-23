package kmqc.manager.controller.memory;

import kpfu.terentyev.quantum.KazanModel.MemoryHalf;
import kpfu.terentyev.quantum.KazanModel.QuantumMemoryAddress;

public class QMemAddr extends QuantumMemoryAddress {
    public QMemAddr(
        double frequency, double timeDelay, PartLogicalMemAddr part) {
        super(frequency, timeDelay, toMemoryHalf(part));
    }

    private static MemoryHalf toMemoryHalf(PartLogicalMemAddr part) {
        switch (part) {
        case First:
            return MemoryHalf.HALF_0;
        case Second:
            return MemoryHalf.HALF_1;
        }
        return null;
    }
}