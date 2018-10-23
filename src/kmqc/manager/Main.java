package kmqc.manager;

import java.util.ArrayList;
import java.util.List;

import kmqc.manager.instruction.Init;
import kmqc.manager.instruction.Load;
import kmqc.manager.instruction.Measure;
import kmqc.manager.instruction.QET;
import kmqc.manager.instruction.QInstruction;
import kmqc.manager.instruction.Store;
import kmqc.manager.controller.memory.PartLogicalMemAddr;
import kmqc.manager.controller.memory.QMemAddr;
import kmqc.manager.controller.qpu.QRegAddr;
import kmqc.manager.controller.qpu.Transistor;
import kmqc.manager.controller.qpu.TransistorCellAdrr;

import kpfu.terentyev.quantum.KazanModel.*;
import kpfu.terentyev.quantum.emulator.api.QuantumManager;
import kpfu.terentyev.quantum.emulator.core.Complex;
import kpfu.terentyev.quantum.emulator.core.cuDoubleComplex;

public class Main {
    public static void main(String[] args) throws Exception {
        testKazanModelEmulator();
        testWrapper();
    }

    public static void testWrapper() {
        System.out.print("Start test Wrapper\n");

        double lQubit1Freq = 60.0;
        double lQubit1TimeDelay = 1.0;
        QMemAddr q1Addr = new QMemAddr(
            lQubit1Freq, lQubit1TimeDelay, PartLogicalMemAddr.First);
        QMemAddr q2Addr = new QMemAddr(
            lQubit1Freq, lQubit1TimeDelay, PartLogicalMemAddr.Second);

        int currTrIdx = 0;
        Transistor transistor = new Transistor(
            currTrIdx,
            new QRegAddr(currTrIdx, TransistorCellAdrr.Left),
            new QRegAddr(currTrIdx, TransistorCellAdrr.Center),
            new QRegAddr(currTrIdx, TransistorCellAdrr.Right));

        List<QInstruction> instructions = new ArrayList<>();
        instructions.add(new Init(q1Addr, q2Addr));
        instructions.add(new Load(q1Addr, transistor.qRegAddrL));
        instructions.add(new Load(q2Addr, transistor.qRegAddrR));
        instructions.add(new QET(currTrIdx, Math.PI / 4.0));
        instructions.add(new QET(currTrIdx, Math.PI / 4.0));
        instructions.add(new Store(transistor.qRegAddrL, q1Addr));
        instructions.add(new Store(transistor.qRegAddrR, q2Addr));
        instructions.add(new Measure(q1Addr));
        instructions.add(new Measure(q2Addr));

        for (QInstruction instruction : instructions) {
            instruction.execute();
        }

        ArrayList<Integer> cResults = instructions.get(0).getCResults();
        for (Integer result : cResults) {
            System.out.print("q?: " + result.toString() + "\n");
        }

        System.out.print("End testing\n");
    }

    public static void testKazanModelEmulator() {
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
        double lQubit1Freq = 60.0;
        double lQubit1TimeDelay = 1.0;
        QuantumMemoryAddress q1Addr = new QuantumMemoryAddress(
            lQubit1Freq, lQubit1TimeDelay, MemoryHalf.HALF_0);
        QuantumMemoryAddress q2Addr = new QuantumMemoryAddress(
            lQubit1Freq, lQubit1TimeDelay, MemoryHalf.HALF_1);
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
        System.out.print("End testing\n");
    }
}