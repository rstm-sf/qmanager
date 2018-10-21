package kmqc.manager;

import kpfu.terentyev.quantum.KazanModel.*;
import kpfu.terentyev.quantum.emulator.api.QuantumManager;
import kpfu.terentyev.quantum.emulator.core.Complex;
import kpfu.terentyev.quantum.emulator.core.cuDoubleComplex;

public class Main {
    public static void testKazanModelEmulator(){
        // QVM initialization
        double MAX_MEMORY_FREQUENCY = 200.0;
        double MIN_MEMORY_FREQUENCY = 50.0;
        double MEMORY_TIME_CYCLE = 50.0;
        int PROCCESSING_UNITS_COUNT = 3;
        Emulator QVM = new Emulator(
            MAX_MEMORY_FREQUENCY,
            MIN_MEMORY_FREQUENCY,
            MEMORY_TIME_CYCLE,
            PROCCESSING_UNITS_COUNT);

        // Qubits initialization
        double logicalQubit1Freq = 60;
        double logicalQubit1TimeDelay = 1;
        QuantumMemoryAddress q1Addr = new QuantumMemoryAddress(
            logicalQubit1Freq, logicalQubit1TimeDelay, MemoryHalf.HALF_0);
        QuantumMemoryAddress q2Addr = new QuantumMemoryAddress(
            logicalQubit1Freq, logicalQubit1TimeDelay, MemoryHalf.HALF_1);
        QVM.initLogicalQubit(
            q1Addr,
            cuDoubleComplex.cuCmplx(1, 0),
            cuDoubleComplex.cuCmplx(0, 0),
            q2Addr,
            cuDoubleComplex.cuCmplx(0, 0),
            cuDoubleComplex.cuCmplx(1, 0));

        // Transistor addresses
        int currTrIdx = 0;
        ProcessingAddress tr0 = new ProcessingAddress(
            currTrIdx, ProcessingUnitCellAddress.Cell0);
        ProcessingAddress tr1 = new ProcessingAddress(
            currTrIdx, ProcessingUnitCellAddress.Cell1);
        ProcessingAddress trC = new ProcessingAddress(
            currTrIdx, ProcessingUnitCellAddress.ControlPoint);

        // Transitions
        QVM.load(q1Addr, tr0);
        QVM.load(q2Addr, tr1);
        QVM.QET(currTrIdx, Math.PI / 4.0);
        QVM.QET(currTrIdx, Math.PI / 4.0);
        QVM.save(tr0, q1Addr);
        QVM.save(tr1, q2Addr);

        System.out.print("q1: " + QVM.measure(q1Addr) + "\n");
        System.out.print("q2: " + QVM.measure(q2Addr) + "\n");
        System.out.print("End testing");
    }

    public static void main(String[] args) throws Exception {
        testKazanModelEmulator();
    }
}