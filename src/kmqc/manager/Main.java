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

        testQuantumManager();
    }

    static void testQuantumManager() {
        try {
            cuDoubleComplex u = Complex.unit();
            cuDoubleComplex z = Complex.zero();
            System.out.print("\n\n-----INITIALIZATION-----\n\n");

            QuantumManager manager = new QuantumManager();
            QuantumManager.Qubit q1 = manager.initNewQubit(z, u);
            QuantumManager.Qubit q2 = manager.initNewQubit(u, z);
            QuantumManager.Qubit q3 = manager.initNewQubit(z, u);
            QuantumManager.Qubit q4 = manager.initNewQubit(z, u);
            QuantumManager.Qubit q5 = manager.initNewQubit(z, u);
            QuantumManager.Qubit q6 = manager.initNewQubit(z, u);
            QuantumManager.Qubit q7 = manager.initNewQubit(z, u);
            QuantumManager.Qubit q8 = manager.initNewQubit(z, u);

            System.out.print("Q1: |1>\n");
            System.out.print("Q2: |0>\n");
            System.out.print("Q3: |1>\n");
            System.out.print("Q4: |1>\n");
            System.out.print("Q5: |1>\n");
            System.out.print("Q6: |1>\n");
            System.out.print("Q7: |1>\n");
            System.out.print("Q8: |1>\n");
            System.out.print("\n\n-----OPERATIONS-----\n\n");
            
            cuDoubleComplex[][] U = {
                {u, z, z, z},
                {z, u, z, z},
                {z, z, z, u},
                {z, z, u, z},};

            manager.performTransitionForQubits(q1, U, q2);
            System.out.print("CNOT(Q1, Q2)\n");

            manager.performTransitionForQubits(q3, U, q2);
            System.out.print("CNOT(Q3, Q2)\n");

            manager.performTransitionForQubits(q4, U, q1);
            System.out.print("CNOT(Q4, Q1)\n");

            manager.performTransitionForQubits(q4, U, q5);
            System.out.print("CNOT(Q4, Q5)\n");

            manager.performTransitionForQubits(q6, U, q2);
            System.out.print("CNOT(Q6, Q2)\n");

            manager.performTransitionForQubits(q7, U, q2);
            System.out.print("CNOT(Q7, Q2)\n");

            manager.performTransitionForQubits(q8, U, q3);
            System.out.print("CNOT(Q8, Q3)\n");

            System.out.print("\n\n-----MEASURMENT-----\n\n");         
            System.out.print("Q1:" + manager.measure(q1) + "\n");
            System.out.print("Q2:" + manager.measure(q2) + "\n");
            System.out.print("Q3:" + manager.measure(q3) + "\n");
            System.out.print("Q4:" + manager.measure(q4) + "\n");
            System.out.print("Q5:" + manager.measure(q5) + "\n");
            System.out.print("Q6:" + manager.measure(q6) + "\n");
            System.out.print("Q7:" + manager.measure(q7) + "\n");
            System.out.print("Q8:" + manager.measure(q8) + "\n");
        } catch (Exception e) {
            System.out.print("Exception:" + e.getLocalizedMessage() + "\n");
        }
    }

    public static void main(String[] args) throws Exception {
        testKazanModelEmulator();
    }
}