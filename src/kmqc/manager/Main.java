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
        testWrapper();
    }

    public static void testWrapper() {
        System.out.print("Start test Wrapper\n");

        int idxCMem1 = 0;
        int idxCMem2 = 1;
        int idxQMem1 = 0;
        int idxQMem2 = 1;
        ComplexDouble alpha1 = Complex.unit();
        ComplexDouble beta1  = Complex.zero();
        ComplexDouble alpha2 = Complex.zero();
        ComplexDouble beta2  = Complex.unit();
        int idxTransistor = 0;
        AddrDevice addrDev1 = new AddrDevice(idxTransistor, Placing.Left);
        AddrDevice addrDev2 = new AddrDevice(idxTransistor, Placing.Right);
        List<QInstruction> instructions = new ArrayList<>();

        instructions.add(new InitCMem(0, 0));
        instructions.add(new InitCMem(1, 0));
        instructions.add(new InitQMem(0, alpha1, beta1));
        instructions.add(new InitQMem(1, alpha2, beta2));
        instructions.add(new Load(idxQMem1, addrDev1));
        instructions.add(new Load(idxQMem2, addrDev2));
        instructions.add(new QET(idxTransistor, Math.PI / 4.0));
        instructions.add(new QET(idxTransistor, Math.PI / 4.0));
        instructions.add(new Store(addrDev1, idxQMem1));
        instructions.add(new Store(addrDev2, idxQMem2));
        instructions.add(new Measure(idxQMem1, idxCMem1));
        instructions.add(new Measure(idxQMem2, idxCMem2));
        for (QInstruction instruction : instructions) {
            instruction.execute();
        }

        System.out.print(
            "q0: " + QInstruction.getIdxCMem(idxCMem1).toString() + "\n");
        System.out.print(
            "q1: " + QInstruction.getIdxCMem(idxCMem2).toString() + "\n");
        System.out.print("End testing\n");
    }
}