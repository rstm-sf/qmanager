package kmqc.manager;

import java.util.ArrayList;
import java.util.List;

import kmqc.manager.controller.qpu.AddrDevice;
import kmqc.manager.controller.qpu.Placing;
import kmqc.manager.controller.QController;
import kmqc.manager.instruction.*;

import kpfu.terentyev.quantum.util.Complex;
import kpfu.terentyev.quantum.util.ComplexDouble;

public class Main {

    public static void main(String[] args) throws Exception {
        testDeutsch();
    }

    public static void testDeutsch() {
        System.out.print("Start Deutsch algorithm\n");

        int idxCMem = 0;
        LogicalQubit q0 = new LogicalQubit(0, 1);
        LogicalQubit q1 = new LogicalQubit(2, 3);
        int idxTransistor = 0;

        List<QInstruction> instructions = new ArrayList<>();
        instructions.addAll(initCBit(idxCMem));
        instructions.addAll(initLogicalQubit(q0));
        instructions.addAll(initLogicalQubit(q1));
        instructions.addAll(gateX(idxTransistor, q1));
        instructions.addAll(gateH(idxTransistor, q0));
        instructions.addAll(gateH(idxTransistor, q1));
        instructions.addAll(gateCNOT(idxTransistor, q1, q0));
        instructions.addAll(gateH(idxTransistor, q0));
        instructions.addAll(measure(q0, idxCMem));

        for (QInstruction instruction : instructions) {
            instruction.execute();
        }

        System.out.print(
            "q0: " + QInstruction.getIdxCMem(idxCMem).toString() + "\n");
        System.out.print("End algorithm\n");
    }

    private static class LogicalQubit {
        public int idxFirst;
        public int idxSecond;

        public LogicalQubit(int idxFirst, int idxSecond) {
            this.idxFirst = idxFirst;
            this.idxSecond = idxSecond;
        }
    }

    private static List<QInstruction> initCBit(int idxCMem) {
        List<QInstruction> instr = new ArrayList<>(List.of(
            new InitCMem(idxCMem, 0)
        ));
        return instr;
    }

    private static List<QInstruction> measure(LogicalQubit q, int idxCMem) {
        List<QInstruction> instr = new ArrayList<>(List.of(
            new Measure(q.idxFirst, idxCMem)
        ));
        return instr;
    }

    private static List<QInstruction> initLogicalQubit(LogicalQubit q) {
        List<QInstruction> instr = new ArrayList<>(List.of(
            new InitQMem(q.idxFirst, Complex.zero(), Complex.zero()),
            new InitQMem(q.idxSecond, Complex.unit(), Complex.zero())
        ));
        return instr;
    }

    private static List<QInstruction> opU2(
        int idxTransistor, double phi, double mu, LogicalQubit q) {
        AddrDevice addrL = new AddrDevice(idxTransistor, Placing.Left);
        AddrDevice addrR = new AddrDevice(idxTransistor, Placing.Right);
        List<QInstruction> instr = new ArrayList<>(List.of(
            new Load(q.idxFirst, addrL),
            new Load(q.idxSecond, addrR),
            new PHASE(idxTransistor, phi + Math.PI / 2.0),
            new QET(  idxTransistor, -Math.PI / 2.0),
            new PHASE(idxTransistor, mu - Math.PI / 2.0),
            new Store(addrL, q.idxFirst),
            new Store(addrR, q.idxSecond)
        ));
        return instr;
    }

    private static List<QInstruction> opU3(
        int idxTransistor,
        double theta, double phi, double mu,
        LogicalQubit q) {
        AddrDevice addrL = new AddrDevice(idxTransistor, Placing.Left);
        AddrDevice addrR = new AddrDevice(idxTransistor, Placing.Right);
        List<QInstruction> instr = new ArrayList<>(List.of(
            new Load(q.idxFirst, addrL),
            new Load(q.idxSecond, addrR),
            new PHASE(idxTransistor, phi + 3 * Math.PI),
            new QET(  idxTransistor, -Math.PI / 2.0),
            new PHASE(idxTransistor, theta + Math.PI),
            new QET(  idxTransistor, -Math.PI / 2.0),
            new PHASE(idxTransistor, mu),
            new Store(addrL, q.idxFirst),
            new Store(addrR, q.idxSecond)
        ));
        return instr;
    }

    private static List<QInstruction> gateCNOT(
        int idxTransistor, LogicalQubit qA, LogicalQubit qC) {
        AddrDevice addrL = new AddrDevice(idxTransistor, Placing.Left);
        AddrDevice addrC = new AddrDevice(idxTransistor, Placing.Center);
        AddrDevice addrR = new AddrDevice(idxTransistor, Placing.Right);
        List<QInstruction> instr = new ArrayList<>(List.of(
            new Load(qC.idxFirst, addrL),
            new Load(qC.idxSecond, addrR),
            new Load(qA.idxFirst, addrC),
            new QET(  idxTransistor, Math.PI),
            new CQET( idxTransistor, Math.PI),
            new PHASE(idxTransistor, Math.PI / 2.0),
            new QET(  idxTransistor, Math.PI),
            new Store(addrL, qC.idxFirst),
            new Store(addrR, qC.idxSecond),
            new Store(addrC, qA.idxFirst)
        ));
        return instr;
    }

    private static List<QInstruction> gateX(int idxTransistor, LogicalQubit q) {
        List<QInstruction> instr = new ArrayList<>();
        instr.addAll(opU3(idxTransistor, Math.PI, 0.0, Math.PI, q));
        return instr;
    }

    private static List<QInstruction> gateH(int idxTransistor, LogicalQubit q) {
        List<QInstruction> instr = new ArrayList<>();
        instr.addAll(opU2(idxTransistor, 0.0, Math.PI, q));
        return instr;
    }
}